package se.fastdev.portal.motivator.bonuses.toolbox.conversion;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.function.Function;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestListsUtil {

  @Test
  @DisplayName("when given list and translator fn then result is list of translated items preserving order")
  public void simpleTranslate() {
    var list = List.of("A", "BC", "DEF", "GHIJ");
    Function<String, Integer> fn = x -> x.length() + 1;

    var result = ListsUtil.translate(list, fn);

    assertThat(result, Matchers.contains(2, 3, 4, 5));
  }
}
