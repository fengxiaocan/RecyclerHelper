package com.evil.helper.recycler.recyclerhelper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 6/6/18
 * @desc ...
 */
public class LinearManager extends IManager<LinearLayoutManager> {

    LinearManager(RecyclerView recyclerView) {
        super(recyclerView);
        mLayoutManager = createManager1(false);
    }

    LinearManager(RecyclerView recyclerView,boolean mathcWidth) {
        super(recyclerView);
        mLayoutManager = createManager1(mathcWidth);
    }

    @Override
    LinearLayoutManager createManager() {
        return null;
    }

    LinearLayoutManager createManager1(boolean mathcWidth) {
        if (mathcWidth) {
            return new LinearLayoutManager(getContext()) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    //解决宽度显示不全的问题
                    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                         ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                }
            };
        } else {
            return new LinearLayoutManager(getContext());
        }
    }

    public LinearManager matchWidth() {
        mLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                //解决宽度显示不全的问题
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                     ViewGroup.LayoutParams.WRAP_CONTENT
                );
            }
        };
        return this;
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
}
