package se.fastdev.portal.motivator.bonuses.toolbox.conversion;

import static java.util.stream.Collectors.joining;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;
import java.util.stream.Stream;

public final class ThrowablesUtil {

  private ThrowablesUtil() {
    // namespace
  }

  public static String fullDescription(Throwable throwable) {
    final var writer = new StringWriter(4096);
    throwable.printStackTrace(new PrintWriter(writer));
    return writer.toString();
  }

  public static String causesChain(Throwable throwable) {
    return Stream.iterate(throwable, Objects::nonNull, Throwable::getCause)
                 .map(Throwable::toString)
                 .limit(10)
                 .collect(joining("\n"));
  }
}
