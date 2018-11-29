package com.evil.helper.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class DivideRecyclerViewHolder<T> extends BaseRecyclerHolder {
	
	public DivideRecyclerViewHolder(View itemView) {
		super(itemView);
	}
	
	public abstract void setData(
			RecyclerView.Adapter adapter,int index,T t,int realPosition);
}
