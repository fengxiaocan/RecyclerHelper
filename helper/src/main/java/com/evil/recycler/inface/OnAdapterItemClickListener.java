package com.evil.recycler.inface;

import android.view.View;

import com.evil.recycler.holder.BaseRecyclerHolder;
import com.evil.recycler.holder.RecyclerViewHolder;

import java.util.List;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 12/6/18
 * @desc ...
 */
public interface OnAdapterItemClickListener<T> {
    /**
     * On item click.
     *
     * @param view the view
     * @param position the position
     */
    void onItemClick(View view, RecyclerViewHolder<T> baseRecyclerHolder, int position);
}
