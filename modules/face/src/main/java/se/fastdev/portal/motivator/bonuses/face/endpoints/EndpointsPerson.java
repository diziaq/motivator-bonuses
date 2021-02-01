package se.fastdev.portal.motivator.bonuses.face.endpoints;

import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.fastdev.portal.motivator.bonuses.core.BonusesGate;
import se.fastdev.portal.motivator.bonuses.face.extras.DefaultController;
import se.fastdev.portal.motivator.bonuses.face.extras.validation.Uuid;
import se.fastdev.portal.motivator.bonuses.face.model.request.JsonPersonCreateBid;
import se.fastdev.portal.motivator.bonuses.face.model.response.JsonPersonBrief;
import se.fastdev.portal.motivator.bonuses.face.model.response.JsonPersonFull;

@DefaultController
@RequestMapping("persons")
public class EndpointsPerson {

  private final BonusesGate gate;

  public EndpointsPerson(BonusesGate gate) {
    this.gate = gate;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<JsonPersonBrief> createPerson(
      @Valid @RequestBody JsonPersonCreateBid body
  ) {
    return Mono.just(body)
               .map(JsonPersonCreateBid::toModel)
               .flatMap(prd -> gate.administer().createNewPerson(prd))
               .map(JsonPersonBrief::from);
  }

  @GetMapping("{uuid}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<JsonPersonFull> getOnePerson(
      @PathVariable @Uuid("person id") String uuid
  ) {
    return Mono.just(uuid)
               .map(UUID::fromString)
               .flatMap(x -> gate.administer().getPersonById(x))
               .map(JsonPersonFull::from);
  }

  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public Flux<JsonPersonBrief> getAllPersons() {
    return Flux.from(gate.administer().getAllPersons())
               .map(JsonPersonBrief::from);
  }
}
