package se.fastdev.portal.motivator.bonuses.face.model.request;

import java.math.BigDecimal;
import java.time.Instant;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile;
import se.fastdev.portal.motivator.bonuses.core.models.MoneyAmount;
import se.fastdev.portal.motivator.bonuses.core.models.TimeRange;

public final class JsonExpenseProfileResetBid {

  public BigDecimal limit;
  public String startAt;
  public String finishAt;

  public ExpenseProfile.Blueprint toModel() {
    return new ExpenseProfile.Blueprint(
        new MoneyAmount(limit),
        new TimeRange(
            Instant.parse(startAt),
            Instant.parse(finishAt)
        )
    );
  }
}
