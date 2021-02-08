package se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public interface JwtHandle {

  JwtPayload payload();

  boolean isValid();

  Collection<GrantedAuthority> authorities();
}
