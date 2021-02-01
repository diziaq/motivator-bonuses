package se.fastdev.portal.motivator.bonuses.face.extras.errhandle;

import org.springframework.http.HttpStatus;

final class ParsedErrorSimple implements ParsedError {

  private final HttpStatus status;
  private final String logMessage;
  private final String responseMessage;

  /* default */ ParsedErrorSimple(HttpStatus status, String message) {
    this(status, message, message);
  }

  /* default */ ParsedErrorSimple(HttpStatus status, String responseMessage, String logMessage) {
    this.status = status;
    this.responseMessage = responseMessage;
    this.logMessage = logMessage;
  }

  @Override
  public HttpStatus serverResponseStatus() {
    return status;
  }

  @Override
  public ErrorResponseBody serverResponseBody() {
    return new ErrorResponseBody(status, responseMessage);
  }

  @Override
  public String logMessage() {
    return logMessage;
  }
}
