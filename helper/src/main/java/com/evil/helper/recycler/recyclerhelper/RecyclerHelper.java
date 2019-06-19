package com.evil.helper.recycler.recyclerhelper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/***
 * fengxiaocan@gmail.com
 * 一个Recycler的初始化类
 */
public class RecyclerHelper {
    private RecyclerView mRecyclerView;

    private RecyclerHelper(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public static RecyclerHelper with(RecyclerView recyclerView) {
        return new RecyclerHelper(recyclerView);
    }

    /**
     * 是否滑动到底部
     *
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return false;
        }
        return (recyclerView.computeVerticalScrollExtent() +
                recyclerView.computeVerticalScrollOffset() >=
                recyclerView.computeVerticalScrollRange());
    }

    /**
     * 滚动到指定位置并且在屏幕顶部
     * @param recyclerView
     * @param position
     */
    public static void scrollToPosition(RecyclerView recyclerView, int position) {
        if (position > -1) {
            recyclerView.scrollToPosition(position);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(position, 0);
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                ((StaggeredGridLayoutManager) layoutManager).scrollToPositionWithOffset(position,
                        0);
            }
        }
    }

    public GridManager gridManager() {
        return new GridManager(mRecyclerView);
    }

    public GridManager fullyGridManager() {
        return new FullyGridManager(mRecyclerView);
    }

    public LinearManager linearManager() {
        return new LinearManager(mRecyclerView);
    }

    public LinearManager linearManager(boolean matchWidth) {
        return new LinearManager(mRecyclerView, matchWidth);
    }

    public LinearManager fullyLinearManager(boolean matchWidth) {
        return new FullyLinearManager(mRecyclerView, matchWidth);
    }

    public StaggeredGridManager staggeredManager() {
        return new StaggeredGridManager(mRecyclerView);
    }

    public StaggeredGridManager fullyStaggeredManager() {
        return new FullyStaggeredGridManager(mRecyclerView);
    }
}
