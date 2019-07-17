package com.evil.recycler.holder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public abstract class RecyclerViewHolder<T> extends BaseRecyclerHolder {
	
	public RecyclerViewHolder(View itemView) {
		super(itemView);
	}
	
	public abstract void setData(RecyclerView.Adapter adapter,T t,int position);
}
