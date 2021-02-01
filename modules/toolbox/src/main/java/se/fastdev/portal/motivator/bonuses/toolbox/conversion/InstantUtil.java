package se.fastdev.portal.motivator.bonuses.toolbox.conversion;

import java.time.Instant;

public final class InstantUtil {

  private InstantUtil() {
    // namespace
  }

  public static Instant parse(String value) {
    final var normalValue = value.endsWith("Z") ? value : (value + "Z");
    return Instant.parse(normalValue);
  }
}
