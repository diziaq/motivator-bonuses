package se.fastdev.portal.motivator.bonuses.face.extras.validation;

import java.lang.annotation.Annotation;
import java.util.function.Function;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

abstract class AbstractParsingValidator<T extends Annotation>
    implements ConstraintValidator<T, CharSequence> {

  private final Function<String, Object> parseFn;

  protected AbstractParsingValidator(Function<String, Object> parseFn) {
    this.parseFn = parseFn;
  }

  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    return value != null && isParsable(value.toString());
  }

  private boolean isParsable(String value) {
    boolean valueIsParsable;

    try {
      final var parsedObj = parseFn.apply(value);
      valueIsParsable = (parsedObj != null);
    } catch (RuntimeException e) {
      valueIsParsable = false;
    }

    return valueIsParsable;
  }
}
