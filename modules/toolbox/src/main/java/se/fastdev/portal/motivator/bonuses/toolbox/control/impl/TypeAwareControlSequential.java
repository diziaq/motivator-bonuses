package se.fastdev.portal.motivator.bonuses.toolbox.control.impl;

import java.util.List;
import java.util.Objects;
import se.fastdev.portal.motivator.bonuses.toolbox.control.TypeAwareControl;
import se.fastdev.portal.motivator.bonuses.toolbox.exceptions.CommonException;

final class TypeAwareControlSequential<T, R> implements TypeAwareControl<T, R> {

  private final List<TypeAwareHandler<T, R>> handlers;

  /* default */ TypeAwareControlSequential(
      List<TypeAwareHandler<T, R>> handlers
  ) {
    this.handlers = List.copyOf(handlers);
  }

  @Override
  public R manage(T value) {
    Objects.requireNonNull(value, "null values must be handled separately");

    return handlers.stream()
                   .filter(x -> x.canHandle(value))
                   .findFirst()
                   .orElseThrow(() -> failedOn(value))
                   .handle(value);
  }

  private static CommonException failedOn(Object unhandledValue) {
    return CommonException.normal(
        "Unable to find handler for type ({0}). Check handlers integrity.",
        unhandledValue.getClass()
    );
  }
}
