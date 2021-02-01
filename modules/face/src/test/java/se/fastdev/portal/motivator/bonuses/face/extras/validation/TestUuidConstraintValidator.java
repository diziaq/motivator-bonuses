package se.fastdev.portal.motivator.bonuses.face.extras.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestUuidConstraintValidator {

  private UuidConstraintValidator validator;

  @BeforeEach
  public void beforeEach() {
    validator = new UuidConstraintValidator();
  }

  @ParameterizedTest
  @MethodSource("uuidSamples")
  public void testUuids(Boolean expectedResult, String uuidSample) {
    assertEquals(expectedResult, validator.isValid(uuidSample, null));
  }

  public static Stream<Arguments> uuidSamples() {
    return Stream.of(
        of(false, "f7f1db3f b34d 46cb a9f1 396d6458b3b3"),
        of(false, "a"),
        of(true, "f7f1db3f-b34d-46cb-a9f1-396d6458b3b3"),
        of(true, "390B88D4-61e9-42de-80d1-938E5C626C36"),
        of(true, "40D4390B-012e-12ae-40b8-C36"),
        of(true, "90B-012e-12ae-40b8-C36"),
        of(false, "09709e9k-5ee1-11eb-ae93-0242ac130002"),
        of(false, "a157fa1c-d025-4a25-a0d7-c0bddb2a5ba50")
    );
  }
}
