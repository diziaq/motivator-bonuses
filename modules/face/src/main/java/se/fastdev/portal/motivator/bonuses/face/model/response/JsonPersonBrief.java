package se.fastdev.portal.motivator.bonuses.face.model.response;

import se.fastdev.portal.motivator.bonuses.core.models.Person;

public class JsonPersonBrief {

  public final String identifier;
  public final JsonPersonAttributes attributes;
  public final JsonExpenseProfile activeExpenseProfile;

  protected JsonPersonBrief(
      String identifier,
      JsonPersonAttributes attributes,
      JsonExpenseProfile activeExpenseProfile
  ) {
    this.identifier = identifier;
    this.attributes = attributes;
    this.activeExpenseProfile = activeExpenseProfile;
  }

  public static JsonPersonBrief from(Person person) {
    return new JsonPersonBrief(
        person.getUuid().toString(),
        JsonPersonAttributes.from(person.getAttributes()),
        JsonExpenseProfile.from(person.getActiveExpenseProfile())
    );
  }
}
