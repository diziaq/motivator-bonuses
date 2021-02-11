package se.fastdev.portal.motivator.bonuses.face.model.response;

import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;

public final class JsonPersonAttributes {

  public final String portalId;
  public final String firstName;
  public final String lastName;
  public final String location;

  public JsonPersonAttributes(String portalId, String firstName, String lastName, String location) {
    this.portalId = portalId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.location = location;
  }

  public static JsonPersonAttributes from(PersonAttributes attributes) {
    return new JsonPersonAttributes(
        attributes.getPortalId(),
        attributes.getFirstName(),
        attributes.getLastName(),
        attributes.getLocation()
    );
  }
}
