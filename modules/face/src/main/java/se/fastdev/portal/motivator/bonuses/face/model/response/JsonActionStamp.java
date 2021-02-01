package se.fastdev.portal.motivator.bonuses.face.model.response;

import se.fastdev.portal.motivator.bonuses.core.models.ActionStamp;

public final class JsonActionStamp {

  public final String at;
  public final String by;

  private JsonActionStamp(String at, String by) {
    this.at = at;
    this.by = by;
  }

  public static JsonActionStamp from(ActionStamp stamp) {
    return new JsonActionStamp(
        stamp.getAt().toString(),
        stamp.getBy()
    );
  }
}
