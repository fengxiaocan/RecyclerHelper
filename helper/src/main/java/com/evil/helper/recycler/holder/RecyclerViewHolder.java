package com.evil.helper.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class RecyclerViewHolder<T> extends BaseRecyclerHolder {
	
	public RecyclerViewHolder(View itemView) {
		super(itemView);
	}
	
	public abstract void setData(RecyclerView.Adapter adapter,T t,int position);
}
