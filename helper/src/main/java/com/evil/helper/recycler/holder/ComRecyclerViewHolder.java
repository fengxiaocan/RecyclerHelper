package com.evil.helper.recycler.holder;

import android.view.View;

import com.evil.helper.recycler.adapter.ComRecyclerViewAdapter;

public abstract class ComRecyclerViewHolder<T> extends BaseRecyclerHolder {

    public ComRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setData(ComRecyclerViewAdapter adapter,T t,int position);
}
