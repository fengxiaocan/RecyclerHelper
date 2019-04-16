package com.evil.helper.recycler.holder;

import android.view.View;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 11/6/18
 * @desc ...
 */
public class RecyclerViewFooter {
    private int viewType;
    private BaseRecyclerHolder holder;

    public RecyclerViewFooter(int viewType,BaseRecyclerHolder holder) {
        this.viewType = viewType;
        this.holder = holder;
        this.holder.isStaggeredGridFullSpan(true);
    }

    public RecyclerViewFooter(int viewType,View rootView) {
        this.viewType = viewType;
        this.holder = new HeadTallRecyclerHolder(rootView);
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
