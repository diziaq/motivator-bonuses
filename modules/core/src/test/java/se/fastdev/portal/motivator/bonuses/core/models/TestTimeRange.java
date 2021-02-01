package se.fastdev.portal.motivator.bonuses.core.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public final class TestTimeRange {

  @ParameterizedTest
  @MethodSource("samplesForContains")
  public void testContains(boolean expectedIsContained, String timePoint) {
    var start = Instant.parse("2000-01-12T00:00:00Z");
    var finish = Instant.parse("2010-01-12T00:00:00Z");

    var range = new TimeRange(start, finish);

    assertEquals(expectedIsContained, range.contains(Instant.parse(timePoint)));
  }

  @Test
  public void testGetters() {
    var start = Instant.parse("2009-11-12T00:00:00Z");
    var finish = Instant.parse("2010-01-12T00:00:00Z");

    var range = new TimeRange(start, finish);

    assertAll(
        () -> assertEquals(start, range.getStart()),
        () -> assertEquals(finish, range.getFinish())
    );
  }

  public static Stream<Arguments> samplesForContains() {
    return Stream.of(
        Arguments.of(false, "2000-01-11T15:17:00Z"),
        Arguments.of(false, "2011-01-12T10:10:00Z"),
        Arguments.of(true, "2005-01-12T15:17:00Z"),
        Arguments.of(false, "2000-01-12T00:00:00Z"),
        Arguments.of(false, "2010-01-12T00:00:00Z")
    );
  }
}
