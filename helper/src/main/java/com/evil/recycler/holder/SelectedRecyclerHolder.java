package com.evil.recycler.holder;

import android.view.View;

import com.evil.recycler.adapter.SelectedRecyclerViewAdapter;
import com.evil.recycler.inface.IRecyclerSelector;

import java.util.List;

public abstract class SelectedRecyclerHolder<T extends IRecyclerSelector> extends RecyclerViewHolder<T> {

    public SelectedRecyclerHolder(View itemView) {
        super(itemView);
    }

    public final void onSelected(boolean selected) {
        if (isMultiSelected()) {
            data.onSelected(selected);
            onSelectedChanged(selected);
        }else{
            SelectedRecyclerViewAdapter selectedAdapter = getSelectedAdapter();
            List<T> list = selectedAdapter.getDatas();
            int dataPosition = getDataPosition();
            for (int i = 0; i < list.size(); i++) {
                if (i != dataPosition){
                    T t = list.get(i);
                    if (t.isSelected()) {
                        t.onSelected(false);
                        selectedAdapter.notifyItemChanged(i);
                    }
                }else{
                    data.onSelected(selected);
                    onSelectedChanged(selected);
                }
            }
        }
    }

    public final SelectedRecyclerViewAdapter getSelectedAdapter() {
        return (SelectedRecyclerViewAdapter) getAdapter();
    }

    public final boolean isMultiSelected() {
        return getSelectedAdapter().isMultiSelected();
    }

    public final boolean isSelectable() {
        return getSelectedAdapter().isSelectable();
    }

    /**
     * 选择改变状态
     *
     * @param selected
     */
    public abstract void onSelectedChanged(boolean selected);

    /**
     * 是否可以选择
     *
     * @param selectable
     */
    public abstract void onSelectableChanged(boolean selectable);
}
