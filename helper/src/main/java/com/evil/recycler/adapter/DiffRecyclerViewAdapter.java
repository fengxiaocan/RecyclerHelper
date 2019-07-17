package com.evil.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.DiffCallBack;
import com.evil.recycler.holder.BaseRecyclerHolder;
import com.evil.recycler.holder.RecyclerViewHolder;
import com.evil.recycler.inface.IDiffData;
import com.evil.recycler.inface.OnAdapterItemClickListener;

import java.util.ArrayList;
import java.util.List;

public abstract class DiffRecyclerViewAdapter<T extends IDiffData, V extends RecyclerViewHolder<T>>
        extends RecyclerView.Adapter<BaseRecyclerHolder> implements IExtendAdapter<T> {

    protected List<T> mDatas;
    protected OnAdapterItemClickListener<T> mOnItemClickListener;
    protected LayoutInflater layoutInflater;

    public void setOnItemClickListener(OnAdapterItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setDiffDatas(List<T> datas) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack<T>(mDatas, datas),
                true);
        //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter
        diffResult.dispatchUpdatesTo(this);
        //将新数据给Adapter
        mDatas = datas;
    }

    public void setDiffDatas(List<T> datas, boolean detectMoves) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack<T>(mDatas, datas),
                detectMoves);
        //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter
        diffResult.dispatchUpdatesTo(this);
        //将新数据给Adapter
        mDatas = datas;
    }

    public void setDiffDatas(T... datas) {
        if (datas != null) {
            List<T> newList = new ArrayList<>();
            for (T data : datas) {
                newList.add(data);
            }
            setDiffDatas(newList);
        }
    }

    public void setDiffDatas(boolean detectMoves, T... datas) {
        if (datas != null) {
            List<T> newList = new ArrayList<>();
            for (T data : datas) {
                newList.add(data);
            }
            setDiffDatas(newList, detectMoves);
        }
    }

    @Override
    public List<T> getDatas() {
        return mDatas;
    }

    @Override
    public void setDatas(T... datas) {
        if (datas != null) {
            if (mDatas == null) {
                mDatas = new ArrayList<>();
            }
            mDatas.clear();
            for (T data : datas) {
                mDatas.add(data);
            }
        }
    }

    @Override
    public void setDatas(List<T> datas) {
        mDatas = datas;
    }

    @Override
    public T getData(int position) {
        return mDatas.get(position);
    }

    @Override
    public T getFirstData() {
        return ListUtils.getFirstData(mDatas);
    }

    @Override
    public T getLastData() {
        return ListUtils.getLastData(mDatas);
    }

    @Override
    public void setDatasAndNotify(T... datas) {
        setDatas(datas);
        notifyDataSetChanged();
    }

    @Override
    public void setDatasAndNotify(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public void addDatas(T... datas) {
        if (ObjectUtils.isEmpty(datas)) {
            return;
        }
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        for (T data : datas) {
            mDatas.add(data);
        }
    }

    @Override
    public void addDatas(List<T> datas) {
        if (ObjectUtils.isEmpty(datas)) {
            return;
        }
        if (mDatas == null) {
            mDatas = datas;
        } else {
            mDatas.addAll(datas);
        }
    }

    @Override
    public void addData(T data) {
        if (data == null) {
            return;
        }
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.add(data);
    }

    @Override
    public void addDataAndNotify(T data) {
        addData(data);
        notifyItemInserted(mDatas.size() - 1);
    }

    @Override
    public void addDatasAndNotify(List<T> datas) {
        addDatas(datas);
        notifyDataSetChanged();
    }

    @Override
    public void insertData(int position, T data) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
            mDatas.add(data);
        } else {
            if (position < 0) {
                mDatas.add(0, data);
            } else if (position >= mDatas.size()) {
                mDatas.add(data);
            } else {
                mDatas.add(position, data);
            }
        }
    }

    @Override
    public void insertDataAndNotify(int position, T data) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
            mDatas.add(data);
            notifyDataSetChanged();
        } else {
            if (position < 0) {
                mDatas.add(0, data);
                notifyItemInserted(0);
            } else if (position >= mDatas.size()) {
                mDatas.add(data);
                notifyItemInserted(mDatas.size() - 1);
            } else {
                mDatas.add(position, data);
                notifyItemInserted(position);
            }
        }
    }


    @Override
    public void insertDatas(int position, List<T> datas) {
        if (ObjectUtils.isEmpty(datas)) {
            return;
        }
        if (ObjectUtils.isEmpty(mDatas)) {
            mDatas = datas;
        } else {
            if (position <= 0) {
                //position位于第一
                datas.addAll(mDatas);
                mDatas = datas;
            } else if (position >= mDatas.size()) {
                //position位于最后
                mDatas.addAll(datas);
            } else {
                //position位于中间
                mDatas.addAll(position, datas);
            }
        }
    }

    @Override
    public void insertDatasAndNotify(int position, List<T> datas) {
        if (ObjectUtils.isEmpty(datas)) {
            return;
        }
        if (ObjectUtils.isEmpty(mDatas)) {
            mDatas = datas;
            notifyDataSetChanged();
        } else {
            int size1 = mDatas.size();
            int itemCount = datas.size();
            if (position <= 0) {
                //position位于第一
                datas.addAll(mDatas);
                mDatas = datas;
                notifyItemRangeInserted(0, itemCount);
            } else if (position >= size1) {
                //position位于最后
                mDatas.addAll(datas);
                notifyItemRangeInserted(size1, itemCount);
            } else {
                //position位于中间
                mDatas.addAll(position, datas);
                notifyItemRangeInserted(position, itemCount);
            }
        }
    }

    @Override
    public void remove(int position) {
        if (mDatas != null && position >= 0 && position < mDatas.size()) {
            mDatas.remove(position);
        }
    }

    @Override
    public void removeAndNotify(int position) {
        remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void remove(T t) {
        if (mDatas != null) {
            mDatas.remove(t);
        }
    }

    @Override
    public void removeAndNotify(T t) {
        if (mDatas != null) {
            int index = mDatas.indexOf(t);
            if (index >= 0) {
                mDatas.remove(index);
                notifyItemRemoved(index);
            }
        }
    }

    @Override
    public void clear() {
        if (mDatas != null) {
            mDatas.clear();
        }
    }

    @Override
    public void clearAndNotify() {
        clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, final int position) {
        holder.onBindData(this, position);
        List<T> datas = getDatas();
        T t = null;
        if (datas != null && position < datas.size()) {
            t = datas.get(position);
        }
        if (holder instanceof RecyclerViewHolder) {
            ((RecyclerViewHolder) holder).setData(this, t, position);
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v, getDatas(), position);
                    }
                });
            }
        }
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        if (attachParent()) {
            view = layoutInflater.inflate(onCreateLayoutRes(viewType), parent, false);//解决高度不全问题
        } else {
            view = layoutInflater.inflate(onCreateLayoutRes(viewType), null);//解决宽度不能铺满
        }
        return createViewHolder(view, viewType);
    }

    public abstract boolean attachParent();

    public abstract V createViewHolder(View view, int viewType);

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getRealItemCount();
    }

    @Override
    public int getRealItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    public boolean isNotEmpty() {
        return getItemCount() > 0;
    }

    @Override
    public void onViewRecycled(@NonNull BaseRecyclerHolder holder) {
        holder.onViewRecycled();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseRecyclerHolder holder) {
        holder.onViewDetachedFromWindow();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseRecyclerHolder holder) {
        holder.onViewAttachedToWindow();
    }

}
