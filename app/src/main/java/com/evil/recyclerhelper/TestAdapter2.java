package com.evil.recyclerhelper;

import android.view.View;
import android.widget.TextView;

import com.evil.helper.recycler.adapter.ComRecyclerViewAdapter;
import com.evil.helper.recycler.adapter.SwipeRecyclerViewAdapter;
import com.evil.helper.recycler.holder.RecyclerViewHolder;
import com.evil.helper.recycler.holder.SwipeRecyclerViewHolder;
import com.evil.helper.recycler.inface.IRecycleData;

import androidx.recyclerview.widget.RecyclerView;

public class TestAdapter2 extends
		ComRecyclerViewAdapter<IRecycleData, RecyclerViewHolder<IRecycleData>> {


	@Override
	public boolean attachParent() {
		return true;
	}
	
	@Override
	public RecyclerViewHolder<IRecycleData> createViewHolder(View view,int viewType) {
		return new RecyclerViewHolder<IRecycleData>(view) {
			private TextView mTvTitle;

			@Override
			public void initView(View rootView) {
				mTvTitle = findViewById(R.id.tv_title);
			}

			@Override
			public void setData(RecyclerView.Adapter adapter, IRecycleData iRecycleData,
					int position)
			{

			}
		};
	}

	@Override
	protected void onBindDefaultData(RecyclerViewHolder<IRecycleData> holder, int position) {

	}

	@Override
	public int onCreateLayoutRes(int viewType) {
		return R.layout.holder_test2;
	}
}
