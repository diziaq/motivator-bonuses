package se.fastdev.portal.motivator.bonuses.core.fakes;

import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage;
import se.fastdev.portal.motivator.bonuses.core.models.Person;
import se.fastdev.portal.motivator.bonuses.toolbox.exceptions.CommonException;

public final class BonusesStorageBroken implements BonusesStorage {

  @Override
  public Mono<Person> save(Person person) {
    return Mono.error(CommonException.thin("save(Person) secret message"));
  }

  @Override
  public Mono<Person> findById(UUID uuid) {
    return Mono.error(CommonException.thin("findById(UUID) secret message"));
  }

  @Override
  public Flux<Person> findAll() {
    return Flux.error(CommonException.thin("findALl() secret message"));
  }
}