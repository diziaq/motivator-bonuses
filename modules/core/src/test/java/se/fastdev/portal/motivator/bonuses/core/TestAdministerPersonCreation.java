package se.fastdev.portal.motivator.bonuses.core;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;

public final class TestAdministerPersonCreation {

  private BonusesGate gate;

  @BeforeEach
  public void beforeEach() {
    this.gate = BonusesGate.createGate(BonusesStorage.inMemory());
  }

  @Test
  @DisplayName("when Person created it should have UUID")
  public void createdPersonHasUuid() {
    var attr = new PersonAttributes("fkshjhajga", "gsjgsjsgjsg", "sgjdjkfjkd");
    var person = gate.administer()
                     .createNewPerson(attr)
                     .block();

    assertNotNull(person.getUuid());
  }

  @Test
  @DisplayName("when Person created from attributes it should have those attributes")
  public void createdPersonHasCorrectAttributes() {
    var originalAttr = new PersonAttributes("fdshsrger", "djfijausy", "trukfgasdgaf");
    var person = gate.administer()
                     .createNewPerson(originalAttr)
                     .block();
    var createdAttr = person.getAttributes();

    assertAll(
        () -> assertEquals("fdshsrger", createdAttr.getFirstName()),
        () -> assertEquals("djfijausy", createdAttr.getLastName()),
        () -> assertEquals("trukfgasdgaf", createdAttr.getLocation())
    );
  }

  @Test
  @DisplayName("when Person created it should have appropriate attributes")
  public void createdPersonHasAttributes() {
    var attr = new PersonAttributes("kflduja", "d", "a");
    var person = gate.administer()
                     .createNewPerson(attr)
                     .block();

    assertEquals(attr, person.getAttributes());
  }
}
