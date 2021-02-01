package se.fastdev.portal.motivator.bonuses.face.persistence.mongo;

import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;

public final class PersonAttributesDoc {

  public String firstName;
  public String lastName;
  public String location;

  public PersonAttributesDoc(String firstName, String lastName, String location) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.location = location;
  }

  public PersonAttributes toModel() {
    return new PersonAttributes(firstName, lastName, location);
  }

  public static PersonAttributesDoc from(PersonAttributes attributes) {
    return new PersonAttributesDoc(
        attributes.getFirstName(),
        attributes.getLastName(),
        attributes.getLocation()
    );
  }
}
