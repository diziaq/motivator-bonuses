package se.fastdev.portal.motivator.bonuses.face.extras.errhandle;

import static java.util.stream.Collectors.joining;

import java.util.Collection;
import java.util.function.Function;
import org.springframework.http.HttpStatus;

final class ParsedErrorForParamViolation<T> implements ParsedError {

  private final Collection<T> violations;
  private final Function<T, String> getMessage;
  private static final int ONE = 1;

  /* default */ ParsedErrorForParamViolation(
      Collection<T> violations,
      Function<T, String> getMessage
  ) {
    this.violations = violations;
    this.getMessage = getMessage;
  }

  private String wrap(String message) {
    final var size = violations.size();
    String result;

    if (size == ONE) {
      result = "Format violated; " + message;
    } else if (size > ONE) {
      result = "Format violated; " + message + "; Violations count: " + size;
    } else {
      result = "Unknown violation of input format";
    }

    return result;
  }

  @Override
  public HttpStatus serverResponseStatus() {
    return HttpStatus.BAD_REQUEST;
  }

  @Override
  public ErrorResponseBody serverResponseBody() {
    final var iter = violations.iterator();
    final var message = iter.hasNext() ? getMessage.apply(iter.next()) : "";

    return new ErrorResponseBody(HttpStatus.BAD_REQUEST, wrap(message));
  }

  @Override
  public String logMessage() {
    return violations.stream().map(getMessage).collect(joining("; "));
  }
}
