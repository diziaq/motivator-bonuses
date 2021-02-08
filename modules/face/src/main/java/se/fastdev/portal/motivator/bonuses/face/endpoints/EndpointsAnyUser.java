package se.fastdev.portal.motivator.bonuses.face.endpoints;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;
import se.fastdev.portal.motivator.bonuses.face.extras.DefaultController;
import se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt.JwtPayload;

@DefaultController
public class EndpointsAnyUser {

  @GetMapping("anyuser/i")
  @ResponseStatus(HttpStatus.OK)
  public Mono<?> getCurrentUserAuthInfo(@AuthenticationPrincipal JwtPayload jwt) {
    return Mono.just(Map.of("jwt", jwt.claims()));
  }
}
