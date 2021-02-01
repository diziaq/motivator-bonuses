package se.fastdev.portal.motivator.bonuses.face.persistence.mongo;

import java.math.BigDecimal;
import java.util.List;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile;
import se.fastdev.portal.motivator.bonuses.core.models.MoneyAmount;
import se.fastdev.portal.motivator.bonuses.toolbox.conversion.ListsUtil;

public final class ExpenseProfileDoc {

  public BigDecimal limit;

  public TimeRangeDoc periodOfActivity;

  public List<ExpenseItemDoc> expenses;

  private ExpenseProfileDoc(
      BigDecimal limit,
      TimeRangeDoc periodOfActivity,
      List<ExpenseItemDoc> expenses
  ) {
    this.limit = limit;
    this.periodOfActivity = periodOfActivity;
    this.expenses = expenses;
  }

  public ExpenseProfile toModel() {
    return new ExpenseProfile(
        new MoneyAmount(limit),
        periodOfActivity.toModel(),
        ListsUtil.translate(expenses, ExpenseItemDoc::toModel)
    );
  }

  public static ExpenseProfileDoc from(ExpenseProfile profile) {
    return new ExpenseProfileDoc(
        profile.getLimit().asBigDecimal(),
        TimeRangeDoc.from(profile.getPeriodOfActivity()),
        ListsUtil.translate(profile.getExpenses(), ExpenseItemDoc::from)
    );
  }
}
