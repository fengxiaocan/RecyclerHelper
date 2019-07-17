/*
 * Copyright (c) 2018. Dgtle.All Rights Reserved.
 */

package com.evil.recycler.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.HORIZONTAL;
import static androidx.recyclerview.widget.RecyclerView.VERTICAL;


public class RecyclerViewDivider extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private int mOrientation = HORIZONTAL;
    private SparseArray<RecyclerDividerProps> mPropMap; // itemType -> prop
    private RecyclerDividerProps dividerProps;

    public RecyclerViewDivider() {
    }

    public RecyclerViewDivider(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        this.mOrientation = orientation;
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        this.mOrientation = orientation;
    }

    public RecyclerViewDivider(RecyclerDividerProps divider) {
        this.dividerProps = divider;
    }

    public RecyclerViewDivider(int orientation, RecyclerDividerProps divider) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        this.mOrientation = orientation;
        this.dividerProps = divider;
    }

    public RecyclerViewDivider(int orientation, int type, RecyclerDividerProps divider) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        this.mOrientation = orientation;
        mPropMap = new SparseArray<>();
        mPropMap.put(type, divider);
    }


    public RecyclerViewDivider addDecoration(int itemType, RecyclerDividerProps divider)
    {
        if (mPropMap == null) {
            mPropMap = new SparseArray<>();
        }
        mPropMap.put(itemType, divider);
        return this;
    }

    public RecyclerViewDivider setDecoration(RecyclerDividerProps divider)
    {
        dividerProps = divider;
        return this;
    }

    private boolean isTypeDecoration() {
        return mPropMap != null && mPropMap.size() > 0;
    }

    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state)
    {
        //        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = parent.getAdapter();
        int itemType = adapter.getItemViewType(position);

        RecyclerDividerProps props = null;
        if (mPropMap != null) {
            props = mPropMap.get(itemType);
        }
        if (props == null) {
            props = dividerProps;
        }
        if (props == null) {
            return;
        }
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, props.getHeight(), 0);
        } else {
            outRect.set(0, 0, 0, props.getHeight());
        }
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {

        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) child.getLayoutParams();

            int position = parent.getChildAdapterPosition(child);
            RecyclerView.Adapter adapter = parent.getAdapter();
            int itemType = adapter.getItemViewType(position);

            RecyclerDividerProps props = null;
            if (mPropMap != null) {
                props = mPropMap.get(itemType);
            }
            if (props == null) {
                props = dividerProps;
            }
            if (props == null) {
                return;
            }
            final int left = parent.getPaddingLeft() + props.getMarginLeft();
            final int right =
                    parent.getMeasuredWidth() - parent.getPaddingRight() - props.getMarginRight();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + props.getHeight();
            if (props.getDrawable() != null) {
                props.getDrawable()
                     .setBounds(left, top, right, bottom);
                props.getDrawable()
                     .draw(canvas);
            }
            if (props.getPaint() != null) {
                canvas.drawRect(left, top, right, bottom, props.getPaint());
            }
        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) child.getLayoutParams();

            int position = parent.getChildAdapterPosition(child);
            RecyclerView.Adapter adapter = parent.getAdapter();
            int itemType = adapter.getItemViewType(position);

            RecyclerDividerProps props = null;
            if (mPropMap != null) {
                props = mPropMap.get(itemType);
            }
            if (props == null) {
                props = dividerProps;
            }
            if (props == null) {
                return;
            }

            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + props.getHeight();

            final int top = parent.getPaddingTop() + props.getMarginTop();
            final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom() -
                               props.getMarginBottom();

            if (props.getDrawable() != null) {
                props.getDrawable()
                     .setBounds(left, top, right, bottom);
                props.getDrawable()
                     .draw(canvas);
            }
            if (props.getPaint() != null) {
                canvas.drawRect(left, top, right, bottom, props.getPaint());
            }
        }
    }
}
