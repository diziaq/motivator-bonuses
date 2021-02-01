package se.fastdev.portal.motivator.bonuses.face.extras.validation;

import java.util.UUID;

public class UuidConstraintValidator extends AbstractParsingValidator<Uuid> {

  public UuidConstraintValidator() {
    super(UUID::fromString);
  }
}
