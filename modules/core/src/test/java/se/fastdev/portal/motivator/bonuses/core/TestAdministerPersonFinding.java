package se.fastdev.portal.motivator.bonuses.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fastdev.portal.motivator.bonuses.core.models.Person;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;

public class TestAdministerPersonFinding {

  private BonusesGate gate;

  @BeforeEach
  public void beforeEach() {
    this.gate = BonusesGate.createGate(BonusesStorage.inMemory());
  }

  @Test
  @DisplayName("when Person created it could be found by UUID")
  public void createdPersonCanBeFoundById() {
    var attr = new PersonAttributes("Evgeny", "Ki", "hgjhg");
    var personCreated = gate.administer().createNewPerson(attr).block();

    var personFound = gate.administer().getPersonById(personCreated.getUuid()).block();

    assertEquals(personCreated.getUuid(), personFound.getUuid());
  }

  @Test
  @DisplayName("when searching by unknown UUID then error `Not found`")
  public void unableToFindPersonByUnknownId() {
    var personMono = gate.administer().getPersonById(UUID.randomUUID());

    var exception = assertThrows(BonusesException.class, () -> personMono.block());

    assertThat(exception.getMessage(), startsWith("Unable to get person for id"));
  }

  @Test
  @DisplayName("when multiple Persons created then they all could be found with findAll")
  public void allCreatedPersonsAreFound() {
    var attr1 = new PersonAttributes("hjkyjss", "jfldks", "wuytjkhgj");
    var attr2 = new PersonAttributes("likertjfk", "kjfrdsyjs", "gjdhjsj");
    var attr3 = new PersonAttributes("fahkdkja", "kfkds", "jlifiyutsgf");

    gate.administer().createNewPerson(attr1).block();
    gate.administer().createNewPerson(attr2).block();
    gate.administer().createNewPerson(attr3).block();

    var personsFlux = gate.administer().getAllPersons();
    var attrsList = personsFlux.map(Person::getAttributes).collectList().block();

    assertThat(attrsList, containsInAnyOrder(attr3, attr2, attr1));
  }
}
