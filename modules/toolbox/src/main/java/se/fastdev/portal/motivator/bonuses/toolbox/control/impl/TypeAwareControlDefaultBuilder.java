package se.fastdev.portal.motivator.bonuses.toolbox.control.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import se.fastdev.portal.motivator.bonuses.toolbox.control.TypeAwareControl;
import se.fastdev.portal.motivator.bonuses.toolbox.control.TypeAwareControl.Builder;

final class TypeAwareControlDefaultBuilder<T, R> implements Builder<T, R> {

  private final Class<T> basicManagedClass;
  private final BiFunction<Class<T>, Function<T, R>, TypeAwareHandler<T, R>>
      handlerMold;
  private final List<TypeAwareHandler<T, R>> handlers = new LinkedList<>();

  /* default */TypeAwareControlDefaultBuilder(
      Class<T> basicManagedClass,
      BiFunction<Class<T>, Function<T, R>, TypeAwareHandler<T, R>> handlerMold
  ) {
    this.basicManagedClass = basicManagedClass;
    this.handlerMold = handlerMold;
  }

  @Override
  public <X extends T> Builder<T, R> with(Class<X> managedClass, Function<X, R> handleFn) {
    handlers.add(asHandler(managedClass, handleFn));

    return this;
  }

  @Override
  public TypeAwareControl<T, R> fallback(Function<T, R> handleFn) {
    with(basicManagedClass, handleFn);

    return new TypeAwareControlSequential<>(handlers);
  }

  @SuppressWarnings("unchecked")
  private <X extends T> TypeAwareHandler<T, R> asHandler(
      Class<X> managedClass,
      Function<X, R> handleFn
  ) {
    return handlerMold.apply((Class<T>) managedClass, (Function<T, R>) handleFn);
  }
}
