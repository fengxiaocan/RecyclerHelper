package com.evil.recyclerhelper;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evil.helper.recycler.adapter.SwipeRecyclerViewAdapter;
import com.evil.helper.recycler.holder.SwipeRecyclerViewHolder;
import com.evil.helper.recycler.inface.IRecycleData;
import com.evil.helper.recycler.menu.IMenuDragView;
import com.evil.helper.recycler.menu.MenuDragLayout;

public class TestAdapter extends SwipeRecyclerViewAdapter<IRecycleData,SwipeRecyclerViewHolder<IRecycleData>> {
	
	@Override
	public SwipeRecyclerViewHolder<IRecycleData> onCreateWithMenuHolder(
			View layout,int viewType)
	{
		return new SwipeRecyclerViewHolder<IRecycleData>(layout) {
			private TextView mTextView;
			
			public void initMenu(View menuLayout) {
			}
			
			public void setData(
					RecyclerView.Adapter adapter,IRecycleData iRecycleData,int position)
			{
				String text = "哈哈哈";
				for (int i = 0;i < position;i++) {
					text += "\n哈哈哈";
				}
				mTextView.setText(text);
			}
			
			public void initView(View rootView) {
				mTextView = findViewById(R.id.tv_title);
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
