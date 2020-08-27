package com.evil.recycler.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.evil.helper.R;
import com.evil.recycler.adapter.BaseRecyclerViewAdapter;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 12/6/18
 * @desc ...
 */
public abstract class BaseRecyclerHolder<D> extends RecyclerView.ViewHolder{
    public BaseRecyclerViewAdapter<D,RecyclerViewHolder<D>> selfAdapter;
    protected boolean isStaggeredGridFullSpan=false;//在流式布局的情况下是否需要占满全屏

    public BaseRecyclerHolder(View itemView){
        super(itemView);
        initView(itemView);
    }

    public boolean canDrag(){
        return true;
    }

    public boolean canSwipe(){
        return true;
    }

    public abstract void initView(View rootView);

    public final Context getContext(){
        return itemView.getContext();
    }

    public BaseRecyclerViewAdapter getAdapter(){
        return selfAdapter;
    }

    public void removeAndNotifySelf(){
        selfAdapter.removeAndNotify(getBindingAdapterPosition());
    }

    public void isStaggeredGridFullSpan(boolean isFull){
        isStaggeredGridFullSpan=isFull;
    }

    public View getItemView(){
        return itemView;
    }

    public void onBindData(RecyclerView.Adapter adapter,int position){
        //绑定数据
    }

    public final <T extends View> T  findViewById(@IdRes int id) {
        if (id == View.NO_ID) {
            return null;
        }
        return (T)itemView.findViewById(id);
    }


    public void onViewRecycled(){

    }

    public void onViewDetachedFromWindow(){

    }

    public void onViewAttachedToWindow(){
        /**
         * 针对流式布局
         */
        if(isStaggeredGridFullSpan){
            ViewGroup.LayoutParams layoutParams=itemView.getLayoutParams();
            if(layoutParams!=null){
                if(layoutParams instanceof StaggeredGridLayoutManager.LayoutParams){
                    StaggeredGridLayoutManager.LayoutParams params=
                            (StaggeredGridLayoutManager.LayoutParams)layoutParams;
                    //占领全部空间;
                    params.setFullSpan(true);
                }
            }
        }
    }

    public static void removeParent(View view){
        if(view!=null){
            ViewParent parent=view.getParent();
            if(parent instanceof ViewGroup){
                ((ViewGroup)parent).removeView(view);
            }
        }
    }
}
