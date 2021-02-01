package se.fastdev.portal.motivator.bonuses.face.model.response;

import java.math.BigDecimal;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseItem;

public final class JsonExpenseItem {

  public final String uuid;
  public final String type;
  public final BigDecimal amount;
  public final String description;
  public final JsonActionStamp spentStamp;

  private JsonExpenseItem(
      String uuid, String type, BigDecimal amount, String description,
      JsonActionStamp spentStamp
  ) {
    this.uuid = uuid;
    this.type = type;
    this.amount = amount;
    this.description = description;
    this.spentStamp = spentStamp;
  }

  public static JsonExpenseItem from(ExpenseItem item) {
    return new JsonExpenseItem(
        item.getUuid().toString(),
        item.getType().toString(),
        item.getAmount().asBigDecimal(),
        item.getDescription(),
        JsonActionStamp.from(item.getSpentStamp())
    );
  }
}
