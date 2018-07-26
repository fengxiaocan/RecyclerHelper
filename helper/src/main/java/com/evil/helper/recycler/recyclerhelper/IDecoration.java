package com.evil.helper.recycler.recyclerhelper;

import android.support.v7.widget.RecyclerView;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 6/6/18
 * @desc ...
 */
public abstract class IDecoration<T extends RecyclerView.ItemDecoration> extends IHelper {

    T itemDecoration;

    IDecoration(RecyclerView recyclerView) {
        super(recyclerView);
        itemDecoration = createDecoration();
    }

    abstract T createDecoration();

    public T getItemDecoration() {
        return itemDecoration;
    }

    @Override
    public void init() {
        recyclerView.addItemDecoration(itemDecoration);
    }

}
