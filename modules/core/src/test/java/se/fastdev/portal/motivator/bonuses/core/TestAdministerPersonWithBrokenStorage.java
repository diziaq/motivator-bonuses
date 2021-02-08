package se.fastdev.portal.motivator.bonuses.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fastdev.portal.motivator.bonuses.core.fakes.BonusesStorageBroken;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;

public final class TestAdministerPersonWithBrokenStorage {

  private BonusesGate gate;

  @BeforeEach
  public void beforeEach() {
    this.gate = BonusesGate.createGate(new BonusesStorageBroken());
  }

  @Test
  @DisplayName("when getPersonById got error from storage then exception is BonusesException with 'public message'")
  public void testGetPersonById() {
    var uuid = "f24a2109-75ae-48d2-8010-00008b610000";
    var personMono = gate.administer().getPersonById(UUID.fromString(uuid));

    var exception = assertThrows(BonusesException.class, () -> personMono.block());

    assertEquals("Unable to get person for id f24a2109-75ae-48d2-8010-00008b610000",
                 exception.getMessage());
  }

  @Test
  @DisplayName("when getAllPersons got error from storage then exception is BonusesException with 'public message'")
  public void testGetAllPersons() {
    var personMono = gate.administer().getAllPersons();

    var exception = assertThrows(BonusesException.class, () -> personMono.blockFirst());

    assertEquals("Unable to get all persons", exception.getMessage());
  }

  @Test
  @DisplayName("when createNewPerson got error from storage then exception is BonusesException with 'public message'")
  public void testCreateNewPerson() {
    var personMono = gate.administer().createNewPerson(new PersonAttributes("a", "b", "c", "d"));

    var exception = assertThrows(BonusesException.class, () -> personMono.block());

    assertEquals("Unable to save person (a, b, c, d)", exception.getMessage());
  }
}
