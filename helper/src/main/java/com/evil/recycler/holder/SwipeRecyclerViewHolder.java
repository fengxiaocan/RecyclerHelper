package com.evil.recycler.holder;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.adapter.SwipeRecyclerViewAdapter;
import com.evil.recycler.inface.IRecyclerData;
import com.evil.recycler.inface.OnMenuItemClickListener;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/6/18
 * @desc ...
 */
public abstract class SwipeRecyclerViewHolder<T extends IRecyclerData>
        extends RecyclerViewHolder<T> {
    protected View contentView;
    protected View menuView;

    public SwipeRecyclerViewHolder(View layout) {
        super(layout);
    }

    public abstract void initMenu(View menuLayout);

    public View getContentView() {
        return contentView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public View getMenuView() {
        return menuView;
    }

    public void setMenuView(View menuView) {
        this.menuView = menuView;
    }

    @Override
    public View getItemView() {
        return contentView;
    }

    @Override
    public void onBindData(RecyclerView.Adapter adapter, final int position) {
    }
}
