package com.evil.recyclerhelper;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.evil.helper.recycler.adapter.ComRecyclerViewAdapter;
import com.evil.helper.recycler.adapter.SwipeRecyclerViewAdapter;
import com.evil.helper.recycler.holder.RecyclerViewHolder;
import com.evil.helper.recycler.holder.SimpleRecyclerViewHolder;
import com.evil.helper.recycler.holder.SwipeRecyclerViewHolder;
import com.evil.helper.recycler.inface.IRecycleData;
import com.evil.helper.recycler.menu.MenuDragLayout;

public class TestAdapter extends SwipeRecyclerViewAdapter<IRecycleData,SwipeRecyclerViewHolder<IRecycleData>> {
	
	@Override
	public SwipeRecyclerViewHolder<IRecycleData> onCreateWithMenuHolder(
			MenuDragLayout layout,int viewType)
	{
		return new SwipeRecyclerViewHolder<IRecycleData>(layout) {
			public void initMenu(View menuLayout) {
			
			}
			
			public void setData(
					RecyclerView.Adapter adapter,IRecycleData iRecycleData,int position)
			{
			
			}
			
			public void initView(View rootView) {
			
			}
		};
	}
	
	@Override
	public int onCreateRightMenuLayout(int viewType) {
		return R.layout.holder_delete;
	}
	
	@Override
	public boolean attachParent() {
		return true;
	}
	
	@Override
	public SwipeRecyclerViewHolder<IRecycleData> createViewHolder(View view,int viewType) {
		return null;
	}
	
	@Override
	public int onCreateLayoutRes(int viewType) {
		return R.layout.holder_test;
	}
}
