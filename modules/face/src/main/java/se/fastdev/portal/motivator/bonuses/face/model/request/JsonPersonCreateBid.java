package se.fastdev.portal.motivator.bonuses.face.model.request;

import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;

public class JsonPersonCreateBid {

  public String firstName;

  public String lastName;

  public String location;

  public PersonAttributes toModel() {
    return new PersonAttributes(firstName, lastName, location);
  }
}
