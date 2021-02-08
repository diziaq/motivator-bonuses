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
           new PersonAttributes("paklifksgjmrlkstnjm", "gfkhsldkga", "afhjyutteaf", "sda"),
           new PersonAttributes("jdjgsyjshdhkshjdjs", "afhjsyaehta", "ghgkjsfmdsa", "sdgdsg")
        ),
        of(0,
           new PersonAttributes("paklifksgjmrlkstnjm", "sfaihtmewdcpin", "ghtryA", "Loco"),
           new PersonAttributes("paklifksgjmrlkstnjm", "sfaihtmewdcpin", "ghtryA", "Loco")
        ),
        of(-6,
           new PersonAttributes("jdjgsyjshdhkshjdjs", "afhjsyaehta", "ghgkjsfmdsa", "gfh"),
           new PersonAttributes("paklifksgjmrlkstnjm", "gfkhsldkga", "afhjyutteaf", "hwtwt")
        ),
        of(-15,
           new PersonAttributes("ITWRUTPOQRTWO", "i8ruiarytwyt", "dkhshksdhk", "out83t"),
           new PersonAttributes("ITWRUTPOQRTWO", "etyuw57eoed", "sjskjskskh", "rwgwey545")
        ),
        of(-19,
           new PersonAttributes("tyqtyywklhjms", "erhjgkjnja", "alashkda", "fdga"),
           new PersonAttributes("tyqtyywklhjms", "erhjgkjnja", "tshathuwa", "fWE")
        )
    );
  }
}
