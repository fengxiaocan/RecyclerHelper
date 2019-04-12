package com.evil.helper.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evil.helper.recycler.holder.BaseRecyclerHolder;
import com.evil.helper.recycler.holder.RecyclerViewHolder;
import com.evil.helper.recycler.holder.SwipeRecyclerViewHolder;
import com.evil.helper.recycler.inface.IRecycleData;
import com.evil.helper.recycler.inface.OnMenuItemClickListener;
import com.evil.helper.recycler.menu.IMenuDragView;
import com.evil.helper.recycler.menu.MenuDragLayout;

import androidx.annotation.NonNull;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/6/18
 * @desc ...
 */
public abstract class SwipeRecyclerViewAdapter<T extends IRecycleData, V extends SwipeRecyclerViewHolder<T>>
        extends ComRecyclerViewAdapter<T, RecyclerViewHolder<T>> {
    protected OnMenuItemClickListener mOnMenuItemClickListener;

    public OnMenuItemClickListener getOnMenuItemClickListener() {
        return mOnMenuItemClickListener;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        mOnMenuItemClickListener = onMenuItemClickListener;
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (isEmptyView(viewType)) {
            return emptyRecyclerView.getHolder();
        } else if (isLoadingView(viewType)) {
            return loadingRecyclerView.getHolder();
        } else if (isErrorView(viewType)) {
            return errorRecyclerView.getHolder();
        } else if (isFooter(viewType)) {
            return getFooterHolder(viewType);
        } else if (isHeader(viewType)) {
            return getHeaderHolder(viewType);
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
