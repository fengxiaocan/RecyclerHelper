package com.evil.helper.recycler.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evil.helper.recycler.holder.BaseRecyclerHolder;
import com.evil.helper.recycler.holder.ComRecyclerViewHolder;
import com.evil.helper.recycler.holder.EmptyViewHolder;
import com.evil.helper.recycler.holder.RecyclerViewFooter;
import com.evil.helper.recycler.holder.RecyclerViewHeader;
import com.evil.helper.recycler.inface.IRecycleData;
import com.evil.helper.recycler.inface.OnAdapterItemClickListener;
import com.evil.helper.recycler.inface.OnFooterClickListener;
import com.evil.helper.recycler.inface.OnHeaderClickListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 11/6/18
 * @desc 一个可以添加头部跟尾部View的 recyclerview adapter
 * @注意 viewType尽量不要使用负数,使用也不能小等于 -0x11111
 */
public abstract class ComRecyclerViewAdapter<T extends IRecycleData,V extends ComRecyclerViewHolder<T>> extends RecyclerView.Adapter<BaseRecyclerHolder> implements IExtendAdapter<T> {
	public static final int EXTEND_RECYCLER_HEADER_TYPE = -0x11111;//头部
	public static final int EXTEND_RECYCLER_FOOTER_TYPE = -0xfffff;//脚部
	
	protected List<T> mDatas;
	protected LinkedList<RecyclerViewHeader> mHeaders;
	protected LinkedList<RecyclerViewFooter> mFooters;
	protected View mEmptyView;
	protected int mEmptyViewLayout;
	private OnAdapterItemClickListener<T> mTOnAdapterItemClickListener;
	private OnHeaderClickListener mOnHeaderClickListener;
	private OnFooterClickListener mOnFooterClickListener;
	
	private synchronized void checkHeader() {
		if (mHeaders == null) {
			mHeaders = new LinkedList<>();
		}
	}
	
	public void addHeader(View header) {
		checkHeader();
		mHeaders.add(new RecyclerViewHeader(EXTEND_RECYCLER_HEADER_TYPE - mHeaders.size(),header));
		notifyDataSetChanged();
	}
	
	public void addHeader(BaseRecyclerHolder header) {
		checkHeader();
		mHeaders.add(new RecyclerViewHeader(EXTEND_RECYCLER_HEADER_TYPE - mHeaders.size(),header));
		notifyDataSetChanged();
	}
	
	private synchronized void checkFooter() {
		if (mFooters == null) {
			mFooters = new LinkedList<>();
		}
	}
	
	public void addFooter(View footer) {
		checkFooter();
		mFooters.add(new RecyclerViewFooter(EXTEND_RECYCLER_FOOTER_TYPE - mFooters.size(),footer));
		notifyDataSetChanged();
	}
	
	public void addFooter(BaseRecyclerHolder footer) {
		checkFooter();
		mFooters.add(new RecyclerViewFooter(EXTEND_RECYCLER_FOOTER_TYPE - mFooters.size(),footer));
		notifyDataSetChanged();
	}
	
	@Override
	public int getItemCount() {
		int itemCount = getAllItemCount();
		if (itemCount == 0) {
			if (isHasEmptyView()) {
				return 1;
			}
		}
		return itemCount;
	}
	
	/**
	 * 获取所有条目的数量
	 *
	 * @return
	 */
	public int getAllItemCount() {
		return getRealItemCount() + getHeaderCount() + getFooterCount();
	}
	
	public int getRealItemCount() {
		return mDatas == null ? 0 : mDatas.size();
	}
	
	public int getHeaderCount() {
		return mHeaders == null ? 0 : mHeaders.size();
	}
	
	public int getFooterCount() {
		return mFooters == null ? 0 : mFooters.size();
	}
	
	public boolean hasHeader() {
		return mHeaders != null && mHeaders.size() > 0;
	}
	
	public boolean hasFooter() {
		return mFooters != null && mFooters.size() > 0;
	}
	
