package com.evil.helper.recycler.recyclerhelper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

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

    @Override
    StaggeredGridLayoutManager createManager() {
        return new FullyStaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return super.generateDefaultLayoutParams();
            }
        };
    }
}
