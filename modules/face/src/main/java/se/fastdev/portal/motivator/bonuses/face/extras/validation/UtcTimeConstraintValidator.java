package se.fastdev.portal.motivator.bonuses.face.extras.validation;

import se.fastdev.portal.motivator.bonuses.toolbox.conversion.InstantUtil;

public class UtcTimeConstraintValidator extends AbstractParsingValidator<UtcTime> {

  public UtcTimeConstraintValidator() {
    super(InstantUtil::parse);
  }
}
