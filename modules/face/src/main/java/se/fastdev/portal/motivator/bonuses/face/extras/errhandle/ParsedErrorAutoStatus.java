package se.fastdev.portal.motivator.bonuses.face.extras.errhandle;

import java.util.Locale;
import org.springframework.http.HttpStatus;

final class ParsedErrorAutoStatus implements ParsedError {

  private final String message;
  private final HttpStatus status;

  /* default */ ParsedErrorAutoStatus(String message) {
    this.message = message;
    this.status = statusFrom(message);
  }

  @Override
  public HttpStatus serverResponseStatus() {
    return status;
  }

  @Override
  public ErrorResponseBody serverResponseBody() {
    return new ErrorResponseBody(status, message);
  }

  @Override
  public String logMessage() {
    return message;
  }

  private static HttpStatus statusFrom(String message) {
    final var msg = message == null ? "" : message.toLowerCase(Locale.ENGLISH);
    HttpStatus status;

    if (msg.contains("found")) {
      status = HttpStatus.NOT_FOUND;
    } else if (msg.contains("auth")) {
      status = HttpStatus.UNAUTHORIZED;
    } else {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    return status;
  }
}
