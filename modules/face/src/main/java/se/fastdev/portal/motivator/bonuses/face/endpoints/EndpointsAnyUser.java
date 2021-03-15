package se.fastdev.portal.motivator.bonuses.face.endpoints;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.fastdev.portal.motivator.bonuses.core.BonusesGate;
import se.fastdev.portal.motivator.bonuses.face.extras.DefaultController;
import se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt.JwtPayload;
import se.fastdev.portal.motivator.bonuses.face.model.response.JsonPersonFull;

@DefaultController
public class EndpointsAnyUser {

  private final BonusesGate gate;

  public EndpointsAnyUser(BonusesGate gate) {
    this.gate = gate;
  }

  @GetMapping("anyuser/i")
  @ResponseStatus(HttpStatus.OK)
  public Mono<?> getCurrentUserAuthInfo(@AuthenticationPrincipal JwtPayload jwt) {
    return Mono.just(Map.of("jwt", jwt.claims()));
  }

  @GetMapping("anyuser/persons")
  @ResponseStatus(HttpStatus.OK)
  public Flux<JsonPersonFull> getCurrentUserPersons(@AuthenticationPrincipal JwtPayload jwt) {
    return Mono.just(jwt.claim("email", String.class))
               .flatMap(portalId -> gate.peep(portalId).getOwnedPerson())
               .map(JsonPersonFull::from)
               .flux();
  }
}
