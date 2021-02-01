package se.fastdev.portal.motivator.bonuses.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fastdev.portal.motivator.bonuses.core.BonusesException;

public final class TestExpenseProfile {

  @Test
  @DisplayName("when sum of expenses exceeds limit then calculation of remainder fails")
  public void testForcedNegativeRemainder() {
    var profile = new ExpenseProfile(
        new MoneyAmount(1000),
        new TimeRange(Instant.parse("2019-05-03T11:16:19Z"), Instant.parse("2021-11-01T18:16:19Z")),
        List.of(
            createExpenseItem(500),
            createExpenseItem(200),
            createExpenseItem(301)
        )
    );

    var exception = assertThrows(BonusesException.class, () -> profile.remainder());

    assertEquals("Remainder cannot be negative: limit (10), totalSum (10.01)",
                 exception.getMessage());
  }

  private ExpenseItem createExpenseItem(int amount) {
    return new ExpenseItem(
        UUID.randomUUID(),
        ExpenseType.SPORTS,
        new MoneyAmount(amount),
        "shsgj",
        new ActionStamp(Instant.parse("2019-05-03T11:16:19Z"), "rdyuteduj"),
        new ActionStamp(Instant.parse("2019-05-03T11:16:19Z"), "pp")
    );
  }
}
