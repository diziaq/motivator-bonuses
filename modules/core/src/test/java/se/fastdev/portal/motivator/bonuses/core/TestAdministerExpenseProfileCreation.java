package se.fastdev.portal.motivator.bonuses.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile.Blueprint;
import se.fastdev.portal.motivator.bonuses.core.models.MoneyAmount;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;
import se.fastdev.portal.motivator.bonuses.core.models.TimeRange;

public class TestAdministerExpenseProfileCreation {

  private BonusesGate gate;

  @BeforeEach
  public void beforeEach() {
    this.gate = BonusesGate.createGate(BonusesStorage.inMemory());
  }

  @Test
  @DisplayName("when ExpenseProfile created for unknown person then error")
  public void profileForUnknownPerson() {
    var blueprint = new Blueprint(new MoneyAmount(100),
                                  new TimeRange(Instant.parse("2020-01-04T08:00:10Z"),
                                                Instant.parse("2020-11-08T12:10:07Z")));

    var personMono = gate.administer().startNewExpenseProfile(UUID.randomUUID(), blueprint);

    var exception = assertThrows(BonusesException.class, () -> personMono.block());

    assertThat(exception.getMessage(), startsWith("Unable to get person for id"));
  }

  @Test
  @DisplayName("when ExpenseProfile created it should have UUID")
  public void profileForExistingPerson() {
    var person = gate.administer()
                     .createNewPerson(new PersonAttributes("asd", "safsa", "safgds"))
                     .block();

    final var limit = new MoneyAmount(100);
    final var period = new TimeRange(Instant.parse("2020-01-04T08:00:10Z"),
                                     Instant.parse("2020-11-08T12:10:07Z"));

    var blueprint = new Blueprint(limit, period);

    var personWithProfile =
        gate.administer().startNewExpenseProfile(person.getUuid(), blueprint).block();

    var activeProfile = personWithProfile.getActiveExpenseProfile();

    assertAll(
        () -> assertEquals(limit, activeProfile.getLimit()),
        () -> assertThat(activeProfile.getExpenses(), is(empty())),
        () -> assertEquals(period, activeProfile.getPeriodOfActivity())
    );
  }
}
