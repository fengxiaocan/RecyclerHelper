package com.evil.helper.recycler.recyclerhelper;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;


/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 6/6/18
 * @desc ...
 */
public class DividerDecoration extends IDecoration<DividerItemDecoration> {

    DividerDecoration(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    DividerItemDecoration createDecoration() {
        return new DividerItemDecoration(getContext(),LinearLayout.VERTICAL);
    }

    public DividerDecoration orientation(boolean isHorizontal) {
        itemDecoration.setOrientation(isHorizontal
                                              ? LinearLayout.HORIZONTAL
                                              : LinearLayout.VERTICAL);
        return this;
    }

    public DividerDecoration drawable(Drawable drawable) {
        itemDecoration.setDrawable(drawable);
        return this;
    }

    public DividerDecoration drawable(@DrawableRes int drawable) {
        itemDecoration.setDrawable(getContext().getResources().getDrawable(drawable));
        return this;
    }
}
