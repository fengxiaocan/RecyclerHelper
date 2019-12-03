package com.evil.recycler.holder;

import android.view.View;

public class SimpleRecyclerViewHolder<T> extends RecyclerViewHolder<T> {

    public SimpleRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindData(T t) {

    }

    @Override
    public void initView(View rootView) {

    }
}
