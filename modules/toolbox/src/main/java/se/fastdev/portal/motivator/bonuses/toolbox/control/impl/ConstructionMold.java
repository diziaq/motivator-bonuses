package se.fastdev.portal.motivator.bonuses.toolbox.control.impl;

import se.fastdev.portal.motivator.bonuses.toolbox.control.TypeAwareControl.Builder;

public final class ConstructionMold {

  private ConstructionMold() {
    //namespace
  }

  public static <T, R> Builder<T, R> perAssignable(
      Class<T> basicManagedClass,
      Class<R> resultClass
  ) {
    // `resultClass` as a parameter is required only for type safety

    return new TypeAwareControlDefaultBuilder<>(
        basicManagedClass, TypeAwareHandlerTakesAssignable::new
    );
  }
}
