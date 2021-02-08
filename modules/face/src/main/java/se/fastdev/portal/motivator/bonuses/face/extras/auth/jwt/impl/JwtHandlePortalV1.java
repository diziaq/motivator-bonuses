package se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt.impl;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt.JwtHandle;
import se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt.JwtPayload;

final class JwtHandlePortalV1 implements JwtHandle {

  private final JwtPayload payload;
  private final String[] permissions;
  private final long expirationMilli;
  private final long currentMilli;

  /* default */ JwtHandlePortalV1(JwtPayload payload) {
    this(payload, Instant.now().toEpochMilli());
  }

  /* default */ JwtHandlePortalV1(JwtPayload payload, long currentMilli) {
    this.payload = requireNonNull(payload);
    this.permissions = requireNonNull(payload.claim("permission", String[].class));
    this.expirationMilli = requireNonNull(payload.claim("exp", Long.class)) * 1000;
    this.currentMilli = currentMilli;
  }

  @Override
  public JwtPayload payload() {
    return payload;
  }

  @Override
  public boolean isValid() {
    return permissions.length != 0 && expirationMilli > currentMilli;
  }

  @Override
  public Collection<GrantedAuthority> authorities() {
    return Stream.of(permissions)
                 .filter(Objects::nonNull)
                 .map(SimpleGrantedAuthority::new)
                 .collect(Collectors.toUnmodifiableList());
  }
}
