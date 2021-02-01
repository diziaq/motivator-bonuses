package se.fastdev.portal.motivator.bonuses.face.model.request;

import java.math.BigDecimal;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile;
import se.fastdev.portal.motivator.bonuses.core.models.MoneyAmount;
import se.fastdev.portal.motivator.bonuses.core.models.TimeRange;
import se.fastdev.portal.motivator.bonuses.face.extras.validation.UtcTime;
import se.fastdev.portal.motivator.bonuses.toolbox.conversion.InstantUtil;

public final class JsonExpenseProfileResetBid {

  public BigDecimal limit;

  @UtcTime("start at")
  public String startAt;

  @UtcTime("finish at")
  public String finishAt;

  public ExpenseProfile.Blueprint toModel() {
    return new ExpenseProfile.Blueprint(
        new MoneyAmount(limit),
        new TimeRange(
            InstantUtil.parse(startAt),
            InstantUtil.parse(finishAt)
        )
    );
  }
}
