package se.fastdev.portal.motivator.bonuses.face.model.response;

import java.util.List;
import se.fastdev.portal.motivator.bonuses.core.models.Person;
import se.fastdev.portal.motivator.bonuses.toolbox.conversion.ListsUtil;

public final class JsonPersonFull extends JsonPersonBrief {

  public final List<JsonExpenseProfile> expenseProfilesHistory;

  private JsonPersonFull(
      String identifier,
      JsonPersonAttributes attributes,
      JsonExpenseProfile activeExpenseProfile,
      List<JsonExpenseProfile> expenseProfilesHistory
  ) {
    super(identifier, attributes, activeExpenseProfile);
    this.expenseProfilesHistory = expenseProfilesHistory;
  }

  public static JsonPersonFull from(Person person) {
    return new JsonPersonFull(
        person.getUuid().toString(),
        JsonPersonAttributes.from(person.getAttributes()),
        JsonExpenseProfile.from(person.getActiveExpenseProfile()),
        ListsUtil.translate(person.getExpenseProfilesHistory(), JsonExpenseProfile::from)
    );
  }
}
