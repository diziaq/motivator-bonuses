package se.fastdev.portal.motivator.bonuses.face.model.request;

import java.math.BigDecimal;
import se.fastdev.portal.motivator.bonuses.core.models.ActionStamp;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseItem;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseType;
import se.fastdev.portal.motivator.bonuses.core.models.MoneyAmount;
import se.fastdev.portal.motivator.bonuses.face.extras.validation.UtcTime;
import se.fastdev.portal.motivator.bonuses.toolbox.conversion.InstantUtil;

public final class JsonExpenseItemAddBid {

  public String type;

  public BigDecimal amount;

  public String description;

  @UtcTime("spent at")
  public String spentAt;

  public ExpenseItem.Blueprint toModel(String spentBy) {
    return new ExpenseItem.Blueprint(
        ExpenseType.valueOf(type),
        new MoneyAmount(amount),
        description,
        new ActionStamp(InstantUtil.parse(spentAt), spentBy)
    );
  }
}
