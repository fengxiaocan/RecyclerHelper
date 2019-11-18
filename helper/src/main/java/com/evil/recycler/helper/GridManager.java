package com.evil.recycler.helper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 6/6/18
 * @desc ...
 */
public class GridManager extends IManager<GridLayoutManager> {
    GridManager(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    GridLayoutManager createManager() {
        return new GridLayoutManager(getContext(),2);
    }

    GridManager(RecyclerView recyclerView, GridLayoutManager manager) {
        super(recyclerView, manager);
    }

    @Override
    public IManager orientation(boolean isHorizontal) {
        if (isHorizontal) {
            mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        } else {
            mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        }
        return this;
    }

    @Override
    public IManager reverseLayout(boolean reverseLayout) {
        mLayoutManager.setReverseLayout(reverseLayout);
        return this;
    }

    public GridManager spanCount(int spanCount) {
        mLayoutManager.setSpanCount(spanCount);
        return this;
    }
}
