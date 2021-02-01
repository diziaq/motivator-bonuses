package se.fastdev.portal.motivator.bonuses.face.persistence.mongo;

import java.math.BigDecimal;
import java.util.UUID;
import se.fastdev.portal.motivator.bonuses.core.models.ActionStamp;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseItem;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseType;
import se.fastdev.portal.motivator.bonuses.core.models.MoneyAmount;

public final class ExpenseItemDoc {

  public String uuid;
  public String type;
  public BigDecimal amount;
  public String description;
  public ActionStampDoc spentStamp;
  public ActionStampDoc publishedStamp;

  private ExpenseItemDoc(
      String uuid, String type, BigDecimal amount, String description,
      ActionStampDoc spentStamp, ActionStampDoc publishedStamp
  ) {
    this.uuid = uuid;
    this.type = type;
    this.amount = amount;
    this.description = description;
    this.spentStamp = spentStamp;
    this.publishedStamp = publishedStamp;
  }

  public ExpenseItem toModel() {
    return new ExpenseItem(
        UUID.fromString(uuid),
        ExpenseType.Companion.byName(type),
        new MoneyAmount(amount),
        description == null ? "" : description,
        new ActionStamp(spentStamp.at.toInstant(), spentStamp.by),
        new ActionStamp(publishedStamp.at.toInstant(), publishedStamp.by)
    );
  }

  public static ExpenseItemDoc from(ExpenseItem item) {
    return new ExpenseItemDoc(
        item.getUuid().toString(),
        item.getType().toString(),
        item.getAmount().asBigDecimal(),
        item.getDescription(),
        ActionStampDoc.from(item.getSpentStamp()),
        ActionStampDoc.from(item.getPublishedStamp())
    );
  }
}
