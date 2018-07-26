package com.evil.helper.recycler.adapter;

import com.evil.helper.recycler.holder.MultipleRecyclerViewHolder;
import com.evil.helper.recycler.inface.IRecycleData;

/**
 * The type Recycler view adapter.
 *
 * @param <T> the type parameter
 */
public abstract class MultipleRecyclerSimpleAdapter<T extends IRecycleData>
        extends MultipleRecyclerViewAdapter<T,MultipleRecyclerViewHolder<T>>
{
    @Override
    public boolean attachParent() {
        return false;
    }
}
