package se.fastdev.portal.motivator.bonuses.face;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import org.springframework.test.web.reactive.server.WebTestClient;
import se.fastdev.portal.motivator.bonuses.toolbox.capture.StringCapture;

public final class Help {

  public static void createSamplePerson(WebTestClient client, String... requestBodies) {
    String adminAuth = "Bearer " + StringCapture.fromLocalResource("auth/jwt").from("valid_adminPermission.jwt");

    for (String bodyValue : requestBodies) {
      client
          .post()
          .uri("/admin/persons")
          .header("authorization", adminAuth)
          .body(fromValue(bodyValue))
          .header("content-type", "application/json")
          .exchange()
          .expectStatus()
          .isEqualTo(201);
    }
  }
}
