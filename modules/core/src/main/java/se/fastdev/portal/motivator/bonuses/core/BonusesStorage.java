package se.fastdev.portal.motivator.bonuses.core;

import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.fastdev.portal.motivator.bonuses.core.internal.ConstructionMold;
import se.fastdev.portal.motivator.bonuses.core.models.Person;

public interface BonusesStorage {

  Mono<Person> save(Person person);

  Mono<Person> findByUuid(UUID uuid);

  Flux<Person> findAll();

  static BonusesStorage inMemory() {
    return ConstructionMold.INSTANCE.storageInMemory();
  }
}
