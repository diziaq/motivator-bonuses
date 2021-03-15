package se.fastdev.portal.motivator.bonuses.face.persistence.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonsRepository extends ReactiveMongoRepository<PersonDoc, ObjectId> {

  @Query("{ 'uuid' : ?0 }")
  Mono<PersonDoc> findByUuid(String uuid);

  @Query("{ 'attributes.portalId' : ?0 }")
  Mono<PersonDoc> findByPortalId(String portalId);
}
