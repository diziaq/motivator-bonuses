package se.fastdev.portal.motivator.bonuses.face.extras.errhandle;

import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.http.HttpStatus;

public class ErrorResponseBody {

  public final int status;
  public final Timestamp timestamp;
  public final String message;

  /* default */ ErrorResponseBody(int statusCode, Timestamp timestamp, String message) {
    this.status = statusCode;
    this.timestamp = timestamp;
    this.message = message;
  }

  /* default */ ErrorResponseBody(HttpStatus status, String message) {
    this(status.value(), message);
  }

  /* default */ ErrorResponseBody(int statusCode, String message) {
    this(statusCode, Timestamp.from(Instant.now()), message);
  }
}
