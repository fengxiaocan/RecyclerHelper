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
public class ErrorRecyclerView {
    private int viewType ;
    private BaseRecyclerHolder holder;


    public ErrorRecyclerView(int viewType, View rootView) {
        this.viewType = viewType;
        this.holder = new SimpleRecyclerHolder(rootView);
        this.holder.isStaggeredGridFullSpan(true);
    }

    public ErrorRecyclerView(int viewType, BaseRecyclerHolder holder) {
        this.viewType = viewType;
        this.holder = holder;
        this.holder.isStaggeredGridFullSpan(true);
    }

    public ErrorRecyclerView(Context context, int viewType, int layoutId) {
        this.viewType = viewType;
        this.holder = new SimpleRecyclerHolder(
                LayoutInflater.from(context).inflate(layoutId, null));
        this.holder.isStaggeredGridFullSpan(true);
    }

    public BaseRecyclerHolder getHolder() {
        return holder;
    }

    public void setHolder(BaseRecyclerHolder holder) {
        this.holder = holder;
        this.holder.isStaggeredGridFullSpan(true);
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
