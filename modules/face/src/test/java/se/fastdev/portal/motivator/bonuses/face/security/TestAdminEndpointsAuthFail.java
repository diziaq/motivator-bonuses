package se.fastdev.portal.motivator.bonuses.face.security;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

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
class TestAdminEndpointsAuthFail {

  @Autowired
  WebTestClient client;

  static StringCapture captureJwt;

  @BeforeAll
  static void beforeAll() {
    captureJwt = StringCapture.fromLocalResource("auth/jwt");
  }

  @Test
  @DisplayName("when no Authorization then status 401")
  void noAuthHeader() {
    client
        .get()
        .uri("/admin/persons")
        .header("CONtenT-typE", "application/json")
        .exchange()
        .expectStatus()
        .isEqualTo(401)
        .expectBody().isEmpty();
  }

  @Test
  @DisplayName("when empty Authorization then status 401")
  void emptyAuthHeader() {
    client
        .post()
        .uri("/admin/persons")
        .header("Authorization", "")
        .body(fromValue(""))
        .header("CONtenT-typE", "application/json")
        .exchange()
        .expectStatus()
        .isEqualTo(401)
        .expectBody().isEmpty();
  }

  @Test
  @DisplayName("when garbage in Authorization then status 401")
  void garbageAuthHeader() {
    client
        .post()
        .uri("/admin/persons")
        .header("Authorization", "adghthwth")
        .body(fromValue(""))
        .header("content-type", "application/json")
        .exchange()
        .expectStatus()
        .isEqualTo(401)
        .expectBody().isEmpty();
  }

  @Test
  @DisplayName("when bearer with garbage in Authorization then status 401")
  void bearerWithGarbageAuthHeader() {
    client
        .post()
        .uri("/admin/persons")
        .header("Authorization", "Bearer adghthwth")
        .body(fromValue(""))
        .header("CONtenT-typE", "application/json")
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
        .post()
        .uri("/admin/persons")
        .header("Authorization", "Bearer " + jwt)
        .body(fromValue(""))
        .header("CONtenT-typE", "application/json")
        .exchange()
        .expectStatus()
        .isEqualTo(403)
        .expectBody()
        .jsonPath("$").isEqualTo("Access Denied");
  }

  @ParameterizedTest
  @MethodSource("samplesJwtMalformed")
  @DisplayName("when JWT has non-array 'permission' field in Authorization then status 401")
  void malformedPermissionJwtAuthHeader(String jwtSource) {
    var jwt = captureJwt.from(jwtSource);

    client
        .post()
        .uri("/admin/persons")
        .header("AuthoRIZation", "Bearer " + jwt)
        .body(fromValue(""))
        .header("CONTENT-TYPE", "application/json")
        .exchange()
        .expectStatus()
        .isEqualTo(401)
        .expectBody().isEmpty();
  }

  @ParameterizedTest
  @MethodSource("samplesJwtNotAdmin")
  @DisplayName("when JWT 'permission' has no admin value in Authorization then status 403")
  void lackAdminPermissionJwtAuthHeader(String jwtSource) {
    var jwt = captureJwt.from(jwtSource);

    client
        .post()
        .uri("/admin/persons")
        .header("Authorization", "Bearer " + jwt)
        .body(fromValue(""))
        .header("CONTENT-TYPE", "application/json")
        .exchange()
        .expectStatus()
        .isEqualTo(403)
        .expectBody()
        .jsonPath("$").isEqualTo("Access Denied");
  }

  static Stream<Arguments> samplesJwtNotAdmin() {
    return Stream
               .of(
                   "forbidden_unknownPermission.jwt",
                   "valid_anyuserPermission.jwt",
                   "valid_supervisorPermission.jwt"
               )
               .map(Arguments::of);
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
}