	public void removeHeader(int position) {
		if (hasHeader()) {
			mHeaders.remove(position);
		}
		notifyDataSetChanged();
	}
	
	public void removeAllHeader() {
		mHeaders = null;
		notifyDataSetChanged();
	}
	
	public void removeFooter(int position) {
		if (hasFooter()) {
			mFooters.remove(position);
		}
		notifyDataSetChanged();
	}
	
	public void removeAllFooter() {
		mFooters = null;
		notifyDataSetChanged();
	}
	
	public boolean isHeader(int viewType) {
		return hasHeader()
		       && viewType <= EXTEND_RECYCLER_HEADER_TYPE
		       && viewType > EXTEND_RECYCLER_FOOTER_TYPE;
	}
	
	public boolean isHeaderOfPosition(int position) {
		return hasHeader() && position < getHeaderCount();
	}
	
	public boolean isFooterOfPosition(int position) {
		return hasFooter() && position >= getRealItemCount() + getHeaderCount();
	}
	
	public boolean isFooter(int viewType) {
		return hasFooter() && viewType <= EXTEND_RECYCLER_FOOTER_TYPE;
	}
	
	BaseRecyclerHolder getHeaderHolder(int viewType) {
		try {
			return mHeaders.get(EXTEND_RECYCLER_HEADER_TYPE - viewType).getHolder();
		} catch (Exception e) {
			return null;
		}
	}
	
