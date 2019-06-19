package com.evil.helper.recycler.recyclerhelper;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerScrollManager {
    private RecyclerView recyclerView;
    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll = false;
    //记录目标项位置
    private int mToPosition;

    public RecyclerScrollManager(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        mShouldScroll = false;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll) {
                    mShouldScroll = false;
                    smoothMoveToPosition(mToPosition);
                }
            }
        });
    }

    /**
     * 滑动到指定位置
     */
    public void smoothMoveToPosition(final int position) {
        // 第一个可见位置
        int firstItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = recyclerView.getChildLayoutPosition(
                recyclerView.getChildAt(recyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            recyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < recyclerView.getChildCount()) {
                int top = recyclerView.getChildAt(movePosition).getTop();
                recyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            recyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }
}
