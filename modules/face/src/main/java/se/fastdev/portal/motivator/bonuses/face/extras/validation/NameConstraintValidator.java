package se.fastdev.portal.motivator.bonuses.face.extras.validation;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameConstraintValidator implements ConstraintValidator<Name, CharSequence> {

  private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Z][a-zA-Z-]{1,69}[a-z]$");

  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    return value != null && NAME_PATTERN.matcher(value).matches();
  }
}
