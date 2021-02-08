package se.fastdev.portal.motivator.bonuses.core;

import static java.util.function.Predicate.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fastdev.portal.motivator.bonuses.core.models.BulkProcessReport;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;

@DisplayName("when new PersonAttributes element is given ...")
public final class TestSupportRefreshAttributesNewItemIntroducedToEmptyStorage {

  private BonusesGate gate;
  private BonusesStorage storage;
  private BulkProcessReport report;

  @BeforeEach
  public void beforeEach() {
    this.storage = BonusesStorage.inMemory();
    this.gate = BonusesGate.createGate(storage);

    var newOfferedAttributes = Stream.of(
        new PersonAttributes("1234", "iqjpoqeurqr", "oifuoaisifpa", "naokdaflkdpoa")
    );

    this.report = gate.support()
                      .refreshAttributes(newOfferedAttributes, PersonAttributes::getPortalId)
                      .block();
  }

  @Test
  @DisplayName("... then new person is created")
  public void thenNewPersonIsCreated() {
    var predefinedUuids = List.of("00000000-0000-0000-0000-000000000001",
                                  "00000000-0000-0000-0000-000000000002",
                                  "00000000-0000-0000-0000-000000000003");

    var newPerson = gate.administer()
                        .getAllPersons()
                        .filter(not(p -> predefinedUuids.contains(p.getUuid().toString())))
                        .single()
                        .block();

    assertEquals(new PersonAttributes("1234", "iqjpoqeurqr", "oifuoaisifpa", "naokdaflkdpoa"),
                 newPerson.getAttributes());
  }

  @Test
  @DisplayName("... then one processed")
  public void thenOneProcessed() {
    assertEquals(1, report.getProcessedCount());
  }

  @Test
  @DisplayName("... then zero skipped")
  public void thenOneSkipped() {
    assertEquals(0, report.getSkippedCount());
  }

  @Test
  @DisplayName("... then no errors'")
  public void thenHasNoErrors() {
    assertThat(report.getErrors(), is(empty()));
  }
}
