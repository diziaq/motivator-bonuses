package se.fastdev.portal.motivator.bonuses.face.model.request;

import javax.validation.constraints.NotBlank;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;
import se.fastdev.portal.motivator.bonuses.face.extras.validation.Name;

public class JsonPersonCreateBid {

  @NotBlank(message = "Expect 'portalId' to be not blank (given: ${validatedValue})")
  public String portalId;

  @Name("firstName")
  public String firstName;

  @Name("lastName")
  public String lastName;

  @Name("location")
  public String location;

  public PersonAttributes toModel() {
    return new PersonAttributes(portalId, firstName, lastName, location);
  }
}
