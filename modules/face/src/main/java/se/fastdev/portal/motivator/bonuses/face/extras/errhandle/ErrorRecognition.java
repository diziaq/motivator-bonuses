package se.fastdev.portal.motivator.bonuses.face.extras.errhandle;

import se.fastdev.portal.motivator.bonuses.toolbox.control.TypeAwareControl;

public interface ErrorRecognition {

  ParsedError parse(Exception exception);

  static ErrorRecognition fromControl(TypeAwareControl<Exception, ParsedError> control) {
    return control::manage;
  }
}
