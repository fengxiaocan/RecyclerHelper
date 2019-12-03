package com.evil.recycler.holder;

import com.evil.recycler.inface.IRecyclerData;
import com.evil.recycler.inface.OnAdapterItemClickListener;
import com.evil.recycler.inface.OnItemChildClickListener;
import com.evil.recycler.inface.OnItemChildLongClickListener;
import com.evil.recycler.inface.OnMenuItemClickListener;

public class ViewHolderHepler {
    public static <T> void setOnItemChildClickListener(RecyclerViewHolder<T> viewHolder,
            OnItemChildClickListener<T> onItemChildClickListener)
    {
        viewHolder.setOnItemChildClickListener(onItemChildClickListener);
    }

    public static <T> void setOnItemChildLongClickListener(RecyclerViewHolder<T> viewHolder,
            OnItemChildLongClickListener<T> tOnItemChildLongClickListener)
    {
        viewHolder.setOnItemChildLongClickListener(tOnItemChildLongClickListener);
    }

    public static <T extends IRecyclerData> void setOnMenuItemClickListener(
            SwipeRecyclerViewHolder<T> viewHolder, OnMenuItemClickListener onMenuItemClickListener)
    {
        viewHolder.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    public static <T> void setOnItemClickListener(RecyclerViewHolder<T> viewHolder,
            OnAdapterItemClickListener<T> itemClickListener)
    {
        viewHolder.setOnItemClickListener(itemClickListener);
    }

    public static <T> void setData(RecyclerViewHolder<T> viewHolder, T data)
    {
        viewHolder.setData(data);
    }
}
