package se.fastdev.portal.motivator.bonuses.face;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
final class DummyEndpoint {

  private final String dummyGreeting;

  public DummyEndpoint(
      @Value("${dummy_0:default}") String dummy0,
      @Value("${dummy_1:default}") String dummy1,
      @Value("${dummy_2:default}") String dummy2
  ) {
    this.dummyGreeting = dummy0 + ", " + dummy1 + ", " + dummy2;
  }

  @GetMapping(value = "dummy", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Map<String, ?>> hitDummy() {
    return Flux.interval(Duration.ofSeconds(1))
               .take(5)

               .map(x -> of(dummyGreeting, System.nanoTime(),
                            "a", x % 2 == 0 ? Date.from(Instant.now()) : 1L,
                            "b", x % 2 == 0 ? 2L : Timestamp.valueOf(LocalDateTime.now()))
               );
  }

  private static <K, V> Map<K, V> of(K key1, V value1, K key2, V value2, K key3, V value3) {
    final var map = new HashMap<K, V>();

    map.put(key1, value1);
    map.put(key2, value2);
    map.put(key3, value3);

    return map;
  }
}
