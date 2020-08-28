package com.evil.recyclerhelper;


import android.view.View;
import android.widget.TextView;

import com.evil.recycler.adapter.SwipeRecyclerViewAdapter;
import com.evil.recycler.holder.SwipeRecyclerViewHolder;

public class TestAdapter extends SwipeRecyclerViewAdapter<RecyclerData,SwipeRecyclerViewHolder<RecyclerData>> {
	
	@Override
	public SwipeRecyclerViewHolder<RecyclerData> onCreateWithMenuHolder(
			View layout,int viewType)
	{
		return new SwipeRecyclerViewHolder<RecyclerData>(layout) {
			@Override
			public void onBindData(RecyclerData data) {
				mTextView.setText(data.name);
			}

			private TextView mTextView;
			
			public void initMenu(View menuLayout) {
			}
			
			public void initView(View rootView) {
				mTextView =  findViewById(R.id.tv_title);
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
	public SwipeRecyclerViewHolder<RecyclerData> createViewHolder(View view,int viewType) {
		return null;
	}
	
	@Override
	public int onCreateLayoutRes(int viewType) {
		return R.layout.holder_test;
	}
}
