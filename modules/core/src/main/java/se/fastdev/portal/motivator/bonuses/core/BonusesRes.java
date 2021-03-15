package se.fastdev.portal.motivator.bonuses.core;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile;

public interface BonusesRes {

  Instant currentTime();

  UUID newUuid();

  BonusesException newException(String message, Exception cause);

  List<String> availableExpenseTypes();

  ExpenseProfile.Blueprint currentExpenseProfile();
}
