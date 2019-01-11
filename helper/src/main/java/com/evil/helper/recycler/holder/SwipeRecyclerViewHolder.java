package com.evil.helper.recycler.holder;

import android.view.View;

import com.evil.helper.recycler.inface.IRecycleData;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/6/18
 * @desc ...
 */
public abstract class SwipeRecyclerViewHolder<T extends IRecycleData> extends RecyclerViewHolder<T> {
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
}
