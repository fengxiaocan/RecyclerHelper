package com.evil.helper.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.evil.helper.recycler.adapter.RecyclerViewAdapter;

public abstract class RecyclerViewHolder<T,A extends RecyclerView.Adapter> extends BaseRecyclerHolder {

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setData(A adapter,T t,int position);
}
