package se.fastdev.portal.motivator.bonuses.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fastdev.portal.motivator.bonuses.core.models.BulkProcessReport;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile;
import se.fastdev.portal.motivator.bonuses.core.models.MoneyAmount;
import se.fastdev.portal.motivator.bonuses.core.models.Person;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;
import se.fastdev.portal.motivator.bonuses.core.models.TimeRange;

@DisplayName("when offered a new expense profile ...")
public final class TestSupportRefreshExpensePeriodsMatchedAndSkippedWithoutErrors {

  private BonusesGate gate;
  private BonusesStorage storage;
  private BulkProcessReport report;

  @BeforeEach
  public void beforeEach() {
    this.storage = BonusesStorage.inMemory();
    this.gate = BonusesGate.createGate(storage);

    addToStorage(
        storage,
        person("00000000-0000-0000-0000-000000000001",
               new ExpenseProfile(
                   new MoneyAmount(1001),
                   new TimeRange(Instant.parse("2020-10-01T00:13:01Z"), Instant.parse("2021-08-08T01:19:22Z")),
                   List.of()
               )
        ),
        person("00000000-0000-0000-0000-000000000002",
               new ExpenseProfile(
                   new MoneyAmount(1002),
                   new TimeRange(Instant.parse("2021-01-01T13:00:41Z"), Instant.parse("2021-08-01T05:00:51Z")),
                   List.of()
               )
        ),
        person("00000000-0000-0000-0000-000000000003",
               new ExpenseProfile(
                   new MoneyAmount(1003),
                   new TimeRange(Instant.parse("2021-04-01T13:00:41Z"), Instant.parse("2021-07-11T15:22:21Z")),
                   List.of()
               )
        )
    );

    this.report = gate.support()
                      .refreshExpenseProfiles(
                          new MoneyAmount(2000),
                          new TimeRange(Instant.parse("2021-08-01T05:00:51Z"), Instant.parse("2022-10-01T00:13:01Z"))
                      )
                      .block();
  }

  @Test
  @DisplayName("... then no errors'")
  public void thenHasNoErrors() {
    assertThat(report.getErrors(), is(empty()));
  }

  @Test
  @DisplayName("... then one skipped")
  public void thenHasNoSkipped() {
    assertEquals(1, report.getSkippedCount());
  }

  @Test
  @DisplayName("... then two processed")
  public void thenOneSuccessful() {
    assertEquals(2, report.getProcessedCount());
  }

  @Test
  @DisplayName(".. and new time range starts BEFORE old time range ends then expense profile remain unchanged")
  public void thenProfileWithLastingOldTimeRangeRemainUnchanged() {
    var person = gate.administer()
                     .getPersonById(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                     .block();

    var profile = person.getActiveExpenseProfile();

    assertAll(
        () -> assertEquals(1001, profile.getLimit().asInt()),
        () -> assertEquals(
            new TimeRange(Instant.parse("2020-10-01T00:13:01Z"), Instant.parse("2021-08-08T01:19:22Z")),
            profile.getPeriodOfActivity()
        )
    );
  }

  @Test
  @DisplayName(".. and new time range starts AFTER old time range ends then expense profile is renewed")
  public void thenProfileWithExpiredTimeRangeIsReplacedWithNew() {
    var person = gate.administer()
                     .getPersonById(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                     .block();

    var profile = person.getActiveExpenseProfile();

    assertAll(
        () -> assertEquals(2000, profile.getLimit().asInt()),
        () -> assertEquals(
            new TimeRange(Instant.parse("2021-08-01T05:00:51Z"), Instant.parse("2022-10-01T00:13:01Z")),
            profile.getPeriodOfActivity()
        )
    );
  }

  @Test
  @DisplayName(".. and new time range start is ADJACENT with old time range end then expense profile is renewed")
  public void thenProfileWithAdjacentTimeRangeIsReplacedWithNew() {
    var person = gate.administer()
                     .getPersonById(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                     .block();

    var profile = person.getActiveExpenseProfile();

    assertAll(
        () -> assertEquals(2000, profile.getLimit().asInt()),
        () -> assertEquals(
            new TimeRange(Instant.parse("2021-08-01T05:00:51Z"), Instant.parse("2022-10-01T00:13:01Z")),
            profile.getPeriodOfActivity()
        )
    );
  }

  private static void addToStorage(BonusesStorage storage, Person... persons) {
    Stream.of(persons)
          .map(storage::save)
          .forEach(m -> m.block());
  }

  private static Person person(String uuid, ExpenseProfile expenseProfile) {
    var attrItem = "attr-test-" + uuid;
    var person = new Person(
        UUID.fromString(uuid),
        new PersonAttributes(attrItem, attrItem, attrItem, attrItem)
    );

    person = person.startNewExpenseProfile(expenseProfile);

    return person;
  }
}
