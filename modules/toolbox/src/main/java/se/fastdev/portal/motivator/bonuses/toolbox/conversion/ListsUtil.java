package se.fastdev.portal.motivator.bonuses.toolbox.conversion;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ListsUtil {

  private ListsUtil() {
    // namespace
  }

  public static <A, B> List<B> translate(List<A> list, Function<A, B> translation) {
    return list.stream()
               .map(translation)
               .collect(Collectors.toUnmodifiableList());
  }
}
