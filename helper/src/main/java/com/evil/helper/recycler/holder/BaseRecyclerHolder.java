package com.evil.helper.recycler.holder;

import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 12/6/18
 * @desc ...
 */
public abstract class BaseRecyclerHolder extends RecyclerView.ViewHolder {
    protected boolean isStaggeredGridFullSpan = false;//在流式布局的情况下是否需要占满全屏

    public BaseRecyclerHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    public boolean isStaggeredGridFullSpan() {
        return isStaggeredGridFullSpan;
    }

    public void isStaggeredGridFullSpan(boolean isFull) {
        isStaggeredGridFullSpan = isFull;
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public abstract void initView(View rootView);

    public View getItemView() {
        return itemView;
    }

    public void onBindData(RecyclerView.Adapter adapter, int position) {}

    public final <T extends View> T findViewById(@IdRes int id) {
        if (id == View.NO_ID) {
            return null;
        }
        return itemView.findViewById(id);
    }

}
