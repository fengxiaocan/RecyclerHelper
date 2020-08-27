package com.evil.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.holder.BaseRecyclerHolder;
import com.evil.recycler.holder.RecyclerViewHolder;
import com.evil.recycler.holder.ViewHolderHepler;
import com.evil.recycler.inface.OnAdapterItemClickListener;
import com.evil.recycler.inface.OnItemChildClickListener;
import com.evil.recycler.inface.OnItemChildLongClickListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T,V extends RecyclerViewHolder<T>>
        extends RecyclerView.Adapter<BaseRecyclerHolder<T>> implements IExtendAdapter<T>{
    protected final ArrayList<T> mDatas=new ArrayList<>();

    protected OnAdapterItemClickListener<T> mOnItemClickListener;
    protected OnItemChildClickListener<T> mOnItemChildClickListener;
    protected OnItemChildLongClickListener<T> mOnItemChildLongClickListener;

    @Override
    public void setDatas(T... datas){
        mDatas.clear();
        if(datas!=null){
            for(T data: datas){
                mDatas.add(data);
            }
        }
    }

    @Override
    public void setDatas(List<T> datas){
        mDatas.clear();
        if(datas!=null){
            mDatas.addAll(datas);
        }
    }

    @Override
    public void setDatasAndNotify(T... datas){
        setDatas(datas);
        notifyDataSetChanged();
    }

    @Override
    public void setDatasAndNotify(List<T> datas){
        setDatas(datas);
        notifyDataSetChanged();
    }

    @Override
    public void addDatas(T... datas){
        if(ObjectUtils.isEmpty(datas)){
            return;
        }
        for(T data: datas){
            mDatas.add(data);
        }
    }

    @Override
    public void addDatas(List<T> datas){
        if(ObjectUtils.isEmpty(datas)){
            return;
        }
        mDatas.addAll(datas);
    }

    @Override
    public void addData(T data){
        mDatas.add(data);
    }

    @Override
    public void addDataAndNotify(T data){
        addData(data);
        if(getDataCount()==1){
            notifyDataSetChanged();
        } else{
            notifyItemInserted(getDataCount()-1+itemOffset());
        }
    }

    @Override
    public void addDatasAndNotify(List<T> datas){
        if(ObjectUtils.isEmpty(datas)){
            return;
        }
        final int index=getDataCount();
        addDatas(datas);
        if(index>0){
            notifyItemRangeInserted(index+itemOffset(),datas.size());
        } else{
            notifyDataSetChanged();
        }
    }

    @Override
    public void insertData(int position,T data){
        if(position<0){
            mDatas.add(0,data);
        } else if(position >= mDatas.size()){
            mDatas.add(data);
        } else{
            mDatas.add(position,data);
        }
    }

    @Override
    public void insertDataAndNotify(int position,T data){
        final int oldCount=getDataCount();
        if(position<=0){
            mDatas.add(0,data);
            if(oldCount>0){
                notifyItemInserted(itemOffset());
            } else{
                notifyDataSetChanged();
            }
        } else if(position >= oldCount){
            mDatas.add(data);
            if(oldCount>0){
                notifyItemInserted(getDataCount()-1+itemOffset());
            } else{
                notifyDataSetChanged();
            }
        } else{
            mDatas.add(position,data);
            notifyItemInserted(position+itemOffset());
        }
    }

    @Override
    public void insertDatas(int position,List<T> datas){
        if(ObjectUtils.isEmpty(datas)){
            return;
        }
        if(position<=0){
            //position位于第一
            mDatas.addAll(0,datas);
        } else if(position >= mDatas.size()){
            //position位于最后
            mDatas.addAll(datas);
        } else{
            //position位于中间
            mDatas.addAll(position,datas);
        }
    }

    @Override
    public void insertDatasAndNotify(int position,List<T> datas){
        if(ObjectUtils.isEmpty(datas)){
            return;
        }
        final int oldCount=getDataCount();
        final int itemCount=datas.size();

        if(position<=0){
            //position位于第一
            mDatas.addAll(0,datas);
            if(oldCount>0){
                notifyItemRangeInserted(itemOffset(),itemCount);
            } else{
                notifyDataSetChanged();
            }
        } else if(position >= oldCount){
            //position位于最后
            mDatas.addAll(datas);
            if(oldCount>0){
                notifyItemRangeInserted(oldCount+itemOffset(),itemCount);
            } else{
                notifyDataSetChanged();
            }
        } else{
            //position位于中间
            mDatas.addAll(position,datas);
            notifyItemRangeInserted(position+itemOffset(),itemCount);
        }
    }

    @Override
    public void remove(int position){
        if(position >= 0&&position<getDataCount()){
            mDatas.remove(position);
        }
    }

    @Override
    public void removeAndNotify(int position){
        if(position >= 0&&position<getDataCount()){
            remove(position);
            notifyItemRemoved(position+itemOffset());
        }
    }

    @Override
    public void remove(T t){
        mDatas.remove(t);
    }

    @Override
    public void removeAndNotify(T t){
        int index=mDatas.indexOf(t);
        if(index >= 0){
            mDatas.remove(index);
            notifyItemRemoved(index+itemOffset());
        }
    }

    @Override
    public void clear(){
        mDatas.clear();
    }

    @Override
    public void clearAndNotify(){
        clear();
        notifyDataSetChanged();
    }

    @Override
    public int getDataCount(){
        return mDatas.size();
    }

    @Override
    public List<T> getDatas(){
        return mDatas;
    }

    @Override
    public T getData(int position){
        return mDatas.get(position);
    }

    @Override
    public T getFirstData(){
        return ObjectUtils.getFirstData(mDatas);
    }

    @Override
    public T getLastData(){
        return ObjectUtils.getLastData(mDatas);
    }

    @Override
    public void onBindViewHolder(final BaseRecyclerHolder holder,int position){
        holder.onBindData(this,position);
        if(holder instanceof RecyclerViewHolder){
            T t=getData(position-itemOffset());

            RecyclerViewHolder viewHolder=(RecyclerViewHolder)holder;

            ViewHolderHepler.setData(viewHolder,t);
            ViewHolderHepler.setOnItemChildClickListener(viewHolder,mOnItemChildClickListener);
            ViewHolderHepler.setOnItemChildLongClickListener(viewHolder,mOnItemChildLongClickListener);
            ViewHolderHepler.setOnItemClickListener(viewHolder,mOnItemClickListener);

            viewHolder.onBindData(t);
        }
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent,int viewType){
        int resource=onCreateLayoutRes(viewType);
        V viewHolder=createViewHolder(inflate(parent,resource,attachParent()),viewType);
        viewHolder.selfAdapter=this;
        return viewHolder;
    }

    public boolean attachParent(){
        return true;
    }

    public abstract V createViewHolder(View view,int viewType);

    @Override
    public int getItemCount(){
        return getDataCount();
    }

    public void setOnItemClickListener(OnAdapterItemClickListener<T> listener){
        this.mOnItemClickListener=listener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener<T> listener){
        this.mOnItemChildClickListener=listener;
    }

    public void setOnItemChildLongClickListener(OnItemChildLongClickListener<T> listener){
        this.mOnItemChildLongClickListener=listener;
    }

    @Override
    public void onViewRecycled(BaseRecyclerHolder holder){
        holder.onViewRecycled();
    }

    @Override
    public void onViewDetachedFromWindow(BaseRecyclerHolder holder){
        holder.onViewDetachedFromWindow();
    }

    @Override
    public void onViewAttachedToWindow(BaseRecyclerHolder holder){
        holder.onViewAttachedToWindow();
    }

    protected static final View inflate(ViewGroup viewGroup,@LayoutRes int layoutRes,boolean attachToRoot){
        if(attachToRoot){
            return LayoutInflater.from(viewGroup.getContext())
                                 .inflate(layoutRes,viewGroup,false);
        } else{
            return LayoutInflater.from(viewGroup.getContext())
                                 .inflate(layoutRes,null);
        }
    }
    protected int itemOffset(){
        return 0;
    }

}
