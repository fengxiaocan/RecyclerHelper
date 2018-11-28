package com.evil.helper.recycler.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;

import com.evil.helper.recycler.inface.OnPositionListener;

public class AroundItemDecoration extends RecyclerView.ItemDecoration {
	//	public static final int FAR_FIRST = 0x0001;
	//	public static final int FAR_LAST = 0x0010;
	//	public static final int CENTER = 0x0100;
	//	public static final int ALL = FAR_FIRST | FAR_LAST | CENTER;
	private SparseArray<AroundDecorationProps> mPropMap; // itemType -> prop
	private AroundDecorationProps mItemDecorationProps;
	private boolean mIgnoreViewType;//是否忽略ViewType
	private int decorationColor = -1;
	
	public AroundItemDecoration(SparseArray<AroundDecorationProps> propMap) {
		mPropMap = propMap;
	}
	
	public AroundItemDecoration(
			AroundDecorationProps center)
	{
		mIgnoreViewType = true;
		mItemDecorationProps = center;
	}
	
	public AroundItemDecoration(int itemType,AroundDecorationProps itemDecorationProps) {
		mPropMap = new SparseArray<>();
		mPropMap.put(itemType,itemDecorationProps);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public AroundItemDecoration addDecoration(
			int itemType,AroundDecorationProps itemDecorationProps)
	{
		mIgnoreViewType = false;
		if (mPropMap == null) {
			mPropMap = new SparseArray<>();
		}
		mPropMap.put(itemType,itemDecorationProps);
		return this;
	}
	
	public AroundItemDecoration setDecoration(
			AroundDecorationProps itemDecorationProps)
	{
		mIgnoreViewType = true;
		mItemDecorationProps = itemDecorationProps;
		return this;
	}
	
	public AroundItemDecoration setDecorationColor(int decorationColor) {
		this.decorationColor = decorationColor;
		return this;
	}
	
	@Override
	public void onDraw(
			Canvas c,RecyclerView parent,RecyclerView.State state)
	{
		super.onDraw(c,parent,state);
		if (decorationColor > 0) {
			c.drawColor(decorationColor);
		}
	}
	
	@Override
	public void getItemOffsets(
			Rect outRect,View view,RecyclerView parent,RecyclerView.State state)
	{
		int position = parent.getChildAdapterPosition(view);
		RecyclerView.Adapter adapter = parent.getAdapter();
		int itemType = adapter.getItemViewType(position);
		
		AroundDecorationProps props;
		if (mIgnoreViewType) {
			props = mItemDecorationProps;
		}
		else {
			if (mPropMap != null) {
				props = mPropMap.get(itemType);
			}
			else {
				return;
			}
		}
		if (props == null) {
			return;
		}
		int spanIndex = 0;
		int spanCount = 1;
		if (parent.getLayoutManager() instanceof GridLayoutManager) {
			GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams)view
					.getLayoutParams();
			spanIndex = lp.getSpanIndex();
			GridLayoutManager layoutManager = (GridLayoutManager)parent.getLayoutManager();
			spanCount = layoutManager
					.getSpanCount(); // Assume that there're spanCount items in this row/column.
		}
		else if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
			StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams)view
					.getLayoutParams();
			spanIndex = lp.getSpanIndex();
			StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager)parent
					.getLayoutManager();
			spanCount = layoutManager
					.getSpanCount(); // Assume that there're spanCount items in this row/column.
		}
		
		boolean isFirstRowOrColumn, isLastRowOrColumn;
		int prePos = position > 0 ? position - 1 : -1;
		int nextPos = position < adapter.getItemCount() - 1 ? position + 1 : -1;
		// Last position on the last row 上一行的最后一个位置
		int preRowPos = position > spanIndex ? position - (1 + spanIndex) : -1;
		// First position on the next row 下一行的第一个位置
		int nextRowPos = position < adapter.getItemCount() - (spanCount - spanIndex) ? position + (
				spanCount
				- spanIndex) : -1;
		isFirstRowOrColumn = position == 0 || prePos == -1 || (!mIgnoreViewType
		                                                       && itemType != adapter
				.getItemViewType(prePos)) || preRowPos == -1 || (!mIgnoreViewType
		                                                         && itemType != adapter
				.getItemViewType(preRowPos));
		isLastRowOrColumn = position == adapter.getItemCount() - 1
		                    || nextPos == -1
		                    || (!mIgnoreViewType && itemType != adapter.getItemViewType(nextPos))
		                    || nextRowPos == -1
		                    || (!mIgnoreViewType && itemType != adapter
				.getItemViewType(nextRowPos));
		
		int left, top, right, bottom;
		
		left = props.getLeftSpace();
		right = props.getRightSpace();
		top = props.getTopSpace();
		bottom = props.getBottomSpace();
		
		outRect.set(left,top,right,bottom);
		
		OnPositionListener listener = props.getOnPositionListener();
		if (listener != null){
			listener.onLocation(position,spanIndex,outRect);
		}
		
		if (isFirstRowOrColumn) {
			if (listener != null){
				listener.onFirstLocation(spanIndex,outRect);
			}
		}
		if (isLastRowOrColumn) { // Last row
			if (listener != null){
				listener.onLastLocation(spanIndex,outRect);
			}
		}
		
		
	}
	
	public static class Builder {
		private SparseArray<AroundDecorationProps> mPropMap; // itemType -> prop
		private AroundDecorationProps mItemDecorationProps;
		
		private boolean mIgnoreViewType;//是否忽略ViewType
		
		public Builder() {
			mPropMap = new SparseArray<>();
		}
		
		public Builder(SparseArray<AroundDecorationProps> propMap) {
			mPropMap = propMap;
		}
		
		public Builder(int itemType,AroundDecorationProps itemDecorationProps) {
			mPropMap = new SparseArray<>();
			mPropMap.put(itemType,itemDecorationProps);
		}
		
		public AroundItemDecoration build() {
			if (mIgnoreViewType) {
				return new AroundItemDecoration(mItemDecorationProps);
			}
			return new AroundItemDecoration(mPropMap);
		}
		
		public Builder addDecoration(
				int itemType,AroundDecorationProps itemDecorationProps)
		{
			if (mPropMap == null) {
				mPropMap = new SparseArray<>();
			}
			mPropMap.put(itemType,itemDecorationProps);
			return this;
		}
		
		public Builder addDecoration(
				AroundDecorationProps itemDecorationProps)
		{
			mIgnoreViewType = true;
			mItemDecorationProps = itemDecorationProps;
			return this;
		}
	}
}
