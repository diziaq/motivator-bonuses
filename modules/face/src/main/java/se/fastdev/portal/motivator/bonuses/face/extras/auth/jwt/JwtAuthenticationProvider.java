package se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt;

import java.util.function.Function;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt.impl.MakeDecodedJwtFromAuthorizationHeader;
import se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt.impl.MakeJwtHandleFromDecodedJwt;

public interface JwtAuthenticationProvider {

  Authentication fromHeaders(HttpHeaders headers);

  default Authentication fromRequest(ServerHttpRequest request) {
    return fromHeaders(request.getHeaders());
  }

  static <T> JwtAuthenticationProvider createDefault() {
    return with(
        new MakeDecodedJwtFromAuthorizationHeader(),
        new MakeJwtHandleFromDecodedJwt()
    );
  }

  static <T> JwtAuthenticationProvider with(
      Function<HttpHeaders, T> parseToken,
      Function<T, JwtHandle> createHandle
  ) {
    return new JwtAuthenticationProviderDefault(parseToken, createHandle);
  }
}
