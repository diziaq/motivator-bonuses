package se.fastdev.portal.motivator.bonuses.core;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile;
import se.fastdev.portal.motivator.bonuses.core.models.MoneyAmount;
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes;
import se.fastdev.portal.motivator.bonuses.core.models.TimeRange;

public class TestAdministerExpenseProfileArchiving {

  private BonusesGate gate;

  @BeforeEach
  public void beforeEach() {
    this.gate = BonusesGate.createGate(BonusesStorage.inMemory());
  }

  @Test
  @DisplayName("when new ExpenseProfile created then previous ExpenseProfile moved to history")
  public void profileForExistingPerson() {
    final var personCreated = gate.administer()
                                  .createNewPerson(new PersonAttributes("turwu", "dsasg", "uryi"))
                                  .block();

    final var personUuid = personCreated.getUuid();

    gate.administer()
        .startNewExpenseProfile(personUuid, blueprintWithLimit(15335764))
        .block();

    gate.administer()
        .startNewExpenseProfile(personUuid, blueprintWithLimit(7694372))
        .block();

    gate.administer()
        .startNewExpenseProfile(personUuid, blueprintWithLimit(8965472))
        .block();

    var person = gate.administer().getPersonById(personUuid).block();

    var limitsHistory = person.getExpenseProfilesHistory().stream()
                              .map(p -> p.getLimit().asInt())
                              .collect(toList());

    assertThat(limitsHistory, contains(7694372, 15335764, 0));
  }

  private static ExpenseProfile.Blueprint blueprintWithLimit(int amount) {
    final var limit = new MoneyAmount(amount);
    final var period = new TimeRange(Instant.parse("2020-01-04T08:00:10Z"),
                                     Instant.parse("2020-02-05T12:10:07Z"));

    return new ExpenseProfile.Blueprint(limit, period);
  }
}
