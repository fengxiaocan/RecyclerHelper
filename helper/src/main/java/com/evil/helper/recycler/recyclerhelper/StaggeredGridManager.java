package com.evil.helper.recycler.recyclerhelper;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 6/6/18
 * @desc ...
 */
public class StaggeredGridManager extends IManager<StaggeredGridLayoutManager> {

    StaggeredGridManager(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    StaggeredGridLayoutManager createManager() {
        return new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
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

    public StaggeredGridManager spanCount(int spanCount) {
        mLayoutManager.setSpanCount(spanCount);
        return this;
    }
    public StaggeredGridManager gapStrategy(int gapStrategy) {
        //GAP_HANDLING_NONE
        //GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        mLayoutManager.setGapStrategy(gapStrategy);
        return this;
    }
}
