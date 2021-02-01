package se.fastdev.portal.motivator.bonuses.face.endpoints;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import se.fastdev.portal.motivator.bonuses.core.BonusesGate;
import se.fastdev.portal.motivator.bonuses.face.model.request.JsonExpenseItemAddBid;
import se.fastdev.portal.motivator.bonuses.face.model.request.JsonExpenseProfileResetBid;

@RestController
@RequestMapping("persons/{uuid}")
public class EndpointsExpense {

  private final BonusesGate gate;

  public EndpointsExpense(BonusesGate gate) {
    this.gate = gate;
  }

  @PostMapping("expenses")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<String> addNewExpenseItem(
      @PathVariable String uuid,
      @RequestBody JsonExpenseItemAddBid body
  ) {
    final var personUuid = UUID.fromString(uuid);

    return Mono.just(body)
               .map(x -> x.toModel(uuid))
               .flatMap(model -> gate.administer().addNewExpenseItem(personUuid, model))
               .map(x -> "success");
  }

  @PostMapping("expense-profile")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<String> startNewExpenseProfile(
      @PathVariable String uuid,
      @RequestBody JsonExpenseProfileResetBid body
  ) {
    final var personUuid = UUID.fromString(uuid);

    return Mono.just(body)
               .map(JsonExpenseProfileResetBid::toModel)
               .flatMap(model -> gate.administer().startNewExpenseProfile(personUuid, model))
               .map(x -> "success");
  }
}
