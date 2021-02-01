package se.fastdev.portal.motivator.bonuses.toolbox.control.impl;

import java.util.function.Function;

final class TypeAwareHandlerTakesAssignable<T, R> implements TypeAwareHandler<T, R> {

  private final Class<?> handledClass;
  private final Function<T, R> handleFn;

  /* default */  TypeAwareHandlerTakesAssignable(
      Class<?> handledClass,
      Function<T, R> handleFn
  ) {
    this.handledClass = handledClass;
    this.handleFn = handleFn;
  }

  @Override
  public boolean canHandle(T instance) {
    return handledClass.isAssignableFrom(instance.getClass());
  }

  @Override
  public R handle(T instance) {
    return handleFn.apply(instance);
  }
}
