package se.fastdev.portal.motivator.bonuses.toolbox.exceptions;

import java.text.MessageFormat;
import java.util.Locale;

public final class CommonException extends RuntimeException {

  private final boolean hasStack;

  private CommonException(boolean hasStack, Throwable cause, String message) {
    super(message, cause);
    this.hasStack = hasStack;
  }

  @Override
  public Throwable fillInStackTrace() {
    return hasStack ? super.fillInStackTrace() : this;
  }

  private static CommonException createException(
      boolean hasStack, Throwable cause,
      String message, Object... parameters
  ) {
    final var paramMessage = completeMessage(message, parameters);
    return new CommonException(hasStack, cause, paramMessage);
  }

  private static String completeMessage(String message, Object... parameters) {
    String result;
    try {
      result = new MessageFormat(message, Locale.ENGLISH).format(parameters);
    } catch (IllegalArgumentException e) {
      result = message + "\n!\n WARNING: Consider fixing broken message template: "
                   + e.getMessage()
                   + ".\n!\n";
    }
    return result;
  }

  public static CommonException thin(Exception cause, String message, Object... parameters) {
    return createException(false, cause, message, parameters);
  }

  public static CommonException thin(String message, Object... parameters) {
    return createException(false, null, message, parameters);
  }

  public static CommonException normal(Exception cause, String message, Object... parameters) {
    return createException(true, cause, message, parameters);
  }

  public static CommonException normal(String message, Object... parameters) {
    return createException(true, null, message, parameters);
  }

  public static CommonException normal(String message) {
    return createException(true, null, message);
  }
}
