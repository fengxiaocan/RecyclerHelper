package com.evil.helper.recycler.inface;

import android.view.View;

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
     * @param list the list
     * @param position the position
     */
    void onItemClick(View view,List<T> list,int position);
}
