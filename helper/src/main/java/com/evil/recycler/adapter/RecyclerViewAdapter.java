package com.evil.recycler.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.holder.BaseRecyclerHolder;
import com.evil.recycler.holder.RecyclerViewHolder;

public abstract class RecyclerViewAdapter<T,V extends RecyclerViewHolder<T>> extends BaseRecyclerViewAdapter<T,V>{
    private final int MATCH_PARENT=ViewGroup.LayoutParams.MATCH_PARENT;

    protected FrameLayout mEmptyLayouts;//中间的容器布局

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(isEmpty()&&isHasEmptyView()){
            return new ExtensionViewHolder(mEmptyLayouts,this);
        }
        return super.onCreateViewHolder(parent,viewType);
    }

    @Override
    public int getItemViewType(int position){
        if(isHasEmptyView()&&isEmpty()){
            return EMPTY_VIEW_TYPE;
        }
        return 0;
    }

    @Override
    public int getItemCount(){
        if(isEmpty()&&isHasEmptyView()){
            return 1;
        }
        return getDataCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);

        final RecyclerView.LayoutManager manager=recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            ((GridLayoutManager)manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
                @Override
                public int getSpanSize(int position){
                    //如果是头布局或者是脚布局返回为1;
                    if(isEmpty()&&isHasEmptyView()){
                        return ((GridLayoutManager)manager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    public void setEmptyView(ViewGroup view,int layoutRes){
        if(view!=null){
            setEmptyView(inflate(view,layoutRes,true));
        }
    }

    public void setEmptyView(View emptyView){
        if(emptyView==null){
            return;
        }
        if(mEmptyLayouts==null){
            mEmptyLayouts=new FrameLayout(emptyView.getContext());
            mEmptyLayouts.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT,MATCH_PARENT));
        }
        int index=mEmptyLayouts.indexOfChild(emptyView);
        if(index<0){
            mEmptyLayouts.removeAllViews();
            mEmptyLayouts.addView(emptyView);
        }
        notifyDataSetChanged();
    }

    public boolean isEmpty(){
        return getDataCount()==0;
    }

    public boolean isHasEmptyView(){
        return mEmptyLayouts!=null&&mEmptyLayouts.getChildCount()>0;
    }

    public boolean isNotRealEmpty(){
        return getDataCount()>0;
    }

    public boolean isNotEmpty(){
        return getItemCount()>0;
    }

}
