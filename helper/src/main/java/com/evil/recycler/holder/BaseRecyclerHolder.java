package com.evil.recycler.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.evil.recycler.adapter.IExtendAdapter;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 12/6/18
 * @desc ...
 */
public abstract class BaseRecyclerHolder extends RecyclerView.ViewHolder {
    public IExtendAdapter selfAdapter;
    protected boolean isStaggeredGridFullSpan = false;//在流式布局的情况下是否需要占满全屏

    public void removeAndNotifySelf() {
        selfAdapter.removeAndNotify(getLayoutPosition());
    }

    public RecyclerView.Adapter getAdapter() {
        return ((RecyclerView.Adapter) selfAdapter);
    }

    public IExtendAdapter getSelfAdapter() {
        return selfAdapter;
    }

    public BaseRecyclerHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    public static void removeParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view);
            }
        }
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

    public void onBindData(RecyclerView.Adapter adapter, int position) {
        //绑定数据
    }

    public final <T extends View> T findViewById(@IdRes int id) {
        if (id == View.NO_ID) {
            return null;
        }
        return itemView.findViewById(id);
    }

    public void onViewRecycled() {
    }

    public void onViewAttachedToWindow() {
        /**
         * 针对流式布局
         */
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        if (layoutParams != null) {
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                if (isStaggeredGridFullSpan) {
                    StaggeredGridLayoutManager.LayoutParams params =
                            (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                    //占领全部空间;
                    params.setFullSpan(true);
                }
            }
        }
    }

    public void onViewDetachedFromWindow() {
    }
}
