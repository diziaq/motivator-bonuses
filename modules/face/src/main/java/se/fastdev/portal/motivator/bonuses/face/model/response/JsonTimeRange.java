package se.fastdev.portal.motivator.bonuses.face.model.response;

import se.fastdev.portal.motivator.bonuses.core.models.TimeRange;
import se.fastdev.portal.motivator.bonuses.face.extras.validation.UtcTime;
import se.fastdev.portal.motivator.bonuses.toolbox.conversion.InstantUtil;

public final class JsonTimeRange {

  @UtcTime("start")
  public final String start;

  @UtcTime("finish")
  public final String finish;

  private JsonTimeRange(String start, String finish) {
    this.start = start;
    this.finish = finish;
  }

  public TimeRange toModel() {
    return new TimeRange(
        InstantUtil.parse(start),
        InstantUtil.parse(finish)
    );
  }

  public static JsonTimeRange from(TimeRange periodOfActivity) {
    return new JsonTimeRange(
        periodOfActivity.getStart().toString(),
        periodOfActivity.getFinish().toString()
    );
  }
}
