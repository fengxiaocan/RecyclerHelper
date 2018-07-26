package com.evil.helper.recycler.holder;

import android.view.View;

import com.evil.helper.recycler.adapter.RecyclerViewAdapter;

public abstract class RecyclerViewHolder<T> extends BaseRecyclerHolder {

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setData(RecyclerViewAdapter adapter,T t,int position);
}
