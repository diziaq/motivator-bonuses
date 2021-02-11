package se.fastdev.portal.motivator.bonuses.face.persistence.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonsRepository extends ReactiveMongoRepository<PersonDoc, ObjectId> {

  default Mono<PersonDoc> findByUuid(String uuid) {
    return findOne(Example.of(PersonDoc.exampleUuid(uuid)));
  }

  default Mono<PersonDoc> findByPortalId(String portalId) {
    return findOne(Example.of(PersonDoc.examplePortalId(portalId)));
  }
}
