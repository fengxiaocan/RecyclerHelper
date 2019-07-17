package com.evil.recycler.holder;

import android.view.View;
import android.view.ViewGroup;

import com.evil.recycler.adapter.SwipeRecyclerViewAdapter;
import com.evil.recycler.inface.IRecyclerData;
import com.evil.recycler.inface.OnMenuItemClickListener;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/6/18
 * @desc ...
 */
public abstract class SwipeRecyclerViewHolder<T extends IRecyclerData> extends RecyclerViewHolder<T> {
	protected View contentView;
	protected View menuView;
	
	public SwipeRecyclerViewHolder(View layout) {
		super(layout);
	}
	
	public abstract void initMenu(View menuLayout);
	
	public View getContentView() {
		return contentView;
	}
	
	public void setContentView(View contentView) {
		this.contentView = contentView;
	}
	
	public View getMenuView() {
		return menuView;
	}
	
	public void setMenuView(View menuView) {
		this.menuView = menuView;
	}
	
	@Override
	public View getItemView() {
		return contentView;
	}

	@Override
	public void onBindData(RecyclerView.Adapter adapter, final int position) {
		SwipeRecyclerViewAdapter viewAdapter = (SwipeRecyclerViewAdapter) adapter;
		final OnMenuItemClickListener onMenuItemClickListener = viewAdapter.getOnMenuItemClickListener();
		if (onMenuItemClickListener != null) {
			if (menuView instanceof ViewGroup) {
				for (int i = 0;i < ((ViewGroup)menuView).getChildCount();i++) {
					View child = ((ViewGroup)menuView).getChildAt(i);
					final int childPosition = i;
					if (child != null) {
						child.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								onMenuItemClickListener
										.onMenuItemClick(v,position,childPosition);
							}
						});
					}
				}
			}
			else {
				menuView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						onMenuItemClickListener.onMenuItemClick(v,position,0);
					}
				});
			}
		}
	}
}
