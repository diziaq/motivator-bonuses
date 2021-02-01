package se.fastdev.portal.motivator.bonuses.face.extras.errhandle;

import java.util.Collection;
import java.util.function.Function;
import org.springframework.http.HttpStatus;

public interface ParsedError {

  HttpStatus serverResponseStatus();

  ErrorResponseBody serverResponseBody();

  String logMessage();

  // ---

  static ParsedError auto(Exception exception) {
    return new ParsedErrorAutoStatus(exception.getMessage());
  }

  static ParsedError businessLogicFailure(Exception exception) {
    return new ParsedErrorBusinessLogicViolation(exception);
  }

  static <T> ParsedError badInputData(Collection<T> violations, Function<T, String> getMessage) {
    return new ParsedErrorForParamViolation<>(violations, getMessage);
  }

  static <T> ParsedError simple(HttpStatus status, String message) {
    return new ParsedErrorSimple(status, message);
  }

  static <T> ParsedError simple(HttpStatus status, String responseMessage, String logMessage) {
    return new ParsedErrorSimple(status, responseMessage, logMessage);
  }
}
