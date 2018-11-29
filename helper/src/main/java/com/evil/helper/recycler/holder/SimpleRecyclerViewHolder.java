package com.evil.helper.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SimpleRecyclerViewHolder<T> extends RecyclerViewHolder<T> {

    public SimpleRecyclerViewHolder(View itemView) {
        super(itemView);
    }
    
    @Override
    public void initView(View rootView) {
    
    }
    
    @Override
    public void setData(RecyclerView.Adapter adapter,T t,int position) {
    
    }
    
}
