package com.evil.helper.recycler.holder;

import android.view.View;

import com.evil.helper.recycler.adapter.MultipleRecyclerViewAdapter;
import com.evil.helper.recycler.inface.IRecycleData;

public abstract class MultipleRecyclerViewHolder<T extends IRecycleData> extends BaseRecyclerHolder {


    public MultipleRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setData(MultipleRecyclerViewAdapter adapter,T t,int position);

}
