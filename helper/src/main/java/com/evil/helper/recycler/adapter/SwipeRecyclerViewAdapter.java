package com.evil.helper.recycler.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evil.helper.recycler.holder.BaseRecyclerHolder;
import com.evil.helper.recycler.holder.RecyclerViewHolder;
import com.evil.helper.recycler.holder.SwipeRecyclerViewHolder;
import com.evil.helper.recycler.inface.IRecycleData;
import com.evil.helper.recycler.inface.OnMenuItemClickListener;
import com.evil.helper.recycler.menu.MenuDragLayout;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/6/18
 * @desc ...
 */
public abstract class SwipeRecyclerViewAdapter<T extends IRecycleData,V extends SwipeRecyclerViewHolder<T>>
        extends ComRecyclerViewAdapter<T,V>
{
    protected OnMenuItemClickListener mOnMenuItemClickListener;

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        mOnMenuItemClickListener = onMenuItemClickListener;
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(
            @NonNull ViewGroup parent,int viewType
    )
    {
        if (isFooter(viewType)) {
            //暂时不支持footer及Header添加侧滑删除功能
            return getFooterHolder(viewType);
        } else if (isHeader(viewType)) {
            //暂时不支持footer及Header添加侧滑删除功能
            return getHeaderHolder(viewType);
        } else {
            LayoutInflater from = LayoutInflater.from(parent.getContext());
            View view;
            if (attachParent()) {
                view = from.inflate(onCreateLayoutRes(viewType),parent,false);
            } else {
                view = from.inflate(onCreateLayoutRes(viewType),null);
            }
            int menuLayout = onCreateRightMenuLayout(viewType);
            if (menuLayout != 0) {
                //有侧滑菜单
                MenuDragLayout dragLayout = new MenuDragLayout(parent.getContext());
                dragLayout.addView(view);
                dragLayout.setContentView(view);
                View dragView;
                if (attachParent()) {
                    dragView = from.inflate(menuLayout,dragLayout,false);
                } else {
                    dragView = from.inflate(menuLayout,null);
                }
//                AutoUtils.auto(dragView);
                dragLayout.addView(dragView);
                dragLayout.setDragView(dragView);
                SwipeRecyclerViewHolder holder = onCreateWithMenuHolder(dragLayout,viewType);
                holder.setMenuView(dragView);
                holder.setContentView(view);
                holder.initMenu(dragView);
                return holder;
            }
            return createViewHolder(view,viewType);
        }
    }
    
    @Override
    protected  void onBindDefaultData(V holder,final int position) {
        if (mOnMenuItemClickListener != null) {
            View menuView = holder.getMenuView();
            if (menuView instanceof ViewGroup) {
                for (int i = 0;i < ((ViewGroup)menuView).getChildCount();i++) {
                    View child = ((ViewGroup)menuView).getChildAt(i);
                    final int childPosition = i;
                    if (child != null) {
                        child.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnMenuItemClickListener.onMenuItemClick(v,position,childPosition);
                            }
                        });
                    }
                }
            }else {
                menuView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnMenuItemClickListener.onMenuItemClick(v,position,0);
                    }
                });
            }
        }
    }
    
    /**
     * 创建一个带侧滑菜单的ViewHolder
     * @param layout
     * @param viewType
     * @return
     */
    public abstract  SwipeRecyclerViewHolder<T> onCreateWithMenuHolder(MenuDragLayout layout,int viewType);

    public abstract int onCreateRightMenuLayout(int viewType);
}
