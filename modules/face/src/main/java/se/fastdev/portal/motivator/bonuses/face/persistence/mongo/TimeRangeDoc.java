package se.fastdev.portal.motivator.bonuses.face.persistence.mongo;

import java.util.Date;
import se.fastdev.portal.motivator.bonuses.core.models.TimeRange;

public final class TimeRangeDoc {

  public Date start;
  public Date finish;

  public TimeRangeDoc(Date start, Date finish) {
    this.start = start;
    this.finish = finish;
  }

  public TimeRange toModel() {
    return new TimeRange(start.toInstant(), finish.toInstant());
  }

  public static TimeRangeDoc from(TimeRange periodOfActivity) {
    return new TimeRangeDoc(
        Date.from(periodOfActivity.getStart()),
        Date.from(periodOfActivity.getFinish())
    );
  }
}
