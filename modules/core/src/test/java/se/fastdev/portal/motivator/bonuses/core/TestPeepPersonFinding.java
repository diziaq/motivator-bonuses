package se.fastdev.portal.motivator.bonuses.core;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fastdev.portal.motivator.bonuses.core.models.Person;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;

public class TestPeepPersonFinding {

  private BonusesGate gate;

  @BeforeEach
  public void beforeEach() {
    var storage = BonusesStorage.inMemory();
    this.gate = BonusesGate.createGate(storage);

    addToStorage(storage,
                 person("00000000-0000-0000-0000-002635247209",
                        new PersonAttributes("4684", "skdksktjskydhjdu", "afjsha", "gjtrjehatesj")),
                 person("00000000-0000-0000-0000-903262393202",
                        new PersonAttributes("8943", "gskdksask", "asdasdesafd", "fjgdsjgsjf")),
                 person("00000000-0000-0000-0000-532862886904",
                        new PersonAttributes("6832", "sgsjdlkwsatjs", "gjikjrgwtuk", "tuirutrjyte"))
    );
  }

  @Test
  @DisplayName("when Person created it could be found by portalId")
  public void ownedPersonCanBeFoundByPortalId() {
    var personFound = gate.peep("8943").getOwnedPerson().block();

    assertAll(
        () -> assertEquals("00000000-0000-0000-0000-903262393202",
                           personFound.getUuid().toString()),
        () -> assertEquals(new PersonAttributes("8943", "gskdksask", "asdasdesafd", "fjgdsjgsjf"),
                           personFound.getAttributes())
    );
  }

  @Test
  @DisplayName("when Person has unknown portalId it could not be found by portalId")
  public void unknownPersonCanNotBeFoundByPortalId() {
    var personMono = gate.peep("9897").getOwnedPerson();

    var exception = assertThrows(BonusesException.class, () -> personMono.block());
    assertEquals("Unable to get person for portalId 9897", exception.getMessage());
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
