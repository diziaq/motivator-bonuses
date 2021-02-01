package se.fastdev.portal.motivator.bonuses.face.model.response;

import java.math.BigDecimal;
import java.util.List;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile;
import se.fastdev.portal.motivator.bonuses.toolbox.conversion.ListsUtil;

public final class JsonExpenseProfile {

  public final BigDecimal limit;
  public final BigDecimal remainder;
  public final JsonTimeRange periodOfActivity;
  public final List<JsonExpenseItem> expenses;

  public JsonExpenseProfile(
      BigDecimal limit,
      BigDecimal remainder,
      JsonTimeRange periodOfActivity,
      List<JsonExpenseItem> expenses
  ) {
    this.limit = limit;
    this.remainder = remainder;
    this.periodOfActivity = periodOfActivity;
    this.expenses = expenses;
  }

  public static JsonExpenseProfile from(ExpenseProfile profile) {
    return new JsonExpenseProfile(
        profile.getLimit().asBigDecimal(),
        profile.remainder().asBigDecimal(),
        JsonTimeRange.from(profile.getPeriodOfActivity()),
        ListsUtil.translate(profile.getExpenses(), JsonExpenseItem::from)
    );
  }
}
