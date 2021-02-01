package se.fastdev.portal.motivator.bonuses.face;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@FullHarnessTest
class TestApplicationCanStart {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  @DisplayName("when app is started then application has id")
  void contextContainsAppId() {
    assertEquals("application", applicationContext.getId());
  }

  @Test
  @DisplayName("when app is started then ApplicationMain bean exists in context")
  void contextContainsMainBean() {
    assertNotNull(applicationContext.getBean(WebApi.class));
  }
}
