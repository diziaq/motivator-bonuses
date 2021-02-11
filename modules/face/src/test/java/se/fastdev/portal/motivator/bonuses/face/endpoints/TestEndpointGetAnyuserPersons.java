package se.fastdev.portal.motivator.bonuses.face.endpoints;

import static se.fastdev.portal.motivator.bonuses.face.Help.createSamplePerson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.fastdev.portal.motivator.bonuses.face.FullHarnessTest;
import se.fastdev.portal.motivator.bonuses.toolbox.capture.StringCapture;

@FullHarnessTest
public final class TestEndpointGetAnyuserPersons {

  @Autowired
  WebTestClient client;

  StringCapture capture;

  @BeforeEach
  void before() {
    capture = StringCapture.fromLocalResource("suites/TestEndpointGetAnyuserPersons");

    createSamplePerson(client, capture.from("POST_request_predefined_person.json"));
  }

  @Test
  @DisplayName("when person with portalId (== jwt.id) exists then return array with one item (== this person)")
  void successfulCreationOfNewPerson() {
    client
        .get()
        .uri("/anyuser/persons")
        .header("authorization", "Bearer " + capture.from("predefined_person_token.jwt"))
        .exchange()
        .expectStatus()
        .isEqualTo(200)
        .expectBody()
        .json(capture.from("GET_response_array_of_single_predefined_person.json"));
  }
}
