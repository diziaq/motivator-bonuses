package se.fastdev.portal.motivator.bonuses.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fastdev.portal.motivator.bonuses.core.models.BulkProcessReport;
import se.fastdev.portal.motivator.bonuses.core.models.Person;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;

@DisplayName("when one given PersonAttributes element is matched ...")
public final class TestSupportRefreshAttributesOneMatchedWithoutErrors {

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
               new PersonAttributes("146", "Akdhfjsaf", "Baisyfoidsf", "Csietyiuwtrw")),
        person("00000000-0000-0000-0000-000000000002",
               new PersonAttributes("224", "Safoudisod", "Giyeiurweur", "Dafsfsdfg")),
        person("00000000-0000-0000-0000-000000000003",
               new PersonAttributes("854", "Ddfgsakti", "Soiyuystyoi", "Fiwueryiw"))
    );

    var newOfferedAttributes =
        Stream.of(new PersonAttributes("146", "NewThingA", "NewThingB", "NewThingC"));

    report = gate.support()
                 .refreshAttributes(
                     newOfferedAttributes,
                     PersonAttributes::getPortalId
                 )
                 .block();
  }

  @Test
  @DisplayName("... then processed person attributes are updated")
  public void thenProcessedPersonAttrsAreUpdated() {
    var processedPerson = gate.administer()
                              .getPersonById(
                                  UUID.fromString("00000000-0000-0000-0000-000000000001"))
                              .block();

    assertEquals(new PersonAttributes("146", "NewThingA", "NewThingB", "NewThingC"),
                 processedPerson.getAttributes());
  }

  @Test
  @DisplayName("... then unmatched person attributes are not changed")
  public void thenUnmatchedPersonAttrsAreNotChanged() {
    var unmatchedPerson =
        gate.administer()
            .getPersonById(UUID.fromString("00000000-0000-0000-0000-000000000002"))
            .block();

    assertEquals(new PersonAttributes("224", "Safoudisod", "Giyeiurweur", "Dafsfsdfg"),
                 unmatchedPerson.getAttributes());
  }

  @Test
  @DisplayName("... then no errors'")
  public void thenHasNoErrors() {
    assertThat(report.getErrors(), is(empty()));
  }

  @Test
  @DisplayName("... then zero skipped")
  public void thenHasNoSkipped() {
    assertEquals(0, report.getSkippedCount());
  }

  @Test
  @DisplayName("... then one processed")
  public void thenOneSuccessful() {
    assertEquals(1, report.getProcessedCount());
  }

  private static void addToStorage(BonusesStorage storage, Person... persons) {
    Stream.of(persons)
          .map(storage::save)
          .forEach(m -> m.block());
  }

  private static Person person(String uuid, PersonAttributes attributes) {
    return new Person(UUID.fromString(uuid), attributes);
  }
}
