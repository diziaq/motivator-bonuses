package se.fastdev.portal.motivator.bonuses.face.endpoints;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.fastdev.portal.motivator.bonuses.face.FullHarnessTest;
import se.fastdev.portal.motivator.bonuses.toolbox.capture.StringCapture;

@FullHarnessTest
class TestEndpointPostAdminPersonsSuccess {

  @Autowired
  WebTestClient client;

  StringCapture captureJson;
  String adminAuth;

  @BeforeEach
  void before() {
    captureJson = StringCapture.fromLocalResource("suites/TestEndpointPostAdminPersonsSuccess");
    adminAuth = "Bearer " + StringCapture.fromLocalResource("auth/jwt").from("valid_adminPermission.jwt");
  }

  @Test
  @DisplayName("when all attrs are valid then creates new person with zeroed activeExpenseProfile")
  void successfulCreationOfNewPerson() {
    client
        .post()
        .uri("/admin/persons")
        .header("authorization", adminAuth)
        .body(fromValue(captureJson.from("POST_request_valid.json")))
        .header("content-type", "application/json")
        .exchange()
        .expectStatus()
        .isEqualTo(201)
        .expectBody()
        .jsonPath("$.identifier").value((String x) -> assertNotNull(UUID.fromString(x)))
        .jsonPath("$.attributes.portalId").isEqualTo("wu5i5eet")
        .jsonPath("$.attributes.firstName").isEqualTo("Hoeutoiweripw")
        .jsonPath("$.attributes.lastName").isEqualTo("AkdsfjsldkfBopweurtpoew")
        .jsonPath("$.attributes.location").isEqualTo("Bpaoifdysdfs")
        .jsonPath("$.activeExpenseProfile.limit").isEqualTo(0)
        .jsonPath("$.activeExpenseProfile.remainder").isEqualTo(0)
        .jsonPath("$.activeExpenseProfile.expenses.length()").isEqualTo(0);
  }
}
