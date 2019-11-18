package com.evil.recycler.helper;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 6/6/18
 * @desc 在scrollview中其那套recyclerview
 */
public class FullyLinearManager extends IManager<FullyLinearLayoutManager> {

    FullyLinearManager(RecyclerView recyclerView) {
        super(recyclerView);
    }

    FullyLinearManager(RecyclerView recyclerView, boolean mathcWidth) {
        this(recyclerView, getManager(recyclerView.getContext(), mathcWidth));
    }

    FullyLinearManager(RecyclerView recyclerView, FullyLinearLayoutManager manager) {
        super(recyclerView, manager);
    }

    static FullyLinearLayoutManager getManager(Context context, boolean mathcWidth) {
        if (mathcWidth) {
            return new FullyLinearLayoutManager(context) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    //解决宽度显示不全的问题
                    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            };
        } else {
            return new FullyLinearLayoutManager(context);
        }
    }

    @Override
    FullyLinearLayoutManager createManager() {
        return new FullyLinearLayoutManager(getContext());
    }

    public FullyLinearManager matchWidth() {
        mLayoutManager = new FullyLinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                //解决宽度显示不全的问题
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
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
