package com.aminbenarieb.yandextask.Extensions;

/**
 * Created by aminbenarieb on 4/16/17.
 */

public class Dynamic<T> {
    Listener listener;
    interface Listener<T> {
        void onResponse(T value);
    }
    private T value;

    void bind(Listener listener) {
        this.listener = listener;
    }

    void bindAndFire(Listener listener) {
        bind(listener);
        listener.onResponse(value);
    }

    Dynamic(T value){
        this.value = value;
    }

    void setValue(T value) {
        this.value = value;
        listener.onResponse(value);
    }
}
