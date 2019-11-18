package com.evil.recycler.holder;

import com.evil.recycler.inface.OnItemChildClickListener;
import com.evil.recycler.inface.OnItemChildLongClickListener;

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

    public static <T> void setRealPosition(RecyclerViewHolder<T> viewHolder, int realPosition)
    {
        viewHolder.setRealPosition(realPosition);
    }

    public static <T> void setData(RecyclerViewHolder<T> viewHolder, T data)
    {
        viewHolder.setData(data);
    }
}
