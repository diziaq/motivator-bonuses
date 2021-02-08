package se.fastdev.portal.motivator.bonuses.face.security;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.fastdev.portal.motivator.bonuses.face.FullHarnessTest;
import se.fastdev.portal.motivator.bonuses.toolbox.capture.StringCapture;

@FullHarnessTest
class TestArbitraryEndpointAccess {

  @Autowired
  WebTestClient client;

  static StringCapture captureJwt;
  static String arbitraryEndpointUri;

  @BeforeAll
  static void beforeAll() {
    captureJwt = StringCapture.fromLocalResource("auth/jwt");
    arbitraryEndpointUri = "/abracadabra/boom";
  }

  @Test
  @DisplayName("when no Authorization then status 401")
  void noAuthHeader() {
    client
        .get()
        .uri(arbitraryEndpointUri)
        .exchange()
        .expectStatus()
        .isEqualTo(401)
        .expectBody().isEmpty();
  }

  @ParameterizedTest
  @MethodSource("sampleGarbageInAuthorization")
  @DisplayName("when empty Authorization then status 401")
  void garbageAuthHeader(String authHeaderValue) {
    client
        .put()
        .uri(arbitraryEndpointUri)
        .header("Authorization", authHeaderValue)
        .exchange()
        .expectStatus()
        .isEqualTo(401)
        .expectBody().isEmpty();
  }

  @Test
  @DisplayName("when expired JWT in Authorization then status 403")
  void expiredJwtAuthHeader() {
    var jwt = captureJwt.from("forbidden_expiredAt20201226.jwt");

    client
        .get()
        .uri(arbitraryEndpointUri)
        .header("Authorization", "Bearer " + jwt)
        .exchange()
        .expectStatus()
        .isEqualTo(403)
        .expectBody()
        .jsonPath("$").isEqualTo("Access Denied");
  }

  @ParameterizedTest
  @MethodSource("samplesJwtMalformed")
  @DisplayName("when JWT has malformed fields in Authorization then status 401")
  void malformedPermissionJwtAuthHeader(String jwtSource) {
    var jwt = captureJwt.from(jwtSource);

    client
        .post()
        .uri(arbitraryEndpointUri)
        .header("AuthoRIZation", "Bearer " + jwt)
        .exchange()
        .expectStatus()
        .isEqualTo(401)
        .expectBody().isEmpty();
  }

  @Test
  @DisplayName("when JWT 'permission' has any values in Authorization then status 404")
  void lackAdminPermissionJwtAuthHeader() {
    var jwt = captureJwt.from("forbidden_unknownPermission.jwt");

    client
        .post()
        .uri(arbitraryEndpointUri)
        .header("Authorization", "Bearer " + jwt)
        .exchange()
        .expectStatus()
        .isEqualTo(404)
        .expectBody()
        .jsonPath("error").isEqualTo("Not Found")
        .jsonPath("status").isEqualTo(404)
        .jsonPath("timestamp").exists()
        .jsonPath("message").value(x -> assertNull(x));
  }

  static Stream<Arguments> samplesJwtMalformed() {
    return Stream
               .of(
                   "forbidden_expirationUnknown.jwt",
                   "forbidden_notArrayPermission.jwt",
                   "forbidden_withoutPermission.jwt"
               )
               .map(Arguments::of);
  }

  private static Stream<Arguments> sampleGarbageInAuthorization() {
    return Stream
               .of(
                   "",
                   "fgaofnmawpoefpaesf",
                   "Bearer",
                   "Bearer ",
                   "Bearer kmvaquj0fei9efw9e709ewar0a9er"
               )
               .map(Arguments::of);
  }
}
