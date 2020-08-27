package com.evil.recycler.holder;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.inface.IRecyclerData;
import com.evil.recycler.inface.OnAdapterItemClickListener;
import com.evil.recycler.inface.OnMenuItemClickListener;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/6/18
 * @desc ...
 */
public abstract class SwipeRecyclerViewHolder<D extends IRecyclerData>
        extends RecyclerViewHolder<D> {
    private OnMenuItemClickListener mOnMenuItemClickListener;
    protected View contentView;
    protected View menuView;

    void setOnMenuItemClickListener(OnMenuItemClickListener listener)
    {
        this.mOnMenuItemClickListener = listener;

        if (mOnMenuItemClickListener != null) {
            if (menuView instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) this.menuView;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View child = viewGroup.getChildAt(i);
                    child.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnMenuItemClickListener != null) {
                                mOnMenuItemClickListener.onMenuItemClick(v, getDataPosition(),
                                        ((ViewGroup) menuView).indexOfChild(v));
                            }
                        }
                    });
                }
            } else {
                menuView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnMenuItemClickListener != null) {
                            mOnMenuItemClickListener.onMenuItemClick(v, getDataPosition(), 0);
                        }
                    }
                });
            }
        }

    }

    void setOnItemClickListener(OnAdapterItemClickListener<D> listener)
    {
        this.mOnItemClickListener = listener;
        if (mOnItemClickListener != null) {
            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, SwipeRecyclerViewHolder.this,
                                getDataPosition());
                    }
                }
            });
        } else {
            contentView.setOnClickListener(null);
        }
    }

    public SwipeRecyclerViewHolder(View layout) {
        super(layout);
    }

    public abstract void initMenu(View menuLayout);

    public View getContentView() {
        return contentView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public View getMenuView() {
        return menuView;
    }

    public void setMenuView(View menuView) {
        this.menuView = menuView;
    }

    @Override
    public View getItemView() {
        return contentView;
    }

    @Override
    public void onBindData(RecyclerView.Adapter adapter, final int position) {
    }
}
