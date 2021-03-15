package se.fastdev.portal.motivator.bonuses.face.endpoints;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.fastdev.portal.motivator.bonuses.core.BonusesGate;
import se.fastdev.portal.motivator.bonuses.core.models.BulkProcessReport;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;
import se.fastdev.portal.motivator.bonuses.face.extras.DefaultController;
import se.fastdev.portal.motivator.bonuses.face.model.request.JsonPortalUserDescription;

@DefaultController
@RequestMapping("admin/support")
public class EndpointsSupport {

  private final BonusesGate gate;
  private final Logger log;

  public EndpointsSupport(BonusesGate gate, Logger sharedLogger) {
    this.gate = gate;
    this.log = sharedLogger;
  }

  @PostMapping("/refresh-attributes")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<Void> refreshAttributes(
      @Valid @RequestBody JsonPortalUserDescription[] body
  ) {
    return Flux.just(body)
               .map(JsonPortalUserDescription::toModel)
               .buffer(1000)
               .flatMap(att -> gate.support().refreshAttributes(att.stream(), PersonAttributes::getPortalId))
               .doOnNext(report -> logReport(log, "Completed attributes sync", report))
               .then();
  }

  private void logReport(Logger log, String header, BulkProcessReport report) {
    log.info(header + "\n{}", report);
  }
}
