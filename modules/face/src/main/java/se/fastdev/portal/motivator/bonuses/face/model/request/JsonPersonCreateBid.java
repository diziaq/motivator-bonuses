package se.fastdev.portal.motivator.bonuses.face.model.request;

import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;
import se.fastdev.portal.motivator.bonuses.face.extras.validation.Name;

public class JsonPersonCreateBid {

  @Name("firstName")
  public String firstName;

  @Name("lastName")
  public String lastName;

  @Name("location")
  public String location;

  public PersonAttributes toModel() {
    return new PersonAttributes(firstName, lastName, location);
  }
}
