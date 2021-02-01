package se.fastdev.portal.motivator.bonuses.face.extras.errhandle;

import org.springframework.http.HttpStatus;
import se.fastdev.portal.motivator.bonuses.toolbox.conversion.ThrowablesUtil;

class ParsedErrorDefault implements ParsedError {

  private final HttpStatus status;
  protected final Throwable throwable;

  /* default */ ParsedErrorDefault(Throwable throwable) {
    this(HttpStatus.INTERNAL_SERVER_ERROR, throwable);
  }

  /* default */ ParsedErrorDefault(HttpStatus status, Throwable throwable) {
    this.status = status;
    this.throwable = throwable;
  }

  @Override
  public HttpStatus serverResponseStatus() {
    return status;
  }

  @Override
  public ErrorResponseBody serverResponseBody() {
    return new ErrorResponseBody(status, "hidden info");
  }

  @Override
  public String logMessage() {
    return ThrowablesUtil.fullDescription(throwable);
  }
}
