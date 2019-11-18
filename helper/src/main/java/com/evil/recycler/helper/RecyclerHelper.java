package com.evil.recycler.helper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class RecyclerHelper {
    private RecyclerView mRecyclerView;

    private RecyclerHelper(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public static RecyclerHelper with(RecyclerView recyclerView) {
        return new RecyclerHelper(recyclerView);
    }

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return false;
        }
        return (recyclerView.computeVerticalScrollExtent() +
                recyclerView.computeVerticalScrollOffset() >=
                recyclerView.computeVerticalScrollRange());
    }

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

    public GridManager fullyGridManager(FullyGridLayoutManager manager) {
        return new FullyGridManager(mRecyclerView, manager);
    }

    public LinearManager linearManager() {
        return new LinearManager(mRecyclerView);
    }

    public FullyLinearManager fullyLinearManager() {
        return new FullyLinearManager(mRecyclerView);
    }

    public LinearManager linearManager(boolean matchWidth) {
        return new LinearManager(mRecyclerView, matchWidth);
    }

    public LinearManager linearManager(LinearLayoutManager manager) {
        return new LinearManager(mRecyclerView, manager);
    }

    public FullyLinearManager fullyLinearManager(boolean matchWidth) {
        return new FullyLinearManager(mRecyclerView, matchWidth);
    }

    public FullyLinearManager fullyLinearManager(FullyLinearLayoutManager manager) {
        return new FullyLinearManager(mRecyclerView, manager);
    }

    public StaggeredGridManager staggeredManager() {
        return new StaggeredGridManager(mRecyclerView);
    }

    public StaggeredGridManager staggeredManager(StaggeredGridLayoutManager manager) {
        return new StaggeredGridManager(mRecyclerView, manager);
    }

    public StaggeredGridManager fullyStaggeredManager() {
        return new FullyStaggeredGridManager(mRecyclerView);
    }

    public StaggeredGridManager fullyStaggeredManager(FullyStaggeredGridLayoutManager manager) {
        return new FullyStaggeredGridManager(mRecyclerView, manager);
    }
}
