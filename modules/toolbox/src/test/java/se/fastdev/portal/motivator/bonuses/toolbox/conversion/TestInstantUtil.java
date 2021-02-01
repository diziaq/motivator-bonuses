package se.fastdev.portal.motivator.bonuses.toolbox.conversion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestInstantUtil {

  @ParameterizedTest
  @MethodSource("samples")
  public void testParse(Instant expectedInstant, String parsableValue) {
    assertEquals(expectedInstant, InstantUtil.parse(parsableValue));
  }

  public static Stream<Arguments> samples() {
    return Stream.of(
        Arguments.of(InstantUtil.parse("2020-01-01T00:03:12.888Z"), "2020-01-01T00:03:12.888Z"),
        Arguments.of(InstantUtil.parse("1980-05-01T00:03:12.213Z"), "1980-05-01T00:03:12.213"),
        Arguments.of(InstantUtil.parse("1999-12-11T01:53:07Z"), "1999-12-11T01:53:07"),
        Arguments.of(InstantUtil.parse("1901-10-08T07:19:33Z"), "1901-10-08T07:19:33Z")
    );
  }
}
