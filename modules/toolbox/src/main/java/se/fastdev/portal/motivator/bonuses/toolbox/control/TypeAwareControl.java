package se.fastdev.portal.motivator.bonuses.toolbox.control;

import java.util.function.Function;
import se.fastdev.portal.motivator.bonuses.toolbox.control.impl.ConstructionMold;

public interface TypeAwareControl<T, R> {

  R manage(T value);

  interface Builder<T, R> {

    <X extends T> Builder<T, R> with(Class<X> managedClazz, Function<X, R> handleFn);

    TypeAwareControl<T, R> fallback(Function<T, R> recognizeFn);
  }

  static <T, R> Builder<T, R> perAssignable(Class<T> basicManagedClass, Class<R> resultClass) {
    return ConstructionMold.perAssignable(basicManagedClass, resultClass);
  }
}
