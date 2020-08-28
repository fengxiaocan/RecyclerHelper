package com.evil.recycler.holder;

import android.view.View;

import androidx.annotation.IdRes;

import com.evil.recycler.adapter.ComRecyclerViewAdapter;
import com.evil.recycler.inface.OnAdapterItemClickListener;
import com.evil.recycler.inface.OnItemChildClickListener;
import com.evil.recycler.inface.OnItemChildLongClickListener;

import java.util.List;

public abstract class RecyclerViewHolder<D> extends BaseRecyclerHolder
        implements View.OnClickListener, View.OnLongClickListener {
    D data;
    OnItemChildClickListener<D> mOnItemChildClickListener;
    OnItemChildLongClickListener<D> mOnItemChildLongClickListener;
    OnAdapterItemClickListener<D> mOnItemClickListener;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    void setOnItemChildClickListener(OnItemChildClickListener<D> onItemChildClickListener) {
        this.mOnItemChildClickListener = onItemChildClickListener;
    }

    void setOnItemChildLongClickListener(
            OnItemChildLongClickListener<D> mOnItemChildLongClickListener) {
        this.mOnItemChildLongClickListener = mOnItemChildLongClickListener;
    }

    void setOnItemClickListener(OnAdapterItemClickListener<D> listener) {
        this.mOnItemClickListener = listener;
        if (mOnItemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, RecyclerViewHolder.this, getDataPosition());
                    }
                }
            });
        }
    }

    void setData(D data) {
        this.data = data;
    }

    public List<D> getAdapterDatas() {
        return selfAdapter.getDatas();
    }

    public abstract void onBindData(D t);

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
            mOnItemChildClickListener.onItemChildClick(v, data, getDataPosition());
        }
    }

    public int getDataPosition() {
        if (selfAdapter instanceof ComRecyclerViewAdapter) {
            return getLayoutPosition() - ((ComRecyclerViewAdapter) selfAdapter).getHeaderCount();
        } else {
            return getLayoutPosition();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemChildLongClickListener != null) {
            return mOnItemChildLongClickListener.onItemChildLongClick(v, data, getDataPosition());
        }
        return false;
    }

    /**
     * 移除自身
     */
    @Override
    public void removeAndNotifySelf() {
        if (selfAdapter instanceof ComRecyclerViewAdapter) {
            ComRecyclerViewAdapter selfAdapter1 = (ComRecyclerViewAdapter) this.selfAdapter;
            selfAdapter1.removeAndNotify(getDataPosition());
        } else {
            selfAdapter.removeAndNotify(getLayoutPosition());
        }
    }


    public final <T extends View> T findViewById(@IdRes int id) {
        return itemView.findViewById(id);
    }
}
