package com.evil.recycler.inface;

import android.view.View;

import java.util.List;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 12/6/18
 * @desc ...
 */
public interface OnItemChildClickListener<T> {

    /**
     * On item click.
     *
     * @param view the view
     */
    void onItemChildClick(View view, T data, int position);
}
