package se.fastdev.portal.motivator.bonuses.face.persistence.mongo;

import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage;
import se.fastdev.portal.motivator.bonuses.core.models.Person;
import se.fastdev.portal.motivator.bonuses.toolbox.exceptions.CommonException;

public final class BonusesStorageMongo implements BonusesStorage {

  private final PersonsRepository personsRepository;

  public BonusesStorageMongo(PersonsRepository personsRepository) {
    this.personsRepository = personsRepository;
  }

  @Override
  public Mono<Person> save(Person person) {
    final var personDoc = PersonDoc.from(person);

    return personsRepository
               .findByUuid(personDoc.uuid)
               .map(pd -> new PersonDoc(pd.id, personDoc))
               .defaultIfEmpty(personDoc)
               .flatMap(personsRepository::save)
               .map(PersonDoc::toModel);
  }

  @Override
  public Mono<Person> findByUuid(UUID uuid) {
    return personsRepository
               .findByUuid(uuid.toString())
               .map(PersonDoc::toModel)
               .switchIfEmpty(Mono.defer(
                   () -> Mono.error(CommonException.thin("Not found person with uuid " + uuid)))
               );
  }

  @Override
  public Mono<Person> findByPortalId(String portalId) {
    return personsRepository
               .findByPortalId(portalId)
               .map(PersonDoc::toModel)
               .switchIfEmpty(Mono.defer(
                   () -> Mono.error(
                       CommonException.thin("Not found person with portalId " + portalId)))
               );
  }

  @Override
  public Flux<Person> findAll() {
    return personsRepository
               .findAll()
               .map(PersonDoc::toModel);
  }
}
