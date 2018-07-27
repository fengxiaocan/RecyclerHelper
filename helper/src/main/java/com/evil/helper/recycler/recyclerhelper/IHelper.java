package com.evil.helper.recycler.recyclerhelper;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

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


    public IHelper adapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
        return this;
    }

    Context getContext() {return recyclerView.getContext();}

    public abstract void init();
}
