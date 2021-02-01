package se.fastdev.portal.motivator.bonuses.face.extras.errhandle;

import org.springframework.http.HttpStatus;

final class ParsedErrorBusinessLogicViolation extends ParsedErrorDefault {

  /* default */ ParsedErrorBusinessLogicViolation(Exception exception) {
    super(HttpStatus.BAD_REQUEST, exception);
  }

  @Override
  public HttpStatus serverResponseStatus() {
    return HttpStatus.BAD_REQUEST;
  }

  @Override
  public ErrorResponseBody serverResponseBody() {
    return new ErrorResponseBody(HttpStatus.BAD_REQUEST, throwable.getMessage());
  }
}
