package se.fastdev.portal.motivator.bonuses.face.endpoints;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.fastdev.portal.motivator.bonuses.face.FullHarnessTest;
import se.fastdev.portal.motivator.bonuses.toolbox.capture.StringCapture;

@FullHarnessTest
class TestEndpointPostAdminPersonsFormatViolations {

  @Autowired
  WebTestClient client;

  StringCapture captureJson;
  String adminAuth;

  @BeforeEach
  void before() {
    captureJson = StringCapture.fromLocalResource("suites/TestEndpointPostAdminPersonsFormatViolations");
    adminAuth = "Bearer " + StringCapture.fromLocalResource("auth/jwt")
                                         .from("valid_adminPermission.jwt");
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("invalidFormatsSamples")
  void invalidFormatOfArguments(String jsonSource, String expectedResponseMessage) {
    client
        .post()
        .uri("/admin/persons")
        .header("authorization", adminAuth)
        .body(fromValue(captureJson.from(jsonSource)))
        .header("CONtenT-typE", "application/json")
        .exchange()
        .expectStatus()
        .isEqualTo(400)
        .expectBody()
        .jsonPath("$.message")
        .isEqualTo(expectedResponseMessage);
  }

  static Stream<Arguments> invalidFormatsSamples() {
    return Stream.of(
        Arguments.of("POST_request_invalid_location.json",
                     "Format violated; Expect 'location' to be a name (given: Sputkl,ljhik)"),
        Arguments.of("POST_request_invalid_lastName.json",
                     "Format violated; Expect 'lastName' to be a name (given: Akdsf  jsldkfBopweurtpoew)"),
        Arguments.of("POST_request_invalid_firstName.json",
                     "Format violated; Expect 'firstName' to be a name (given: Flkljk9dsfsdf)"),
        Arguments.of("POST_request_invalid_portalId.json",
                     "Format violated; Expect 'portalId' to be not blank (given:      )"),
        Arguments.of("POST_request_invalid_three_fields.json",
                     "Format violated; Expect 'firstName' to be a name (given: Spods3uoikljlkfs); Violations count: 3"),
        Arguments.of("POST_request_invalid_two_fields.json",
                     "Format violated; Expect 'lastName' to be a name (given: Aljkjo46iyuyio); Violations count: 2")
    );
  }
}
