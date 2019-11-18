package com.evil.recycler.holder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.inface.OnItemChildClickListener;
import com.evil.recycler.inface.OnItemChildLongClickListener;

public abstract class RecyclerViewHolder<T> extends BaseRecyclerHolder
        implements View.OnClickListener, View.OnLongClickListener {
    private T data;
    private int realPosition;
    private OnItemChildClickListener<T> mOnItemChildClickListener;
    private OnItemChildLongClickListener<T> mOnItemChildLongClickListener;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    void setRealPosition(int realPosition) {
        this.realPosition = realPosition;
    }

    void setData(T data) {
        this.data = data;
    }

    void setOnItemChildClickListener(OnItemChildClickListener<T> onItemChildClickListener)
    {
        this.mOnItemChildClickListener = onItemChildClickListener;
    }

    void setOnItemChildLongClickListener(
            OnItemChildLongClickListener<T> mOnItemChildLongClickListener)
    {
        this.mOnItemChildLongClickListener = mOnItemChildLongClickListener;
    }

    public abstract void setData(RecyclerView.Adapter adapter, T t, int position);

    public void addOnClickListener(View view) {
        if (view != null) {
            view.setOnClickListener(this);
        }
    }

    public void addOnLongClickListener(View view) {
        if (view != null) {
            view.setOnLongClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemChildClickListener != null) {
            mOnItemChildClickListener.onItemChildClick(v, data, realPosition);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemChildLongClickListener != null) {
            return mOnItemChildLongClickListener.onItemChildLongClick(v, data, realPosition);
        }
        return false;
    }
}
