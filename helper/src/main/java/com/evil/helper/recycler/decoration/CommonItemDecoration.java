package com.evil.helper.recycler.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;

public class CommonItemDecoration extends RecyclerView.ItemDecoration {
    private boolean mIgnoreViewType;//是否忽略ViewType
    private SparseArray<ItemDecorationProps> mPropMap; // itemType -> prop
    private ItemDecorationProps mItemDecorationProps;
    private int decorationColor = -1;

    public CommonItemDecoration(SparseArray<ItemDecorationProps> propMap) {
        mPropMap = propMap;
    }

    public CommonItemDecoration(ItemDecorationProps propMap) {
        mIgnoreViewType = true;
        mItemDecorationProps = propMap;
    }

    public CommonItemDecoration(int itemType,ItemDecorationProps itemDecorationProps) {
        mPropMap = new SparseArray<>();
        mPropMap.put(itemType,itemDecorationProps);
    }

    public static Builder builder() {
        return new Builder();
    }

    public CommonItemDecoration addDecoration(
            int itemType,ItemDecorationProps itemDecorationProps
    )
    {
        mIgnoreViewType = false;
        if (mPropMap == null) {
            mPropMap = new SparseArray<>();
        }
        mPropMap.put(itemType,itemDecorationProps);
        return this;
    }

    public CommonItemDecoration setDecoration(
            ItemDecorationProps itemDecorationProps
    )
    {
        mIgnoreViewType = true;
        mItemDecorationProps = itemDecorationProps;
        return this;
    }

    public CommonItemDecoration setDecorationColor(int decorationColor) {
        this.decorationColor = decorationColor;
        return this;
    }

    @Override
    public void onDraw(
            Canvas c,RecyclerView parent,RecyclerView.State state
    )
    {
        super.onDraw(c,parent,state);
        if (decorationColor > 0) {
            c.drawColor(decorationColor);
        }
    }

