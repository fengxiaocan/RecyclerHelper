package com.evil.recyclerhelper;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.evil.helper.recycler.adapter.ComRecyclerViewAdapter;
import com.evil.helper.recycler.holder.RecyclerViewHolder;
import com.evil.helper.recycler.holder.SimpleRecyclerViewHolder;
import com.evil.helper.recycler.inface.IRecycleData;

public class TestAdapter extends ComRecyclerViewAdapter<IRecycleData,RecyclerViewHolder<IRecycleData>> {
	@Override
	public boolean attachParent() {
		return false;
	}
	
	@Override
	public RecyclerViewHolder<IRecycleData> createViewHolder(View view,int viewType) {
		return new SimpleRecyclerViewHolder<IRecycleData>(view);
	}
	
	@Override
	protected void onBindDefaultData(
			RecyclerViewHolder<IRecycleData> holder,int position)
	{
	
	}
	
	
	@Override
	public int onCreateLayoutRes(int viewType) {
		return R.layout.holder_test;
	}
	
}
