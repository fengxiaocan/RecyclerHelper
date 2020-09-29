package com.evil.recycler.adapter;

import com.evil.recycler.holder.BaseRecyclerHolder;
import com.evil.recycler.holder.SelectedRecyclerHolder;
import com.evil.recycler.inface.IRecyclerSelector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SelectedRecyclerViewAdapter<T extends IRecyclerSelector, V extends SelectedRecyclerHolder<T>> extends RecyclerViewAdapter<T, V> {
    protected boolean isMultiSelected = true;//是否多选
    protected boolean isSelectable = true;//是否可以选择

    public boolean isMultiSelected() {
        return isMultiSelected;
    }

    public void setMultiSelected(boolean multiSelected) {
        isMultiSelected = multiSelected;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public void setSelectable(boolean isSelectable) {
        this.isSelectable = isSelectable;
        notifyDataSetChanged();
    }

    public void selected(int position) {
        if (isMultiSelected) {
            multiSelected(position);
        } else {
            singleSelected(position);
        }
    }

    /**
     * 多选
     *
     * @param position
     */
    public void multiSelected(int position) {
        this.isMultiSelected = true;
        T data = getData(position);
        if (data != null) {
            data.onSelected(!data.isSelected());
            notifyItemChanged(position);
        }
    }

    /**
     * 单个选择
     *
     * @param position
     */
    public void singleSelected(int position) {
        this.isMultiSelected = false;
        for (int i = 0; i < mDatas.size(); i++) {
            T t = mDatas.get(i);
            if (i != position) {
                if (t.isSelected()) {
                    t.onSelected(false);
                    notifyItemChanged(i);
                }
            } else {
                if (!t.isSelected()) {
                    t.onSelected(true);
                    notifyItemChanged(i);
                }
            }
        }
    }

    /**
     * 选择全部
     */
    public void selectedAll() {
        this.isMultiSelected = true;
        for (int i = 0; i < mDatas.size(); i++) {
            T t = mDatas.get(i);
            if (!t.isSelected()) {
                t.onSelected(true);
                notifyItemChanged(i);
            }
        }
    }

    /**
     * 取消全部选择
     */
    public void cancelAllSelected() {
        for (int i = 0; i < mDatas.size(); i++) {
            T t = mDatas.get(i);
            if (t.isSelected()) {
                t.onSelected(false);
                notifyItemChanged(i);
            }
        }
    }

    /**
     * 取消选择
     *
     * @param position
     */
    public void cancelSelected(int position) {
        T data = getData(position);
        if (data != null) {
            data.onSelected(false);
            notifyItemChanged(position);
        }
    }

    public List<T> getSelectedList() {
        List<T> list = new ArrayList<>();
        for (T data : mDatas) {
            if (data.isSelected()) {
                list.add(data);
            }
        }
        return list;
    }

    public Set<Integer> getSelectedIndex() {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < mDatas.size(); i++) {
            T t = mDatas.get(i);
            if (t.isSelected()) {
                set.add(i);
            }
        }
        return set;
    }

    public int getSelectedCount() {
        int count = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            T t = mDatas.get(i);
            if (t.isSelected()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof SelectedRecyclerHolder) {
            ((SelectedRecyclerHolder) holder).onSelectableChanged(isSelectable);
            T data = getData(position);
            ((SelectedRecyclerHolder) holder).onSelectedChanged(data.isSelected());
        }
    }
}
