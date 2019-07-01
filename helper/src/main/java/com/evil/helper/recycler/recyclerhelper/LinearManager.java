package com.evil.helper.recycler.recyclerhelper;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 6/6/18
 * @desc ...
 */
public class LinearManager extends IManager<LinearLayoutManager> {

    LinearManager(RecyclerView recyclerView) {
        super(recyclerView);
    }

    LinearManager(RecyclerView recyclerView, boolean mathcWidth) {
        this(recyclerView, getManager(recyclerView.getContext(), mathcWidth));
    }

    LinearManager(RecyclerView recyclerView, LinearLayoutManager manager) {
        super(recyclerView, manager);
    }

    private static LinearLayoutManager getManager(Context context, boolean mathcWidth) {
        if (mathcWidth) {
            return new LinearLayoutManager(context) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    //解决宽度显示不全的问题
                    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            };
        } else {
            return new LinearLayoutManager(context);
        }
    }

    @Override
    LinearLayoutManager createManager() {
        return new LinearLayoutManager(getContext());
    }

    public LinearManager matchWidth() {
        mLayoutManager = new LinearLayoutManager(getContext()) {
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
