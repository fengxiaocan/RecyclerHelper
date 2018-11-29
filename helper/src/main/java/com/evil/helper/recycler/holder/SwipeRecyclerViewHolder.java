package com.evil.helper.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.evil.helper.recycler.inface.IRecycleData;
import com.evil.helper.recycler.menu.MenuDragLayout;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/6/18
 * @desc ...
 */
public abstract class SwipeRecyclerViewHolder<T extends IRecycleData,A extends RecyclerView.Adapter> extends RecyclerViewHolder<T,A> {
	public View contentView;
	public View menuView;
	
	public SwipeRecyclerViewHolder(MenuDragLayout layout) {
		super(layout);
	}
	
	public abstract void initMenu(View menuLayout);
	
	@Override
	public View getItemView() {
		return contentView;
	}
}
