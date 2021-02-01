package se.fastdev.portal.motivator.bonuses.toolbox.control.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fastdev.portal.motivator.bonuses.toolbox.control.SampleClassesForTypeAwareControl.Input;
import se.fastdev.portal.motivator.bonuses.toolbox.control.SampleClassesForTypeAwareControl.Input1328562;
import se.fastdev.portal.motivator.bonuses.toolbox.control.SampleClassesForTypeAwareControl.Input2482658;
import se.fastdev.portal.motivator.bonuses.toolbox.control.SampleClassesForTypeAwareControl.Input3435646;
import se.fastdev.portal.motivator.bonuses.toolbox.control.SampleClassesForTypeAwareControl.Output;

// these cases appear only when client misuse the hidden constructor
// or a public builder is error prone
public class TestTypeAwareControlMisuse {

  @Test
  @DisplayName("should throw when handlers are inconsistent (some classes not covered)")
  public void whenSomeClassesNotCovered() {
    var management = new TypeAwareControlSequential<Input, Output>(
        List.of(
            new TypeAwareHandlerTakesAssignable<>(Input1328562.class, x -> new Output("handle 1"))
        )
    );

    var exception = assertThrows(Exception.class, () -> management.manage(new Input2482658()));

    assertAll(
        () -> assertThat(exception.getMessage(), startsWith("Unable to find handler for type")),
        () -> assertThat(exception.getMessage(), containsString("Input2482658")),
        () -> assertThat(exception.getMessage(), endsWith("Check handlers integrity."))
    );
  }

  @Test
  @DisplayName("should throw when handlers are inconsistent (empty list of handlers)")
  public void whenNoHandlers() {
    var management = new TypeAwareControlSequential<Input, Output>(
        Collections.emptyList()
    );

    var exception = assertThrows(Exception.class, () -> management.manage(new Input3435646()));

    assertAll(
        () -> assertThat(exception.getMessage(), startsWith("Unable to find handler for type")),
        () -> assertThat(exception.getMessage(), containsString("Input3435646")),
        () -> assertThat(exception.getMessage(), endsWith("Check handlers integrity."))
    );
  }
}
