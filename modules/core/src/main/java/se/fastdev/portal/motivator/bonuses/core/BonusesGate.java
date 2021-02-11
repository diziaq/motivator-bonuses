package se.fastdev.portal.motivator.bonuses.core;

import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.fastdev.portal.motivator.bonuses.core.internal.ConstructionMold;
import se.fastdev.portal.motivator.bonuses.core.models.BulkProcessReport;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseItem;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile;
import se.fastdev.portal.motivator.bonuses.core.models.Person;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;

public interface BonusesGate {

  Administer administer();

  Peep peep(String portalId);

  Support support();

  interface Administer {

    Mono<Person> createNewPerson(PersonAttributes attributes);

    Mono<Person> getPersonById(UUID uuid);

    Mono<Person> startNewExpenseProfile(UUID personUuid, ExpenseProfile.Blueprint blueprint);

    Mono<Person> addNewExpenseItem(UUID personUuid, ExpenseItem.Blueprint blueprint);

    Flux<Person> getAllPersons();
  }

  interface Peep {

    Mono<Person> getOwnedPerson();
  }

  interface Support {

    <T> Mono<BulkProcessReport> refreshAttributes(
        Stream<PersonAttributes> offeredAttributes,
        Function<PersonAttributes, T> identityProbe
    );
  }

  static BonusesGate createGate(BonusesStorage storage) {
    return ConstructionMold.INSTANCE.gateWithStorage(storage);
  }
}
