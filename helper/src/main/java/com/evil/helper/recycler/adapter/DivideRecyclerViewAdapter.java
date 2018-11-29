package com.evil.helper.recycler.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evil.helper.recycler.holder.BaseRecyclerHolder;
import com.evil.helper.recycler.holder.DivideRecyclerViewHolder;
import com.evil.helper.recycler.holder.EmptyViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Recycler view adapter.
 * 一个可以分割几份的adapter
 *
 * @param <T> the type parameter
 * @param <V> the type parameter
 */
public abstract class DivideRecyclerViewAdapter<T,V extends DivideRecyclerViewHolder<T>> extends RecyclerView.Adapter<BaseRecyclerHolder> implements IExtendAdapter<T> {
	protected View mEmptyView;
	protected int mEmptyViewLayout;
	protected int mEmptyType = EMPTY_VIEW_TYPE;
	/**
	 * The M datas.
	 */
	protected List<T> mDatas;
	
	protected abstract int divideNumber();
	
	public int getDivideCount() {
		int realItemCount = getRealItemCount();
		return realItemCount / divideNumber() + realItemCount % divideNumber();
	}
	
	@Override
	public int getItemCount() {
		//		return super.getItemCount();
		if (isEmpty() && isHasEmptyView()) {
			return 1;
		}
		return getDivideCount();
	}
	
	/**
	 * Gets datas.
	 *
	 * @return the datas
	 */
	public List<T> getDatas() {
		return mDatas;
	}
	
	/**
	 * Sets datas.
	 *
	 * @param datas the datas
	 */
	public void setDatas(List<T> datas) {
		mDatas = datas;
	}
	
	/**
	 * Sets datas.
	 *
	 * @param datas the datas
	 */
	public void setDatas(T... datas) {
		if (datas != null) {
			if (mDatas == null) {
				mDatas = new ArrayList<>();
			}
			mDatas.clear();
			for (T data : datas) {
				mDatas.add(data);
			}
		}
	}
	
	@Override
	public T getData(int position) {
		return mDatas.get(position);
	}
	
	/**
	 * Sets datas and notify.
	 *
	 * @param datas the datas
	 */
	public void setDatasAndNotify(T... datas) {
		setDatas(datas);
		notifyDataSetChanged();
	}
	
	/**
	 * Sets datas and notify.
	 *
	 * @param datas the datas
	 */
	public void setDatasAndNotify(List<T> datas) {
		mDatas = datas;
		notifyDataSetChanged();
	}
	
	/**
	 * Add datas.
	 *
	 * @param datas the datas
	 */
	public void addDatas(List<T> datas) {
		if (mDatas == null) {
			mDatas = datas;
		}
		else {
			mDatas.addAll(datas);
		}
	}
	
	/**
	 * Add datas.
	 *
	 * @param datas the datas
	 */
	public void addDatas(T... datas) {
		if (datas != null) {
			setDatas(datas);
		}
		if (mDatas == null) {
			mDatas = new ArrayList<>();
		}
		for (T data : datas) {
			mDatas.add(data);
		}
	}
	
	/**
	 * Add data.
	 *
	 * @param data the data
	 */
	public void addData(T data) {
		if (mDatas == null) {
			mDatas = new ArrayList<>();
		}
		mDatas.add(data);
	}
	
	/**
	 * Clear.
	 */
	public void clear() {
		mDatas = new ArrayList<>();
	}
	
	/**
	 * Clear and notify.
	 */
	public void clearAndNotify() {
		clear();
		notifyDataSetChanged();
	}
	
	/**
	 * Add data.
	 *
	 * @param position the position
	 * @param data the data
	 */
	public void insertData(int position,T data) {
		if (mDatas == null) {
			mDatas = new ArrayList<>();
			mDatas.add(data);
		}
		else {
			if (position < 0) {
				mDatas.add(0,data);
			}
			else if (position >= mDatas.size()) {
				mDatas.add(data);
			}
			else {
				mDatas.add(position,data);
			}
		}
	}
	
	/**
	 * Add data and notify.
	 *
	 * @param position the position
	 * @param data the data
	 */
	public void insertDataAndNotify(int position,T data) {
		insertData(position,data);
		notifyDataSetChanged();
	}
	
	/**
	 * Add data and notify.
	 *
	 * @param data the data
	 */
	public void addDataAndNotify(T data) {
		addData(data);
		notifyDataSetChanged();
	}
	
	/**
	 * Add datas and notify.
	 *
	 * @param datas the datas
	 */
	public void addDatasAndNotify(List<T> datas) {
		addDatas(datas);
		notifyDataSetChanged();
	}
	
	@Override
	public T getFirstData() {
		return ListUtils.getFirstData(mDatas);
	}
	
	@Override
	public T getLastData() {
		return ListUtils.getLastData(mDatas);
	}
	
