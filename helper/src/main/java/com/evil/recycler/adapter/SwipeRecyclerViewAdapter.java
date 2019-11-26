package com.evil.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.evil.recycler.holder.BaseRecyclerHolder;
import com.evil.recycler.holder.RecyclerViewHolder;
import com.evil.recycler.holder.SwipeRecyclerViewHolder;
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
                return holder;
            }
            return createViewHolder(view, viewType);
        }
    }

    protected IMenuDragView createMenuDragLayou(ViewGroup parent) {
        return new MenuDragLayout(parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof SwipeRecyclerViewHolder) {
            SwipeRecyclerViewHolder viewHolder = (SwipeRecyclerViewHolder) holder;
            final int realPosition = position - getHeaderCount();
            if (mOnItemClickListener != null) {
                viewHolder.getContentView().setOnClickListener(new OnItemClick(realPosition));
            }
            if (mOnMenuItemClickListener != null) {
                if (viewHolder.getMenuView() instanceof ViewGroup) {
                    for (int i = 0; i < ((ViewGroup) viewHolder.getMenuView()).getChildCount(); i++) {
                        View child = ((ViewGroup) viewHolder.getMenuView()).getChildAt(i);
                        final int childPosition = i;
                        if (child != null) {
                            child.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mOnMenuItemClickListener.onMenuItemClick(v, realPosition, childPosition);
                                }
                            });
                        }
                    }
                } else {
                    viewHolder.getMenuView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnMenuItemClickListener.onMenuItemClick(v, realPosition, 0);
                        }
                    });
                }
            }
        }
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
