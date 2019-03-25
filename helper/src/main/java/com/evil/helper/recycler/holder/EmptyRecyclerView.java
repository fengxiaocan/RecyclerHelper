package com.evil.helper.recycler.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.evil.helper.recycler.adapter.IExtendAdapter;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 11/6/18
 * @desc ...
 */
public class EmptyRecyclerView {
    private int viewType = IExtendAdapter.EMPTY_VIEW_TYPE;
    private BaseRecyclerHolder holder;

    public EmptyRecyclerView(View layout) {
        this.holder = new SimpleRecyclerHolder(layout);
    }

    public EmptyRecyclerView(int viewType, View rootView) {
        this.viewType = viewType;
        this.holder = new SimpleRecyclerHolder(rootView);
    }

    public EmptyRecyclerView(BaseRecyclerHolder holder) {
        this.holder = holder;
    }

    public EmptyRecyclerView(int viewType, BaseRecyclerHolder holder) {
        this.viewType = viewType;
        this.holder = holder;
    }

    public EmptyRecyclerView(Context context, int viewType, int layoutId) {
        this.viewType = viewType;
        this.holder = new SimpleRecyclerHolder(
                LayoutInflater.from(context).inflate(layoutId, null));
    }

    public EmptyRecyclerView(Context context, int layoutId) {
        this.holder = new SimpleRecyclerHolder(
                LayoutInflater.from(context).inflate(layoutId, null));
    }

    public BaseRecyclerHolder getHolder() {
        return holder;
    }

    public void setHolder(BaseRecyclerHolder holder) {
        this.holder = holder;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
