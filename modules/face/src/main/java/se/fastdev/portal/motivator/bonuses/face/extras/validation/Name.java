package se.fastdev.portal.motivator.bonuses.face.extras.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NameConstraintValidator.class)
public @interface Name {

  String value() default "value";

  String message() default "Expect '{value}' to be a name (given: ${validatedValue})";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