	BaseRecyclerHolder getFooterHolder(int viewType) {
		try {
			return mFooters.get(EXTEND_RECYCLER_FOOTER_TYPE - viewType).getHolder();
		} catch (Exception e) {
			return null;
		}
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
					int itemViewType = getItemViewType(position);
					if (isHeader(itemViewType)) {
						return ((GridLayoutManager)manager).getSpanCount();
					}
					if (isFooter(itemViewType)) {
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
		if ((isEmpty() && isHasEmptyView())
		    || isHeaderOfPosition(layoutPosition)
		    || isFooterOfPosition(layoutPosition))
		{
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
	
	public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
		mOnHeaderClickListener = onHeaderClickListener;
	}
	
	public void setOnFooterClickListener(OnFooterClickListener onFooterClickListener) {
		mOnFooterClickListener = onFooterClickListener;
	}
	
	@NonNull
	@Override
	public BaseRecyclerHolder onCreateViewHolder(
			@NonNull ViewGroup parent,int viewType)
	{
		if (isEmpty() && isHasEmptyView()) {
			return new EmptyViewHolder(mEmptyView);
		}
		else if (isFooter(viewType)) {
			return getFooterHolder(viewType);
		}
		else if (isHeader(viewType)) {
			return getHeaderHolder(viewType);
		}
		else {
			LayoutInflater from = LayoutInflater.from(parent.getContext());
			View view;
			if (attachParent()) {
				view = from.inflate(onCreateLayoutRes(viewType),parent,false);
			}
			else {
				view = from.inflate(onCreateLayoutRes(viewType),null);
			}
			return createViewHolder(view,viewType);
		}
	}
	
	/**
	 * 为true 可以解决宽度显示不全的问题
	 *
	 * @return
	 */
	public abstract boolean attachParent();
	
	public abstract V createViewHolder(View view,int viewType);
	
	@Override
	public void onBindViewHolder(@NonNull BaseRecyclerHolder holder,int position) {
		if (isEmpty() && isHasEmptyView()) {
		}
		else if (isHeaderOfPosition(position)) {
			if (mOnHeaderClickListener != null) {
				holder.itemView.setOnClickListener(new TOnClickListener<Integer>(position) {
					@Override
					public void onClick(View v) {
						mOnHeaderClickListener.onHeaderClick(v,getData());
					}
				});
			}
		}
		else if (isFooterOfPosition(position)) {
			if (mOnFooterClickListener != null) {
				holder.itemView.setOnClickListener(new TOnClickListener<Integer>(
						position - (getRealItemCount() + getHeaderCount()))
				{
					@Override
					public void onClick(View v) {
						mOnFooterClickListener.onFooterClick(v,getData());
					}
				});
			}
		}
		else {
			int realPosition = position - getHeaderCount();
			if (holder instanceof ComRecyclerViewHolder) {
				((ComRecyclerViewHolder)holder).setData(this,getData(realPosition),realPosition);
			}
			if (holder != null && mTOnAdapterItemClickListener != null) {
				holder.getItemView().setOnClickListener(new OnItemClick(realPosition));
			}
			setDefaultItemData(holder,realPosition);
		}
	}
	
	protected abstract void setDefaultItemData(BaseRecyclerHolder holder,int position);
	
	@Override
	public int getItemViewType(int position) {
		if (isEmpty() && isHasEmptyView()) {
			return EMPTY_VIEW_TYPE;
		}
		if (isHeaderOfPosition(position)) {
			return mHeaders.get(position).getViewType();
		}
		else if (isFooterOfPosition(position)) {
			return mFooters.get(position - (getRealItemCount() + getHeaderCount())).getViewType();
		}
		else {
			return getData(position - getHeaderCount()).getRecycleType();
		}
	}
	
	@Override
	public void setOnItemClickListener(OnAdapterItemClickListener<T> listener) {
		mTOnAdapterItemClickListener = listener;
	}
	
	public List<T> getDatas() {
		return mDatas;
	}
	
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
	
	public void setDatas(List<T> datas) {
		mDatas = datas;
	}
	
	public void setDatasAndNotify(T... datas) {
		setDatas(datas);
		notifyDataSetChanged();
	}
	
	public void setDatasAndNotify(List<T> datas) {
		mDatas = datas;
		notifyDataSetChanged();
	}
	
	public void addDatas(List<T> datas) {
		if (mDatas == null) {
			mDatas = datas;
		}
		else {
			mDatas.addAll(datas);
		}
	}
	
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
	
	public void addData(T data) {
		if (mDatas == null) {
			mDatas = new ArrayList<>();
		}
		mDatas.add(data);
	}
	
	public void clear() {
		mDatas = new ArrayList<>();
	}
	
	public void clearAndNotify() {
		clear();
		notifyDataSetChanged();
	}
	
	public T getData(int position) {
		return mDatas.get(position);
	}
	
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
	
	public void insertDataAndNotify(int position,T data) {
		insertData(position,data);
		notifyDataSetChanged();
	}
	
	public void addDataAndNotify(T data) {
		addData(data);
		notifyDataSetChanged();
	}
	
	public void addDatasAndNotify(List<T> datas) {
		addDatas(datas);
		notifyDataSetChanged();
	}
	
	public void remove(int position) {
		mDatas.remove(position);
	}
	
	public void removeAndNotify(int position) {
		mDatas.remove(position);
		notifyDataSetChanged();
	}
	
	public void remove(T t) {
		mDatas.remove(t);
	}
	
	public void removeAndNotify(T t) {
		mDatas.remove(t);
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
	
	@Override
	public void setEmptyView(int layout,ViewGroup referenceViewGroup) {
		if (referenceViewGroup != null && mEmptyViewLayout != layout) {
			LayoutInflater from = LayoutInflater.from(referenceViewGroup.getContext());
			mEmptyView = from.inflate(layout,referenceViewGroup,false);
			mEmptyViewLayout = layout;
			notifyDataSetChanged();
		}
	}
	
	@Override
	public void setEmptyView(View emptyView) {
		mEmptyView = emptyView;
		notifyDataSetChanged();
	}
	
	@Override
	public boolean isEmpty() {
		return getAllItemCount() == 0;
	}
	
	@Override
	public boolean isHasEmptyView() {
		return mEmptyView != null;
	}
	
	
	private class OnItemClick implements View.OnClickListener {
		private int position;
		
		public OnItemClick(int position) {
			this.position = position;
		}
		
		@Override
		public void onClick(View v) {
			if (mTOnAdapterItemClickListener != null) {
				mTOnAdapterItemClickListener.onItemClick(v,getDatas(),position);
			}
		}
	}
}
