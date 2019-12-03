package com.evil.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.evil.recycler.holder.BaseRecyclerHolder;
import com.evil.recycler.holder.RecyclerViewHolder;
import com.evil.recycler.holder.SwipeRecyclerViewHolder;
import com.evil.recycler.holder.ViewHolderHepler;
import com.evil.recycler.inface.IRecyclerData;
import com.evil.recycler.inface.OnMenuItemClickListener;
import com.evil.recycler.menu.IMenuDragView;
import com.evil.recycler.menu.MenuDragLayout;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/6/18
 * @desc ...
 */
public abstract class SwipeRecyclerViewAdapter<T extends IRecyclerData, V extends SwipeRecyclerViewHolder<T>>
        extends ComRecyclerViewAdapter<T, RecyclerViewHolder<T>> {
    protected OnMenuItemClickListener mOnMenuItemClickListener;

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        mOnMenuItemClickListener = onMenuItemClickListener;
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (isContainer(viewType)) {
            return getExtensionHolder();
        } else if (isFooter(viewType)) {
            return getFooterHolder();
        } else if (isHeader(viewType)) {
            return getHeaderHolder();
        } else {
            LayoutInflater from = LayoutInflater.from(parent.getContext());
            View view;
            if (attachParent()) {
                view = from.inflate(onCreateLayoutRes(viewType), parent, false);
            } else {
                view = from.inflate(onCreateLayoutRes(viewType), null);
            }
            int menuLayout = onCreateRightMenuLayout(viewType);
            if (menuLayout != 0) {
                //有侧滑菜单
                IMenuDragView dragLayout = createMenuDragLayou(parent);
                if (!(dragLayout instanceof ViewGroup)) {
                    throw new ClassCastException(
                            "SwipeRecyclerViewAdapter --> IMenuDragView must extends ViewGroup!!!");
                }
                dragLayout.addView(view);
                dragLayout.setContentView(view);
                View dragView;
                if (attachParent()) {
                    dragView = from.inflate(menuLayout, parent, false);
                } else {
                    dragView = from.inflate(menuLayout, null);
                }
                dragLayout.addView(dragView);
                dragLayout.setDragView(dragView);
                SwipeRecyclerViewHolder holder = onCreateWithMenuHolder((View) dragLayout,
                        viewType);
                holder.setMenuView(dragView);
                holder.setContentView(view);
                holder.initMenu(dragView);
                holder.selfAdapter = this;
                return holder;
            }
            RecyclerViewHolder<T> viewHolder = createViewHolder(view, viewType);
            viewHolder.selfAdapter = this;
            return viewHolder;
        }
    }

    protected IMenuDragView createMenuDragLayou(ViewGroup parent) {
        return new MenuDragLayout(parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseRecyclerHolder holder, int position) {
        if (holder instanceof SwipeRecyclerViewHolder) {
            SwipeRecyclerViewHolder viewHolder = (SwipeRecyclerViewHolder) holder;
            ViewHolderHepler.setOnMenuItemClickListener(viewHolder,mOnMenuItemClickListener);
        }
        super.onBindViewHolder(holder, position);
    }

    /**
     * 创建一个带侧滑菜单的ViewHolder
     *
     * @param layout
     * @param viewType
     * @return
     */
    public abstract SwipeRecyclerViewHolder<T> onCreateWithMenuHolder(View layout, int viewType);

    public abstract int onCreateRightMenuLayout(int viewType);
}
