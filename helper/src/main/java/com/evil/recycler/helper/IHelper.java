package com.evil.recycler.helper;

import android.content.Context;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.evil.recycler.drag.DragOrSwipeCallback;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 7/6/18
 * @desc ...
 */
public abstract class IHelper {
    RecyclerView recyclerView;

    IHelper(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public IHelper animation() {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return this;
    }

    public IHelper scrollListener(RecyclerView.OnScrollListener l) {
        recyclerView.addOnScrollListener(l);
        return this;
    }

    public IHelper nestedScroll(boolean enabled) {
        recyclerView.setNestedScrollingEnabled(enabled);
        return this;
    }

    public IHelper snapLinear() {
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        return this;
    }

    public IHelper snapPager() {
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        return this;
    }

    public IHelper recyclerListener(RecyclerView.RecyclerListener recyclerListener) {
        recyclerView.setRecyclerListener(recyclerListener);
        return this;
    }

    public IHelper snap(SnapHelper snapHelper) {
        snapHelper.attachToRecyclerView(recyclerView);
        return this;
    }

    public IHelper itemTouch(ItemTouchHelper.Callback callback) {
        ItemTouchHelper mItemHelper = new ItemTouchHelper(callback);
        mItemHelper.attachToRecyclerView(recyclerView);
        return this;
    }

    public IHelper dragOrSwipe(boolean canDrag, boolean canSwipe) {
        return itemTouch(new DragOrSwipeCallback(canDrag, canSwipe));
    }

    public IHelper dragAndSwipe() {
        return dragOrSwipe(true,true);
    }

    public IHelper dragAndSwipe(DragOrSwipeCallback.SelectAnimation animation) {
        return itemTouch(new DragOrSwipeCallback(true, true,animation));
    }

    public IHelper drag() {
        return itemTouch(new DragOrSwipeCallback(true, false));
    }

    public IHelper drag(DragOrSwipeCallback.SelectAnimation animation) {
        return itemTouch(new DragOrSwipeCallback(true, false,animation));
    }

    public IHelper swipe() {
        return itemTouch(new DragOrSwipeCallback(false, true,null));
    }

    public IHelper adapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
        return this;
    }

    Context getContext() {
        return recyclerView.getContext();
    }

    public abstract void init();
}
