package com.aminbenarieb.yandextask.Extensions;

/**
 * Created by aminbenarieb on 4/16/17.
 */

public class Dynamic<T> {
    Listener listener;
    public interface Listener<T> {
        void onResponse(T value);
    }

    public  Dynamic(T value){
        this.value = value;
    }

    private T value;
    public void setValue(T value) {
        this.value = value;
        listener.onResponse(value);
    }
    public T getValue() {
        return value;
    }

    public  void bind(Listener listener) {
        this.listener = listener;
    }
    public  void bindAndFire(Listener listener) {
        bind(listener);
        listener.onResponse(value);
    }
}