	@Override
	public void insertDatas(int position,List<T> datas) {
		if (ObjectUtils.isEmpty(datas)) {
			return;
		}
		if (ObjectUtils.isEmpty(mDatas)) {
			mDatas = datas;
		}
		else {
			if (position <= 0) {
				//position位于第一
				datas.addAll(mDatas);
				mDatas = datas;
			}
			else if (position >= mDatas.size()) {
				//position位于最后
				mDatas.addAll(datas);
			}
			else {
				//position位于中间
				for (int size = datas.size();size > 0;size--) {
					mDatas.add(position,datas.get(size));
				}
			}
		}
	}
	
	@Override
	public void insertDatasAndNotify(int position,List<T> datas) {
		insertDatas(position,datas);
		notifyDataSetChanged();
	}
	
	/**
	 * Remove.
	 *
	 * @param position the position
	 */
	public void remove(int position) {
		mDatas.remove(position);
	}
	
	/**
	 * Remove and notify.
	 *
	 * @param position the position
	 */
	public void removeAndNotify(int position) {
		mDatas.remove(position);
		notifyDataSetChanged();
	}
	
	/**
	 * Remove.
	 *
	 * @param t the t
	 */
	public void remove(T t) {
		mDatas.remove(t);
	}
	
	/**
	 * Remove and notify.
	 *
	 * @param t the t
	 */
	public void removeAndNotify(T t) {
		mDatas.remove(t);
		notifyDataSetChanged();
	}
	
	@Override
	public void onBindViewHolder(@NonNull BaseRecyclerHolder holder,final int position) {
		holder.onBindData(this,position);
		if (holder instanceof DivideRecyclerViewHolder) {
			int realPosition = position * divideNumber();
			for (int i = 0;i < divideNumber();i++) {
				if (realPosition < getRealItemCount()) {
					((DivideRecyclerViewHolder)holder)
							.setData(this,i,getData(realPosition+i),realPosition+i);
				}
				else {
					((DivideRecyclerViewHolder)holder).noneData(this,i);
				}
			}
		}
	}
	
	@NonNull
	@Override
	public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
		if (isEmpty() && isHasEmptyView()) {
			return new EmptyViewHolder(mEmptyView);
		}
		View view;
		if (attachParent()) {
			view = LayoutInflater.from(parent.getContext())
			                     .inflate(onCreateLayoutRes(viewType),parent,false);//解决高度不全问题
			
		}
		else {
			view = LayoutInflater.from(parent.getContext())
			                     .inflate(onCreateLayoutRes(viewType),null);//解决宽度不能铺满
		}
		return createViewHolder(view,viewType);
	}
	
	public abstract boolean attachParent();
	
	/**
	 * Create view holder v.
	 * 重载方法,少写代码
	 *
	 * @param view the view
	 * @return the v
	 */
	public abstract V createViewHolder(View view,int viewType);
	
	/**
	 * On create layout res int.
	 * 获取创建的布局文件,不需要inflate
	 *
	 * @param viewType the view type
	 * @return the int
	 */
	public abstract @LayoutRes
	int onCreateLayoutRes(int viewType);
	
	@Override
	public int getItemViewType(int position) {
		if (isHasEmptyView() && isEmpty()) {
			return mEmptyType;
		}
		return 0;
	}
	
	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public int getRealItemCount() {
		if (mDatas != null) {
			return mDatas.size();
		}
		return 0;
	}
	
	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
		if (manager instanceof GridLayoutManager) {
			((GridLayoutManager)manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
				@Override
				public int getSpanSize(int position) {
					//如果是头布局或者是脚布局返回为1;
					if (isEmpty() && isHasEmptyView()) {
						return ((GridLayoutManager)manager).getSpanCount();
					}
					return 1;
				}
			});
		}
	}
	
	/**
	 * 针对流式布局
	 *
	 * @param holder
	 */
	@Override
	public void onViewAttachedToWindow(@NonNull BaseRecyclerHolder holder) {
		super.onViewAttachedToWindow(holder);
		int layoutPosition = holder.getLayoutPosition();
		if ((isEmpty() && isHasEmptyView())) {
			ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
			if (layoutParams != null) {
				if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
					StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams)layoutParams;
					//占领全部空间;
					params.setFullSpan(true);
				}
			}
		}
	}
	
	@Override
	public void setEmptyView(int layout,ViewGroup referenceViewGroup) {
		if (referenceViewGroup != null && mEmptyViewLayout != layout) {
			LayoutInflater from = LayoutInflater.from(referenceViewGroup.getContext());
			mEmptyView = from.inflate(layout,referenceViewGroup,false);
			mEmptyViewLayout = layout;
			mEmptyType = mEmptyType == EMPTY_VIEW_TYPE ? EMPTY_VIEW_TYPE2 : EMPTY_VIEW_TYPE;
			notifyDataSetChanged();
		}
	}
	
	@Override
	public void setEmptyView(View emptyView) {
		mEmptyViewLayout = 0;
		mEmptyType = mEmptyType == EMPTY_VIEW_TYPE ? EMPTY_VIEW_TYPE2 : EMPTY_VIEW_TYPE;
		mEmptyView = emptyView;
		notifyDataSetChanged();
	}
	
	@Override
	public boolean isEmpty() {
		return getRealItemCount() == 0;
	}
	
	@Override
	public boolean isHasEmptyView() {
		return mEmptyView != null;
	}
}
