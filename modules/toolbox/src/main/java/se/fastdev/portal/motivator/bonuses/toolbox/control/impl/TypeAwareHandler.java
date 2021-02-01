package se.fastdev.portal.motivator.bonuses.toolbox.control.impl;

interface TypeAwareHandler<T, R> {

  boolean canHandle(T instance);

  R handle(T instance);
}
