package se.fastdev.portal.motivator.bonuses.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import se.fastdev.portal.motivator.bonuses.core.fakes.BonusesStorageBroken;
import se.fastdev.portal.motivator.bonuses.core.models.BulkProcessReport;
import se.fastdev.portal.motivator.bonuses.core.models.Person;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;

@DisplayName("when storage produces an error for every item ...")
public final class TestSupportRefreshAttributesGotErrorsForAllItems {

  private BonusesGate gate;
  private BonusesStorage storage;
  private BulkProcessReport report;

  @BeforeEach
  public void beforeEach() {
    this.storage = new BonusesStorageBroken() {
      @Override
      public Flux<Person> findAll() {
        return Flux.just();
      }
    };
    this.gate = BonusesGate.createGate(storage);

    var newOfferedAttributes = Stream.of(
        new PersonAttributes("457", "SDosuydifuso", "wjwytjrwjtwyj", "dmwjwmhesnem"),
        new PersonAttributes("769", "rhsyuhtrshstty", "twueyuqtjyeuyj", "tuwwtuwtue")
    );

    this.report = gate.support()
                      .refreshAttributes(newOfferedAttributes, PersonAttributes::getPortalId)
                      .block();
  }

  @Test
  @DisplayName("... then all errors are in the report")
  public void thenReportContainsErrorMessages() {
    assertThat(report.getErrors(),
               containsInAnyOrder(
                   "se.fastdev.portal.motivator.bonuses.core.BonusesException: Unable to save person (769, rhsyuhtrshstty, twueyuqtjyeuyj, tuwwtuwtue)\nse.fastdev.portal.motivator.bonuses.toolbox.exceptions.CommonException: save(Person) secret message",
                   "se.fastdev.portal.motivator.bonuses.core.BonusesException: Unable to save person (457, SDosuydifuso, wjwytjrwjtwyj, dmwjwmhesnem)\nse.fastdev.portal.motivator.bonuses.toolbox.exceptions.CommonException: save(Person) secret message"
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
}
