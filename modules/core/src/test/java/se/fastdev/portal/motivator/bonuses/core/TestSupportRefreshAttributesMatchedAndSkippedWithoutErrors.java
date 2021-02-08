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

@DisplayName("when two given PersonAttributes elements (one is matched and one is skipped) ...")
public final class TestSupportRefreshAttributesMatchedAndSkippedWithoutErrors {

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
               new PersonAttributes("672", "SDosuydifuso", "wjwytjrwjtwyj", "dmwjwmhesnem")),
        person("00000000-0000-0000-0000-000000000002",
               new PersonAttributes("743", "heowjtqpokgq", "yukwrfnrujywrh", "wsjdhktethnsm")),
        person("00000000-0000-0000-0000-000000000003",
               new PersonAttributes("805", "difncqpolp", "kjwrnhtemwjt", "dmsgjsfdhj"))
    );

    var newOfferedAttributes = Stream.of(
        new PersonAttributes("672", "SDosuydifuso", "wjwytjrwjtwyj", "dmwjwmhesnem"),
        new PersonAttributes("743", "NewThingA", "NewThingB", "NewThingC")
    );

    this.report = gate.support()
                      .refreshAttributes(newOfferedAttributes, PersonAttributes::getPortalId)
                      .block();
  }

  @Test
  @DisplayName("... then matched person attributes are updated")
  public void thenProcessedPersonAttrsAreUpdated() {
    var processedPerson = gate.administer()
                              .getPersonById(
                                  UUID.fromString("00000000-0000-0000-0000-000000000002"))
                              .block();

    assertEquals(new PersonAttributes("743", "NewThingA", "NewThingB", "NewThingC"),
                 processedPerson.getAttributes());
  }

  @Test
  @DisplayName("... then unmatched person attributes are not changed")
  public void thenUnmatchedPersonAttrsAreNotChanged() {
    var unmatchedPerson =
        gate.administer()
            .getPersonById(UUID.fromString("00000000-0000-0000-0000-000000000003"))
            .block();

    assertEquals(new PersonAttributes("805", "difncqpolp", "kjwrnhtemwjt", "dmsgjsfdhj"),
                 unmatchedPerson.getAttributes());
  }

  @Test
  @DisplayName("... then skipped person attributes are not changed")
  public void thenSkippedPersonAttrsAreNotChanged() {
    var skippedPerson =
        gate.administer()
            .getPersonById(UUID.fromString("00000000-0000-0000-0000-000000000001"))
            .block();

    assertEquals(new PersonAttributes("672", "SDosuydifuso", "wjwytjrwjtwyj", "dmwjwmhesnem"),
                 skippedPerson.getAttributes());
  }

  @Test
  @DisplayName("... then one processed")
  public void thenOneProcessed() {
    assertEquals(1, report.getProcessedCount());
  }

  @Test
  @DisplayName("... then one skipped")
  public void thenOneSkipped() {
    assertEquals(1, report.getSkippedCount());
  }

  @Test
  @DisplayName("... then no errors'")
  public void thenHasNoErrors() {
    assertThat(report.getErrors(), is(empty()));
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
