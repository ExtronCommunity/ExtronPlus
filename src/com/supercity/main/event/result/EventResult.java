package com.supercity.main.event.result;

public abstract class EventResult<T> {

    public abstract EventResult<T> getDefault();

    public abstract void modify(T event);
}
