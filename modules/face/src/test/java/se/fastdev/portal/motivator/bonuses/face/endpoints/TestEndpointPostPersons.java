package se.fastdev.portal.motivator.bonuses.face.endpoints;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
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

  StringCapture capture;

  @BeforeEach
  void before() {
    capture = StringCapture.fromLocalResource("suites/EndpointPostPersonsTest");
  }

  @Test
  @DisplayName("when all attrs are valid then creates new person with zeroed activeExpenseProfile")
  void successfulCreationOfNewPerson() {
    client
        .method(POST)
        .uri("/persons")
        .body(fromValue(capture.from("POST_request_valid.json")))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isEqualTo(CREATED)
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
        .method(POST)
        .uri("/persons")
        .body(fromValue(capture.from("POST_request_invalid_firstName.json")))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isEqualTo(BAD_REQUEST)
        .expectBody()
        .jsonPath("$.message")
        .isEqualTo("Format violated; Expect 'firstName' to be a name (given: Flkljk9dsfsdf)");
  }

  @Test
  void invalidFormatOfLastName() {
    client
        .method(POST)
        .uri("/persons")
        .body(fromValue(capture.from("POST_request_invalid_lastName.json")))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isEqualTo(BAD_REQUEST)
        .expectBody()
        .jsonPath("$.message")
        .isEqualTo(
            "Format violated; Expect 'lastName' to be a name (given: Akdsf  jsldkfBopweurtpoew)");
  }

  @Test
  void invalidFormatOfLocation() {
    client
        .method(POST)
        .uri("/persons")
        .body(fromValue(capture.from("POST_request_invalid_location.json")))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isEqualTo(BAD_REQUEST)
        .expectBody()
        .jsonPath("$.message")
        .isEqualTo("Format violated; Expect 'location' to be a name (given: Sputkl,ljhik)");
  }
}