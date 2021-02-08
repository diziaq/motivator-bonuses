package se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt.JwtPayload;

final class JwtPayloadAuth0 implements JwtPayload {

  private final DecodedJWT decodedJwt;

  /* default */ JwtPayloadAuth0(DecodedJWT decodedJwt) {
    this.decodedJwt = Objects.requireNonNull(decodedJwt, "decoded jwt");
  }

  @Override
  public <T> T claim(String claimName, Class<T> claimType) {
    return decodedJwt.getClaim(claimName).as(claimType);
  }

  @Override
  public Map<String, Object> claims() {
    return decodedJwt.getClaims().entrySet().stream()
                     .collect(
                         Collectors.toMap(
                             e -> e.getKey(),
                             e -> e.getValue().as(Object.class)
                         ));
  }
}
