package se.fastdev.portal.motivator.bonuses.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public final class TestExpenseType {

  @ParameterizedTest
  @MethodSource("samplesForValueOf")
  public void testByName(ExpenseType expectedType, String name) {
    assertSame(expectedType, ExpenseType.Companion.byName(name));
  }

  @ParameterizedTest
  @MethodSource("samplesForToString")
  public void testToString(String expectedString, ExpenseType type) {
    assertEquals(expectedString, type.toString());
  }

  public static Stream<Arguments> samplesForValueOf() {
    return Stream.of(
        Arguments.of(ExpenseType.SPORTS, "sports"),
        Arguments.of(ExpenseType.SPORTS, "Sports"),
        Arguments.of(ExpenseType.SPORTS, "SPORTS"),
        Arguments.of(ExpenseType.SPORTS, "spORtS"),
        Arguments.of(ExpenseType.HEALTHCARE, "healthcare"),
        Arguments.of(ExpenseType.HEALTHCARE, "Healthcare"),
        Arguments.of(ExpenseType.HEALTHCARE, "HealthCare"),
        Arguments.of(ExpenseType.HEALTHCARE, "HEALTHCARE")
    );
  }

  public static Stream<Arguments> samplesForToString() {
    return Stream.of(
        Arguments.of("Sports", ExpenseType.SPORTS),
        Arguments.of("Healthcare", ExpenseType.HEALTHCARE)
    );
  }
}
