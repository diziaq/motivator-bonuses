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

public class TestTypeAwareControlBasic {

  static TypeAwareControl<Input, Output> management;

  @ParameterizedTest
  @MethodSource("samples")
  @DisplayName("should get appropriate handler for non-null value")
  public void handleNonNull(Input input, String expectedResultMessage) {
    var output = management.manage(input);

    assertEquals(expectedResultMessage, output.message);
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
                                 .with(Input1328562.class,
                                       x -> new Output(x.first() + ":handle 1"))
                                 .with(Input2482658.class,
                                       x -> new Output(x.second() + ":handle 2"))
                                 .with(Input3435646.class,
                                       x -> new Output(x.third() + ":handle 3"))
                                 .fallback(x -> new Output(x.basic() + ":fallback"));
  }

  public static Stream<Arguments> samples() {
    return Stream.of(
        Arguments.of(new Input1328562(), "first-1328562:handle 1"),
        Arguments.of(new Input2482658(), "second-2482658:handle 2"),
        Arguments.of(new Input3435646(), "first-3435646:handle 1"),
        Arguments.of(new Input4749832(), "basic-4749832:fallback")
    );
  }
}
