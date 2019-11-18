package com.evil.recycler.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 11/6/18
 * @desc ...
 */
public class LoadingRecyclerView {
    private int viewType;
    private BaseRecyclerHolder holder;


    public LoadingRecyclerView(int viewType, View rootView) {
        this.viewType = viewType;
        BaseRecyclerHolder.removeParent(rootView);
        this.holder = new SimpleRecyclerHolder(rootView);
        this.holder.isStaggeredGridFullSpan(true);
    }

    public LoadingRecyclerView(int viewType, BaseRecyclerHolder holder) {
        this.viewType = viewType;
        BaseRecyclerHolder.removeParent(holder.itemView);
        this.holder = holder;
        this.holder.isStaggeredGridFullSpan(true);
    }

    public LoadingRecyclerView(Context context, int viewType, int layoutId) {
        this.viewType = viewType;
        this.holder = new SimpleRecyclerHolder(
                LayoutInflater.from(context).inflate(layoutId, null));
        this.holder.isStaggeredGridFullSpan(true);
    }

    public BaseRecyclerHolder getHolder() {
        return holder;
    }

    public void setHolder(BaseRecyclerHolder holder) {
        BaseRecyclerHolder.removeParent(holder.itemView);
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
