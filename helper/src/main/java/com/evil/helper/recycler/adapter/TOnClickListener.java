package com.evil.helper.recycler.adapter;

import android.view.View;

/**
 * @项目名： MyComUtils
 * @包名： com.dgtle.baselib.lib.impl
 * @创建者: Noah.冯
 * @时间: 12:01
 * @描述： TODO
 */
abstract class TOnClickListener<T> implements View.OnClickListener {
    protected T t;

    public TOnClickListener(T t) {
        this.t = t;
    }
    public T getData(){
        return t;
    }
}
