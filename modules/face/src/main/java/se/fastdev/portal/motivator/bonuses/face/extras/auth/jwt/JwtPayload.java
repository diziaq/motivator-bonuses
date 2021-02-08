package se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt;

import java.util.Map;

public interface JwtPayload {

  <T> T claim(String name, Class<T> type);

  Map<String, Object> claims();
}
