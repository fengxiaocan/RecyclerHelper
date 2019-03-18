package com.evil.helper.recycler.holder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public abstract class DivideRecyclerViewHolder<T> extends BaseRecyclerHolder {
	
	public DivideRecyclerViewHolder(View itemView) {
		super(itemView);
	}
	
	public abstract void setData(
			RecyclerView.Adapter adapter,int index,T t,int realPosition);
	
	public abstract void noneData(RecyclerView.Adapter adapter,int index);
}
