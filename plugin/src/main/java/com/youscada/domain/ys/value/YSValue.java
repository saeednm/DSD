package com.youscada.domain.ys.value;

public abstract class YSValue<T> {

    protected T value;

    public YSValue(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

}
