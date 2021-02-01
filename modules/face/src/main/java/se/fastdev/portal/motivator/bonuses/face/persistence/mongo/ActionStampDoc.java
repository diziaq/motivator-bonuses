package se.fastdev.portal.motivator.bonuses.face.persistence.mongo;

import java.util.Date;
import se.fastdev.portal.motivator.bonuses.core.models.ActionStamp;

public final class ActionStampDoc {

  public Date at;
  public String by;

  public ActionStampDoc(Date at, String by) {
    this.at = at;
    this.by = by;
  }

  public static ActionStampDoc from(ActionStamp stamp) {
    return new ActionStampDoc(
        Date.from(stamp.getAt()),
        stamp.getBy()
    );
  }
}
