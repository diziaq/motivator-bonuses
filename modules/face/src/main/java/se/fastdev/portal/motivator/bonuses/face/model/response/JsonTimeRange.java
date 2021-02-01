package se.fastdev.portal.motivator.bonuses.face.model.response;

import java.time.Instant;
import se.fastdev.portal.motivator.bonuses.core.models.TimeRange;

public final class JsonTimeRange {

  public final String start;
  public final String finish;

  private JsonTimeRange(String start, String finish) {
    this.start = start;
    this.finish = finish;
  }

  public TimeRange toModel() {
    return new TimeRange(
        Instant.parse(start),
        Instant.parse(finish)
    );
  }

  public static JsonTimeRange from(TimeRange periodOfActivity) {
    return new JsonTimeRange(
        periodOfActivity.getStart().toString(),
        periodOfActivity.getFinish().toString()
    );
  }
}
