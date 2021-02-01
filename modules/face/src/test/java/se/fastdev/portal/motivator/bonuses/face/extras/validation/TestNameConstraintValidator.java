package se.fastdev.portal.motivator.bonuses.face.extras.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestNameConstraintValidator {

  private NameConstraintValidator validator;

  @BeforeEach
  public void beforeEach() {
    validator = new NameConstraintValidator();
  }

  @ParameterizedTest
  @MethodSource("nameSamples")
  public void testNames(Boolean expectedResult, String nameSample) {
    assertEquals(expectedResult, validator.isValid(nameSample, null));
  }

  public static Stream<Arguments> nameSamples() {
    return Stream.of(
        of(false, "kaksfgjs"),
        of(false, "M"),
        of(false, "Ab"),
        of(false, "Ab6iuewryow"),
        of(false, "Hois%ydfuois"),
        of(false, "Usapooi ksdfjs"),
        of(false, "ASdasd@aghjryhweuopo"),
        of(false, "ASdasd.Gghjryhwe.Juopo"),
        of(true, "Aba"),
        of(true, "Dljkkfsljljl"),
        of(true, "SkopaoiuapioiFoiopiopm"),
        of(true, "Riouwriou-EewrWEkoih-Eldskhfhskj"),
        of(true, "AdsfgkdsjfakjfjAskhgkjdsfBekmVefspkhfgwkfksgnmfpowngrff"),
        of(false, "NmfpowngrffAskhgkjdsfBekmVefspkhFgwkfksgioypasfoAdsfgkdsjfakjfjiyapuasfo"),
        of(true, "HisuefhsldfDoiytyuiuwqkwejrkwlMerewoprlwektjpeWtmewioktfGoiytewoirowwug")
    );
  }
}
