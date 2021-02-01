package se.fastdev.portal.motivator.bonuses.toolbox.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import se.fastdev.portal.motivator.bonuses.toolbox.control.SampleClassesForTypeAwareControl.Input;
import se.fastdev.portal.motivator.bonuses.toolbox.control.SampleClassesForTypeAwareControl.Input1328562;
import se.fastdev.portal.motivator.bonuses.toolbox.control.SampleClassesForTypeAwareControl.Input2482658;
import se.fastdev.portal.motivator.bonuses.toolbox.control.SampleClassesForTypeAwareControl.Input3435646;
import se.fastdev.portal.motivator.bonuses.toolbox.control.SampleClassesForTypeAwareControl.Input4749832;
import se.fastdev.portal.motivator.bonuses.toolbox.control.SampleClassesForTypeAwareControl.Output;

public class TestTypeAwareControlFallbackOnly {

  static TypeAwareControl<Input, Output> management;

  @ParameterizedTest
  @MethodSource("samples")
  @DisplayName("should get fallback handler for non-null value")
  public void handleNonNull(Input input) {
    var output = management.manage(input);

    assertEquals("fallback", output.message);
  }

  @Test
  @DisplayName("should throw for null value")
  public void throwWhenNull() {
    var exception = assertThrows(NullPointerException.class, () -> management.manage(null));

    assertEquals("null values must be handled separately", exception.getMessage());
  }

  @BeforeAll
  public static void before() {
    management = TypeAwareControl.perAssignable(Input.class, Output.class)
                                 .fallback(x -> new Output("fallback"));
  }

  public static Stream<Arguments> samples() {
    return Stream
               .of(
                   new Input1328562(),
                   new Input2482658(),
                   new Input3435646(),
                   new Input4749832()
               )
               .map(Arguments::of);
  }
}
