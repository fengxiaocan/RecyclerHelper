package com.evil.recycler.helper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 6/6/18
 * @desc 在scrollview中其那套recyclerview
 */
public class FullyGridManager extends GridManager {
    FullyGridManager(RecyclerView recyclerView) {
        super(recyclerView);
    }

    public FullyGridManager(RecyclerView recyclerView, GridLayoutManager manager) {
        super(recyclerView, manager);
    }

    @Override
    GridLayoutManager createManager() {
        return new FullyGridLayoutManager(getContext(),2);
    }
}
