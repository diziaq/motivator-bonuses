package se.fastdev.portal.motivator.bonuses.face.extras.errhandle;

import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.http.HttpStatus;

final class ParsedErrorForParamViolation<T> implements ParsedError {

  private static final int ONE = 1;

  private final List<String> violations;

  /* default */ ParsedErrorForParamViolation(
      Collection<T> violations,
      Function<T, String> getMessage
  ) {
    this.violations = violations.stream().map(getMessage).sorted().collect(toUnmodifiableList());
  }

  @Override
  public HttpStatus serverResponseStatus() {
    return HttpStatus.BAD_REQUEST;
  }

  @Override
  public ErrorResponseBody serverResponseBody() {
    final var message = wrappedMessage(violations.size(), () -> violations.get(0));
    return new ErrorResponseBody(HttpStatus.BAD_REQUEST, message);
  }

  @Override
  public String logMessage() {
    return String.join("; ", violations);
  }

  // ---

  private static String wrappedMessage(int count, Supplier<String> suppFirst) {
    String result;

    if (count == ONE) {
      result = "Format violated; " + suppFirst.get();
    } else if (count > ONE) {
      result = "Format violated; " + suppFirst.get() + "; Violations count: " + count;
    } else {
      result = "Unknown violation of input format";
    }

    return result;
  }
}