    @Override
    public void getItemOffsets(
            Rect outRect,View view,RecyclerView parent,RecyclerView.State state
    )
    {
        int position = parent.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = parent.getAdapter();
        int itemType = adapter.getItemViewType(position);

        ItemDecorationProps props;
        if (mIgnoreViewType) {
            props = mItemDecorationProps;
        } else {
            if (mPropMap != null) {
                props = mPropMap.get(itemType);
            } else {
                return;
            }
        }
        if (props == null) {
            return;
        }
        int spanIndex = 0;
        int spanSize = 1;
        int spanCount = 1;
        int orientation = OrientationHelper.VERTICAL;
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams)view.getLayoutParams();
            spanIndex = lp.getSpanIndex();
            spanSize = lp.getSpanSize();
            GridLayoutManager layoutManager = (GridLayoutManager)parent.getLayoutManager();
            spanCount = layoutManager.getSpanCount(); // Assume that there're spanCount items in this row/column.
            orientation = layoutManager.getOrientation();
        } else if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams)view
                    .getLayoutParams();
            spanIndex = lp.getSpanIndex();
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager)parent.getLayoutManager();
            spanCount = layoutManager.getSpanCount(); // Assume that there're spanCount items in this row/column.
            spanSize = lp.isFullSpan() ? spanCount : 1;
            orientation = layoutManager.getOrientation();
        } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager)parent.getLayoutManager();
            orientation = layoutManager.getOrientation();
        }

        boolean isFirstRowOrColumn, isLastRowOrColumn;
        int prePos = position > 0 ? position - 1 : -1;
        int nextPos = position < adapter.getItemCount() - 1 ? position + 1 : -1;
        // Last position on the last row 上一行的最后一个位置
        int preRowPos = position > spanIndex ? position - (1 + spanIndex) : -1;
        // First position on the next row 下一行的第一个位置
        int nextRowPos = position < adapter.getItemCount() - (spanCount - spanIndex)
                ? position +
                  (spanCount -
                   spanIndex)
                : -1;
        isFirstRowOrColumn = position == 0 ||
                             prePos == -1 ||
                             (mIgnoreViewType
                                     ? false
                                     : itemType != adapter.getItemViewType(prePos)) ||
                             preRowPos == -1 ||
                             (mIgnoreViewType
                                     ? false
                                     : itemType != adapter.getItemViewType(preRowPos));
        isLastRowOrColumn = position == adapter.getItemCount() - 1 ||
                            nextPos == -1 ||
                            (mIgnoreViewType
                                    ? false
                                    : itemType != adapter.getItemViewType(nextPos)) ||
                            nextRowPos == -1 ||
                            (mIgnoreViewType
                                    ? false
                                    : itemType != adapter.getItemViewType(nextRowPos));

        int left = 0, top = 0, right = 0, bottom = 0;

        if (orientation == GridLayoutManager.VERTICAL) {
            if (props.getHasVerticalEdge()) {
                left = props.getVerticalSpace() * (spanCount - spanIndex) / spanCount;
                right = props.getVerticalSpace() * (spanIndex + (spanSize - 1) + 1) / spanCount;
            } else {
                left = props.getVerticalSpace() * spanIndex / spanCount;
                right = props.getVerticalSpace() * (spanCount - (spanIndex + spanSize - 1) - 1) /
                        spanCount;
            }

            if (isFirstRowOrColumn) { // First row
                if (props.isHasSetFarFirstEdge()) {
                    top = props.getFarFirstSpace();
                } else if (props.getHasHorizontalEdge()) {
                    top = props.getHorizontalSpace();
                }
            }
            if (isLastRowOrColumn) { // Last row
                if (props.isHasSetFarLastEdge()) {
                    bottom = props.getFarLastSpace();
                } else if (props.getHasHorizontalEdge()) {
                    bottom = props.getHorizontalSpace();
                }
            } else {
                bottom = props.getHorizontalSpace();
            }
        } else {
            if (props.getHasHorizontalEdge()) {
                top = props.getHorizontalSpace() * (spanCount - spanIndex) / spanCount;
                bottom = props.getHorizontalSpace() * (spanIndex + (spanSize - 1) + 1) / spanCount;
            } else {
                top = props.getHorizontalSpace() * spanIndex / spanCount;
                bottom = props.getHorizontalSpace() * (spanCount - (spanIndex + spanSize - 1) - 1) /
                         spanCount;
            }

            if (isFirstRowOrColumn) { // First column
                if (props.isHasSetFarFirstEdge()) {
                    left = props.getFarFirstSpace();
                } else if (props.getHasVerticalEdge()) {
                    left = props.getVerticalSpace();
                }
            }
            if (isLastRowOrColumn) { // Last column
                if (props.isHasSetFarLastEdge()) {
                    right = props.getFarLastSpace();
                } else if (props.getHasVerticalEdge()) {
                    right = props.getVerticalSpace();
                }
            } else {
                right = props.getVerticalSpace();
            }
        }

        outRect.set(left,top,right,bottom);
    }

    public static class Builder {
        private SparseArray<ItemDecorationProps> mPropMap; // itemType -> prop
        private ItemDecorationProps mItemDecorationProps;
        private boolean mIgnoreViewType;//是否忽略ViewType


        public Builder() {
            mPropMap = new SparseArray<>();
        }

        public Builder(SparseArray<ItemDecorationProps> propMap) {
            mPropMap = propMap;
        }

        public Builder(int itemType,ItemDecorationProps itemDecorationProps) {
            mPropMap = new SparseArray<>();
            mPropMap.put(itemType,itemDecorationProps);
        }

        public CommonItemDecoration build() {
            if (mIgnoreViewType) {
                return new CommonItemDecoration(mItemDecorationProps);
            }
            return new CommonItemDecoration(mPropMap);
        }

        public Builder addDecoration(
                int itemType,ItemDecorationProps itemDecorationProps
        )
        {
            if (mPropMap == null) {
                mPropMap = new SparseArray<>();
            }
            mPropMap.put(itemType,itemDecorationProps);
            return this;
        }

        public Builder addDecoration(
                ItemDecorationProps itemDecorationProps
        )
        {
            mIgnoreViewType = true;
            mItemDecorationProps = itemDecorationProps;
            return this;
        }
    }
}
