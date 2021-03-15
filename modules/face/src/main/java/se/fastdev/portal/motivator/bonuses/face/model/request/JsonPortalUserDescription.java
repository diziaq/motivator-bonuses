package se.fastdev.portal.motivator.bonuses.face.model.request;

import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;

public final class JsonPortalUserDescription {

  public String email;
  public String name;
  public String location;

  public PersonAttributes toModel() {
    final var portalId = (email == null) ? "portal-id-synthetic (" + name + ")" : email;
    final var portalUsername = (name == null) ? "Unknown Username" : name;
    final var nameParts = splitName(portalUsername);

    return new PersonAttributes(portalId, nameParts[0], nameParts[1], location);
  }

  private static String[] splitName(String name) {
    final var nameParts = name.split(" ");

    return (nameParts.length == 2)
               ? nameParts
               : new String[] {name, "UNKNOWN"};
  }
}
