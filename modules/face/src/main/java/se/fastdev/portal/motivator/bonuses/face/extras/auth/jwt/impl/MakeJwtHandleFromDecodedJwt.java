package se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.function.Function;
import se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt.JwtHandle;

public final class MakeJwtHandleFromDecodedJwt implements Function<DecodedJWT, JwtHandle> {

  @Override
  public JwtHandle apply(DecodedJWT decodedJwt) {
    return new JwtHandlePortalV1(
        new JwtPayloadAuth0(decodedJwt)
    );
  }
}
