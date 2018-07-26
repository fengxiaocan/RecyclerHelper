package com.evil.helper.recycler.recyclerhelper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 6/6/18
 * @desc 在scrollview中其那套recyclerview
 */
public class FullyLinearManager extends LinearManager{

    FullyLinearManager(RecyclerView recyclerView) {
        super(recyclerView);
        mLayoutManager = createManager1(false);
    }

    FullyLinearManager(RecyclerView recyclerView,boolean mathcWidth) {
        super(recyclerView);
        mLayoutManager = createManager1(mathcWidth);
    }

    @Override
    LinearLayoutManager createManager1(boolean mathcWidth) {
        if (mathcWidth) {
            return new FullyLinearLayoutManager(getContext()) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    //解决宽度显示不全的问题
                    return new RecyclerView.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                }
            };
        } else {
            return new FullyLinearLayoutManager(getContext());
        }
    }
}
