package com.evil.recycler.helper;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 6/6/18
 * @desc 在scrollview中其那套recyclerview
 */
public class FullyStaggeredGridManager extends StaggeredGridManager {

    FullyStaggeredGridManager(RecyclerView recyclerView) {
        super(recyclerView);
    }

    public FullyStaggeredGridManager(RecyclerView recyclerView, StaggeredGridLayoutManager manager)
    {
        super(recyclerView, manager);
    }

    @Override
    StaggeredGridLayoutManager createManager() {
        return new FullyStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return super.generateDefaultLayoutParams();
            }
        };
    }
}
