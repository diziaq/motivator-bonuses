package se.fastdev.portal.motivator.bonuses.toolbox.conversion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestThrowablesUtil {

  @Test
  public void testFullDescription() {
    var first = new IllegalStateException("oinewrh er j;dsp9fe");
    var second = new AssertionError("rewkhewrlgm,ew'r ktliefsa", first);
    var third = new RuntimeException("dgasng,ar owrjtprwq euukfjsjytj i;urqw", second);

    var desc = ThrowablesUtil.fullDescription(third);

    assertAll(
        () -> assertThat(
            desc, startsWith("java.lang.RuntimeException: dgasng,ar owrjtprwq euukfjsjytj i;urqw")),
        () -> assertThat(
            desc, containsString("Caused by: java.lang.AssertionError: rewkhewrlgm,ew'r ktliefsa")),
        () -> assertThat(
            desc, containsString("Caused by: java.lang.IllegalStateException: oinewrh er j;dsp9fe"))
    );
  }

  @Test
  public void testCausesChain() {
    var first = new IllegalStateException("fasyete6tjrsh 5275yrdsgshqyr");
    var second = new IllegalArgumentException("grheyhgr wre gwoirgjpworhgoiw", first);
    var third = new RuntimeException("vojfkwqkvmwjvkc-r 0o ig09reqgq- ugqiwe9gu", second);

    var actualChain = ThrowablesUtil.causesChain(third);

    var expectedString =
        "java.lang.RuntimeException: vojfkwqkvmwjvkc-r 0o ig09reqgq- ugqiwe9gu\n"
            + "java.lang.IllegalArgumentException: grheyhgr wre gwoirgjpworhgoiw\n"
            + "java.lang.IllegalStateException: fasyete6tjrsh 5275yrdsgshqyr";

    assertEquals(expectedString, actualChain);
  }
}
