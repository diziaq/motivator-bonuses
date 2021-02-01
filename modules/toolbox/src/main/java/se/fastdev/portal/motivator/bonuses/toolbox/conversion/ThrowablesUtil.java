package se.fastdev.portal.motivator.bonuses.toolbox.conversion;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ThrowablesUtil {

  private ThrowablesUtil() {
    // namespace
  }

  public static String fullDescription(Throwable throwable) {
    final var writer = new StringWriter(4096);
    throwable.printStackTrace(new PrintWriter(writer));
    return writer.toString();
  }
}
