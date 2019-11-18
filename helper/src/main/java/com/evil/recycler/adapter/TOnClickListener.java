package com.evil.recycler.adapter;

import android.view.View;

abstract class TOnClickListener<T> implements View.OnClickListener {
    protected T t;

    public TOnClickListener(T t) {
        this.t = t;
    }

    public T getData() {
        return t;
    }
}
