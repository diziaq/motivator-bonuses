package se.fastdev.portal.motivator.bonuses.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fastdev.portal.motivator.bonuses.core.models.ActionStamp;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseItem;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseType;
import se.fastdev.portal.motivator.bonuses.core.models.MoneyAmount;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;
import se.fastdev.portal.motivator.bonuses.core.models.TimeRange;

public class TestAdministerExpenseItemAdding {

  private BonusesGate gate;

  @BeforeEach
  public void beforeEach() {
    this.gate = BonusesGate.createGate(BonusesStorage.inMemory());
  }

  @Test
  @DisplayName("when ExpenseItem added for unknown person then error")
  public void expenseForUnknownPerson() {
    var blueprint = new ExpenseItem.Blueprint(
        ExpenseType.HEALTHCARE,
        new MoneyAmount(797575),
        "rkltsyk djkhsfmh",
        new ActionStamp(Instant.MAX, "afjlkgja")
    );

    var personMono = gate.administer().addNewExpenseItem(UUID.randomUUID(), blueprint);

    var exception = assertThrows(BonusesException.class, () -> personMono.block());

    assertThat(exception.getMessage(), startsWith("Unable to get person for id"));
  }

  @Test
  @DisplayName("when ExpenseItem amount exceeds remainder of active profile then error")
  public void expenseForExceedingSum() {
    var personUuid =
        createPersonWithActiveProfile(5382, "2020-01-02T06:07:08Z", "2020-12-02T06:07:08Z");

    var blueprint = new ExpenseItem.Blueprint(
        ExpenseType.HEALTHCARE,
        new MoneyAmount(5383),
        "rkltsyk djkhsfmh",
        new ActionStamp(Instant.parse("2020-03-01T11:16:19Z"), "afjlkgja")
    );

    var personMono = gate.administer().addNewExpenseItem(personUuid, blueprint);

    var exception = assertThrows(BonusesException.class, () -> personMono.block());

    assertEquals(
        "Expense amount (53.83) exceeds remaining sum (53.82)",
        exception.getMessage()
    );
  }

  @Test
  @DisplayName("when ExpenseItem date after active period")
  public void expenseForWringPeriod() {
    var personUuid =
        createPersonWithActiveProfile(7000, "2020-01-02T01:01:02Z", "2020-04-02T03:04:05Z");

    var blueprint = new ExpenseItem.Blueprint(
        ExpenseType.SPORTS,
        new MoneyAmount(1000),
        "dhkskysuk dhkdhkd",
        new ActionStamp(Instant.parse("2020-04-03T11:16:19Z"), "lduskstkys")
    );

    var personMono = gate.administer().addNewExpenseItem(personUuid, blueprint);

    var exception = assertThrows(BonusesException.class, () -> personMono.block());

    assertEquals(
        "Expense time 2020-04-03T11:16:19Z is out of the period (2020-01-02T01:01:02Z :: 2020-04-02T03:04:05Z)",
        exception.getMessage()
    );
  }

  @Test
  @DisplayName("when ExpenseItem is added then remainder is decreased appropriately")
  public void addedExpenseDecreasesRemainingSum() {
    var personUuid =
        createPersonWithActiveProfile(74997, "2019-07-02T01:01:02Z", "2020-12-02T03:04:05Z");

    var blueprint = new ExpenseItem.Blueprint(
        ExpenseType.SPORTS,
        new MoneyAmount(4911),
        "gathjwtywt twqthwt hj yw jtwtr hwwtjw",
        new ActionStamp(Instant.parse("2019-08-13T11:16:19Z"), "ukey3qw")
    );

    var person = gate.administer().addNewExpenseItem(personUuid, blueprint).block();

    var activeProfile = person.getActiveExpenseProfile();

    assertEquals(70086, activeProfile.remainder().asInt());
  }

  @Test
  @DisplayName("when ExpenseItem is added then it is available as first item in expenses list")
  public void addedExpenseAvailableInList() {
    var personUuid =
        createPersonWithActiveProfile(74997, "2018-01-02T01:01:02Z", "2019-12-02T03:04:05Z");

    var blueprint = new ExpenseItem.Blueprint(
        ExpenseType.SPORTS,
        new MoneyAmount(4911),
        "lkfds jdglkjsg kdsfn lka kjg lksajf",
        new ActionStamp(Instant.parse("2019-05-03T11:16:19Z"), "ldagagfy")
    );

    var person = gate.administer().addNewExpenseItem(personUuid, blueprint).block();

    var item = person.getActiveExpenseProfile().getExpenses().get(0);

    assertAll(
        () -> assertNotNull(item.getUuid()),
        () -> assertNotNull(item.getPublishedStamp().getBy()),
        () -> assertEquals(4911, item.getAmount().asInt()),
        () -> assertEquals(ExpenseType.SPORTS, item.getType()),
        () -> assertEquals("lkfds jdglkjsg kdsfn lka kjg lksajf", item.getDescription()),
        () -> assertEquals(Instant.parse("2019-05-03T11:16:19Z"), item.getSpentStamp().getAt()),
        () -> assertEquals("ldagagfy", item.getSpentStamp().getBy())
    );
  }

  private UUID createPersonWithActiveProfile(int profileLimit, String start, String end) {
    var person = gate.administer()
                     .createNewPerson(new PersonAttributes("asd", "safsa", "safgds"))
                     .block();

    final var limit = new MoneyAmount(profileLimit);
    final var period = new TimeRange(Instant.parse(start), Instant.parse(end));

    var blueprint = new ExpenseProfile.Blueprint(limit, period);

    gate.administer().startNewExpenseProfile(person.getUuid(), blueprint).block();

    return person.getUuid();
  }
}
