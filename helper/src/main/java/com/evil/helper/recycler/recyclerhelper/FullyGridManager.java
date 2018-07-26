package com.evil.helper.recycler.recyclerhelper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Override
    GridLayoutManager createManager() {
        return new FullyGridLayoutManager(getContext(),2);
    }
}
