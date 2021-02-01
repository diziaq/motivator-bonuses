package se.fastdev.portal.motivator.bonuses.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public final class TestPersonAttributesCompareTo {

  @ParameterizedTest
  @MethodSource("examples")
  public void test(int expectedResult, PersonAttributes first, PersonAttributes second) {
    assertEquals(expectedResult, first.compareTo(second));
  }

  public static Stream<Arguments> examples() {
    return Stream.of(
        of(6,
           new PersonAttributes("paklifksgjmrlkstnjm", "gfkhsldkga", "afhjyutteaf"),
           new PersonAttributes("jdjgsyjshdhkshjdjs", "afhjsyaehta", "ghgkjsfmdsa")
        ),
        of(0,
           new PersonAttributes("paklifksgjmrlkstnjm", "sfaihtmewdcpin", "ghtryA"),
           new PersonAttributes("paklifksgjmrlkstnjm", "sfaihtmewdcpin", "ghtryA")
        ),
        of(-6,
           new PersonAttributes("jdjgsyjshdhkshjdjs", "afhjsyaehta", "ghgkjsfmdsa"),
           new PersonAttributes("paklifksgjmrlkstnjm", "gfkhsldkga", "afhjyutteaf")
        ),
        of(-15,
           new PersonAttributes("ITWRUTPOQRTWO", "dkhshksdhk", "i8ruiarytwyt"),
           new PersonAttributes("ITWRUTPOQRTWO", "sjskjskskh", "etyuw57eoed")
        ),
        of(-19,
           new PersonAttributes("tyqtyywklhjms", "erhjgkjnja", "alashkda"),
           new PersonAttributes("tyqtyywklhjms", "erhjgkjnja", "tshathuwa")
        )
    );
  }
}
