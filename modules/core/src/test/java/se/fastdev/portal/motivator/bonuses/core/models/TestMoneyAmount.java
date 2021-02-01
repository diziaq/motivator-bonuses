package se.fastdev.portal.motivator.bonuses.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public final class TestMoneyAmount {

  @ParameterizedTest
  @MethodSource("examples")
  public void testBigIntConstructor(int expectedInt, BigDecimal decimalAmount) {
    assertEquals(expectedInt, new MoneyAmount(decimalAmount).asInt());
  }

  public static Stream<Arguments> examples() {
    return Stream.of(
        Arguments.of(1000, new BigDecimal("10")),
        Arguments.of(24101, new BigDecimal("241.01")),
        Arguments.of(0, new BigDecimal("0")),
        Arguments.of(68738345, new BigDecimal("687383.45")),
        Arguments.of(-324363556, new BigDecimal("-3243635.56"))
    );
  }
}
