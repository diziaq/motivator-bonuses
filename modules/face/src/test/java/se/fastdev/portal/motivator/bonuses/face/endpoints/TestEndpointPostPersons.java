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
class TestEndpointPostPersons {

  @Autowired
  WebTestClient client;

  StringCapture captureJson;
  String adminAuth;

  @BeforeEach
  void before() {
    captureJson = StringCapture.fromLocalResource("suites/TestEndpointPostPersons");
    adminAuth =
        "Bearer " + StringCapture.fromLocalResource("auth/jwt").from("valid_adminPermission.jwt");
  }

  @Test
  @DisplayName("when all attrs are valid then creates new person with zeroed activeExpenseProfile")
  void successfulCreationOfNewPerson() {
    client
        .post()
        .uri("/admin/persons")
        .header("AUTHorizATION", adminAuth)
        .body(fromValue(captureJson.from("POST_request_valid.json")))
        .header("CONtenT-typE", "application/json")
        .exchange()
        .expectStatus()
        .isEqualTo(201)
        .expectBody()
        .jsonPath("$.identifier").value((String x) -> assertNotNull(UUID.fromString(x)))
        .jsonPath("$.attributes.firstName").isEqualTo("Hoeutoiweripw")
        .jsonPath("$.attributes.lastName").isEqualTo("AkdsfjsldkfBopweurtpoew")
        .jsonPath("$.attributes.location").isEqualTo("Bpaoifdysdfs")
        .jsonPath("$.activeExpenseProfile.limit").isEqualTo(0)
        .jsonPath("$.activeExpenseProfile.remainder").isEqualTo(0)
        .jsonPath("$.activeExpenseProfile.expenses.length()").isEqualTo(0);
  }

  @Test
  void invalidFormatOfFirstName() {
    client
        .post()
        .uri("/admin/persons")
        .header("authorizATION", adminAuth)
        .body(fromValue(captureJson.from("POST_request_invalid_firstName.json")))
        .header("CONTENT-typE", "application/json")
        .exchange()
        .expectStatus()
        .isEqualTo(400)
        .expectBody()
        .jsonPath("$.message")
        .isEqualTo("Format violated; Expect 'firstName' to be a name (given: Flkljk9dsfsdf)");
  }

  @Test
  void invalidFormatOfLastName() {
    client
        .post()
        .uri("/admin/persons")
        .header("authorization", adminAuth)
        .body(fromValue(captureJson.from("POST_request_invalid_lastName.json")))
        .header("content-type", "application/json")
        .exchange()
        .expectStatus()
        .isEqualTo(400)
        .expectBody()
        .jsonPath("$.message")
        .isEqualTo(
            "Format violated; Expect 'lastName' to be a name (given: Akdsf  jsldkfBopweurtpoew)");
  }

  @Test
  void invalidFormatOfLocation() {
    client
        .post()
        .uri("/admin/persons")
        .header("authorization", adminAuth)
        .body(fromValue(captureJson.from("POST_request_invalid_location.json")))
        .header("CONtenT-typE", "application/json")
        .exchange()
        .expectStatus()
        .isEqualTo(400)
        .expectBody()
        .jsonPath("$.message")
        .isEqualTo("Format violated; Expect 'location' to be a name (given: Sputkl,ljhik)");
  }
}
