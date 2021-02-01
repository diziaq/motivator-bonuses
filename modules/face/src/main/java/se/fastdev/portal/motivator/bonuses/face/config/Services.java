package se.fastdev.portal.motivator.bonuses.face.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.fastdev.portal.motivator.bonuses.core.BonusesGate;
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage;

@Configuration
public class Services {

  /* default */ Services() {
    // empty
  }

  @Bean
  public BonusesGate bonusesGate() {
    return BonusesGate.createGate(BonusesStorage.inMemory());
  }
}
