package se.fastdev.portal.motivator.bonuses.face.config;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import se.fastdev.portal.motivator.bonuses.face.extras.errhandle.ErrorRecognition;
import se.fastdev.portal.motivator.bonuses.face.extras.errhandle.ErrorResponseBody;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private final Logger log;
  private final ErrorRecognition errorRecognition;

  public GlobalExceptionHandler(Logger log, ErrorRecognition errorRecognition) {
    this.log = log;
    this.errorRecognition = errorRecognition;
  }

  @ExceptionHandler(Exception.class)
  public ErrorResponseBody handleException(
      Exception exception,
      ServerWebExchange webExchange
  ) {
    final var error = errorRecognition.parse(exception);

    log.error(error.logMessage());

    webExchange.getResponse().setStatusCode(error.serverResponseStatus());

    return error.serverResponseBody();
  }
}
