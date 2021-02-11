package se.fastdev.portal.motivator.bonuses.face.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.fastdev.portal.motivator.bonuses.core.BonusesGate;
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage;
import se.fastdev.portal.motivator.bonuses.face.persistence.mongo.BonusesStorageMongo;
import se.fastdev.portal.motivator.bonuses.face.persistence.mongo.PersonsRepository;

@Configuration
public class Services {

  @Bean
  public BonusesGate bonusesGate(BonusesStorage bonusesStorage) {
    return BonusesGate.createGate(bonusesStorage);
  }

  @Bean
  public BonusesStorage bonusesStorage(PersonsRepository personsRepository) {
    return new BonusesStorageMongo(personsRepository);
  }
}
