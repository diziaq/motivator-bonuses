package se.fastdev.portal.motivator.bonuses.face.model.request;

import java.math.BigDecimal;
import java.time.Instant;
import se.fastdev.portal.motivator.bonuses.core.models.ActionStamp;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseItem;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseType;
import se.fastdev.portal.motivator.bonuses.core.models.MoneyAmount;

public final class JsonExpenseItemAddBid {

  public String type;
  public BigDecimal amount;
  public String description;
  public String spentAt;

  public ExpenseItem.Blueprint toModel(String spentBy) {
    return new ExpenseItem.Blueprint(
        ExpenseType.valueOf(type),
        new MoneyAmount(amount),
        description,
        new ActionStamp(
            Instant.parse(spentAt),
            spentBy
        )
    );
  }
}
