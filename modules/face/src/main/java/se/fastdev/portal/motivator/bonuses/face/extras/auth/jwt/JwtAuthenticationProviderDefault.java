package se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt;

import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;

final class JwtAuthenticationProviderDefault implements JwtAuthenticationProvider {

  private final Logger log = LoggerFactory.getLogger(JwtAuthenticationProviderDefault.class);
  private final Function<HttpHeaders, JwtHandle> makeHandle;

  /* default */ JwtAuthenticationProviderDefault(Function<HttpHeaders, JwtHandle> makeHandle) {
    this.makeHandle = makeHandle;
  }

  public <T> JwtAuthenticationProviderDefault(
      Function<HttpHeaders, T> parseToken,
      Function<T, JwtHandle> createHandle
  ) {
    this(parseToken.andThen(createHandle));
  }

  @Override
  public Authentication fromHeaders(HttpHeaders headers) {
    log.info("Authentication from headers {}", headers);
    return JwtAuthentication.fromHandle(makeHandle.apply(headers));
  }
}
