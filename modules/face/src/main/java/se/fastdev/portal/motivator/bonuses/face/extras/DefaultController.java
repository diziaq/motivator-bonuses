package se.fastdev.portal.motivator.bonuses.face.extras;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
@Validated
public @interface DefaultController {
  // empty
}
