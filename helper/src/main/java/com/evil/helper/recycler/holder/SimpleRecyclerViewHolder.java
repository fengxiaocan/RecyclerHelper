package com.evil.helper.recycler.holder;

import android.view.View;

import com.evil.helper.recycler.adapter.MultipleRecyclerViewAdapter;
import com.evil.helper.recycler.adapter.SimpleRecyclerViewAdapter;
import com.evil.helper.recycler.inface.IRecycleData;

public abstract class SimpleRecyclerViewHolder<T > extends BaseRecyclerHolder {
    
    public SimpleRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setData(SimpleRecyclerViewAdapter adapter,T data,int position);
}
