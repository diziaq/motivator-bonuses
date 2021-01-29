package se.fastdev.portal.motivator.bonuses.toolbox.exceptions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCommonException {

  @Test
  @DisplayName("when 'normal' has filled its stack then is has NOT EMPTY stack, given message and null cause")
  public void normalWithStack() {
    var exception = CommonException.normal("lvjfoiakfcsajf");

    exception.fillInStackTrace();

    assertAll(
        () -> assertEquals("lvjfoiakfcsajf", exception.getMessage()),
        () -> assertNull(exception.getCause()),
        () -> assertThat(exception.getStackTrace().length, greaterThan(1))
    );
  }

  @Test
  @DisplayName("when 'normal' has NOT filled its stack then it has EMPTY stack, given message and null cause")
  public void normalWithNoStack() {
    var exception = CommonException.normal("foouozfja98asf87asf");

    assertAll(
        () -> assertEquals("foouozfja98asf87asf", exception.getMessage()),
        () -> assertNull(exception.getCause()),
        () -> assertEquals(0, exception.getStackTrace().length)
    );
  }

  @Test
  @DisplayName("when 'normal' created with cause then it has given message and given cause")
  public void normalWithCause() {
    var cause = new RuntimeException("sythjuyijngjte");
    var exception = CommonException.normal(cause, "hdkeluujtmhdtsrj");

    assertAll(
        () -> assertEquals("hdkeluujtmhdtsrj", exception.getMessage()),
        () -> assertSame(cause, exception.getCause())
    );
  }

  @Test
  @DisplayName("when 'normal' created with parameters then it has interpolated message")
  public void normalWithParameterizedMessage() {
    var exception = CommonException
                        .normal("sakflasdjflakg {0} agddsgagd {1} asfsasf",
                                1234, "HHH");

    assertEquals("sakflasdjflakg 1,234 agddsgagd HHH asfsasf", exception.getMessage());
  }

  @Test
  @DisplayName("when 'thin' has filled its stack then it still has EMPTY stack, given message and null cause")
  public void thinWithStack() {
    var exception = CommonException.thin("lafpogauoiaipagia");

    exception.fillInStackTrace();

    assertAll(
        () -> assertEquals("lafpogauoiaipagia", exception.getMessage()),
        () -> assertNull(exception.getCause()),
        () -> assertEquals(0, exception.getStackTrace().length)
    );
  }

  @Test
  @DisplayName("when 'thin' has NOT filled its stack ten it has EMPTY stack, given message and null cause")
  public void thinWithNoStack() {
    var exception = CommonException.thin("simple");

    assertAll(
        () -> assertEquals("simple", exception.getMessage()),
        () -> assertNull(exception.getCause()),
        () -> assertEquals(0, exception.getStackTrace().length)
    );
  }

  @Test
  @DisplayName("when 'thin' created with cause then it has given message and given cause")
  public void thinWithCause() {
    var cause = new RuntimeException("kdgadkgjadlk");
    var exception = CommonException.thin(cause, "dhjskyjaerhjswty");

    assertAll(
        () -> assertEquals("dhjskyjaerhjswty", exception.getMessage()),
        () -> assertSame(cause, exception.getCause())
    );
  }

  @Test
  @DisplayName("when 'thin' created with parameters then it has interpolated message")
  public void thinWithParameterizedMessage() {
    var exception = CommonException
                        .thin("fhjkkdjafhs {0} adjsjsg {1} jsgsfjgs {2} dsfsfg",
                              "ABC", 1234, "HHH");

    assertEquals("fhjkkdjafhs ABC adjsjsg 1,234 jsgsfjgs HHH dsfsfg", exception.getMessage());
  }

  @Test
  @DisplayName("when 'thin' created with malformed message then its  message contains warning")
  public void thinWithMalformedMessage() {
    var exception = CommonException
                        .thin("fdshwrthsthjw {} adhkukjatmukyjern",
                              "ABC", 1234, "HHH");

    var expectedMessage = "fdshwrthsthjw {} adhkukjatmukyjern"
                              + "\n!"
                              + "\n WARNING: Consider fixing broken message template: can't parse argument number: ."
                              + "\n!\n";

    assertEquals(expectedMessage, exception.getMessage());
  }
}
