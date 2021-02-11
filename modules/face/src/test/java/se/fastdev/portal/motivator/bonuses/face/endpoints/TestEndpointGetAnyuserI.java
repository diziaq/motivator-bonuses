package se.fastdev.portal.motivator.bonuses.face.endpoints;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.fastdev.portal.motivator.bonuses.face.FullHarnessTest;
import se.fastdev.portal.motivator.bonuses.toolbox.capture.StringCapture;

@FullHarnessTest
public final class TestEndpointGetAnyuserI {

  @Autowired
  WebTestClient client;

  StringCapture captureJson;
  String anyuserAuth;

  @BeforeEach
  void before() {
    captureJson = StringCapture.fromLocalResource("suites/TestEndpointGetAnyuserI");
    anyuserAuth = "Bearer " + StringCapture.fromLocalResource("auth/jwt")
                                           .from("valid_anyuserPermission.jwt");
  }

  @Test
  @DisplayName("when jwt is valid then return all claims from it")
  void showCurrentJwtClaims() {
    client
        .get()
        .uri("/anyuser/i")
        .header("authorization", anyuserAuth)
        .exchange()
        .expectStatus()
        .isEqualTo(200)
        .expectBody()
        .json(captureJson.from("GET_response_valid_claims.json"));
  }
}
