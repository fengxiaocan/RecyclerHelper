package com.evil.recyclerhelper;

import android.view.View;

import com.evil.helper.recycler.adapter.ComRecyclerViewAdapter;
import com.evil.helper.recycler.holder.RecyclerViewHolder;
import com.evil.helper.recycler.inface.IRecycleData;

public class TestAdapter extends ComRecyclerViewAdapter<IRecycleData,RecyclerViewHolder<IRecycleData,TestAdapter>,TestAdapter> {
	@Override
	public boolean attachParent() {
		return false;
	}
	
	@Override
	public RecyclerViewHolder<IRecycleData,TestAdapter> createViewHolder(View view,int viewType) {
		return new RecyclerViewHolder<IRecycleData,TestAdapter>(view) {
			@Override
			public void setData(TestAdapter adapter,IRecycleData iRecycleData,int position) {
			
			}
			
			@Override
			public void initView(View rootView) {
			
			}
		};
	}
	
	@Override
	protected void onBindDefaultData(
			RecyclerViewHolder<IRecycleData,TestAdapter> holder,int position)
	{
	
	}
	
	@Override
	public int onCreateLayoutRes(int viewType) {
		return R.layout.holder_test;
	}
	
}
