package se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

final class JwtAuthentication extends AbstractAuthenticationToken {

  private final JwtHandle jwtHandle;

  private JwtAuthentication(JwtHandle jwtHandle) {
    super(jwtHandle.authorities());
    this.jwtHandle = jwtHandle;
  }

  @Override
  public JwtPayload getPrincipal() {
    return jwtHandle.payload();
  }

  @Override
  public boolean isAuthenticated() {
    return jwtHandle.isValid();
  }

  @Override
  public Object getCredentials() {
    return "JwtAuthentication[hidden-credentials]";
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) {
    throw new IllegalStateException("Unexpected invocation of JwtAuthentication.setAuthenticated");
  }

  @Override
  public String getName() {
    return "JwtAuthentication[hidden-name]";
  }

  public static JwtAuthentication fromHandle(JwtHandle jwtHandle) {
    return new JwtAuthentication(jwtHandle);
  }
}
