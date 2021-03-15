package se.fastdev.portal.motivator.bonuses.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import se.fastdev.portal.motivator.bonuses.core.fakes.BonusesStorageBroken;
import se.fastdev.portal.motivator.bonuses.core.models.BulkProcessReport;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile;
import se.fastdev.portal.motivator.bonuses.core.models.MoneyAmount;
import se.fastdev.portal.motivator.bonuses.core.models.Person;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;
import se.fastdev.portal.motivator.bonuses.core.models.TimeRange;

@DisplayName("when storage produces an error for every item ...")
public final class TestSupportRefreshExpensePeriodsGotErrorsForAllItems {

  private BonusesGate gate;
  private BonusesStorage storage;
  private BulkProcessReport report;

  @BeforeEach
  public void beforeEach() {
    this.storage = new BonusesStorageBroken() {
      @Override
      public Flux<Person> findAll() {
        return Flux.just(
            person("00000000-0000-0000-0000-000000000001",
                   new ExpenseProfile(
                       new MoneyAmount(1001),
                       new TimeRange(Instant.parse("2020-10-01T00:13:01Z"), Instant.parse("2021-08-01T01:19:22Z")),
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
      }
    };
    this.gate = BonusesGate.createGate(storage);

    this.report = gate.support()
                      .refreshExpenseProfiles(
                          new ExpenseProfile.Blueprint(
                              new MoneyAmount(2000),
                              new TimeRange(Instant.parse("2021-08-01T05:00:51Z"),
                                            Instant.parse("2022-10-01T00:13:01Z"))
                          ))
                      .block();
  }

  @Test
  @DisplayName("... then all errors are in the report")
  public void thenReportContainsErrorMessages() {
    assertThat(report.getErrors(),
               containsInAnyOrder(
                   "se.fastdev.portal.motivator.bonuses.core.BonusesException: Unable to save person (attr-test-00000000-0000-0000-0000-000000000001, attr-test-00000000-0000-0000-0000-000000000001, attr-test-00000000-0000-0000-0000-000000000001, attr-test-00000000-0000-0000-0000-000000000001)\nse.fastdev.portal.motivator.bonuses.toolbox.exceptions.CommonException: save(Person) secret message",
                   "se.fastdev.portal.motivator.bonuses.core.BonusesException: Unable to save person (attr-test-00000000-0000-0000-0000-000000000002, attr-test-00000000-0000-0000-0000-000000000002, attr-test-00000000-0000-0000-0000-000000000002, attr-test-00000000-0000-0000-0000-000000000002)\nse.fastdev.portal.motivator.bonuses.toolbox.exceptions.CommonException: save(Person) secret message",
                   "se.fastdev.portal.motivator.bonuses.core.BonusesException: Unable to save person (attr-test-00000000-0000-0000-0000-000000000003, attr-test-00000000-0000-0000-0000-000000000003, attr-test-00000000-0000-0000-0000-000000000003, attr-test-00000000-0000-0000-0000-000000000003)\nse.fastdev.portal.motivator.bonuses.toolbox.exceptions.CommonException: save(Person) secret message"
               )
    );
  }

  @Test
  @DisplayName("... then zero processed")
  public void thenZeroProcessed() {
    assertEquals(0, report.getProcessedCount());
  }

  @Test
  @DisplayName("... then zero skipped")
  public void thenZeroSkipped() {
    assertEquals(0, report.getSkippedCount());
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
