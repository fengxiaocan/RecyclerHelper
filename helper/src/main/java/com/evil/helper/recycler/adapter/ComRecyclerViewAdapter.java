package com.evil.helper.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evil.helper.recycler.holder.BaseRecyclerHolder;
import com.evil.helper.recycler.holder.EmptyRecyclerView;
import com.evil.helper.recycler.holder.ErrorRecyclerView;
import com.evil.helper.recycler.holder.LoadingRecyclerView;
import com.evil.helper.recycler.holder.RecyclerViewFooter;
import com.evil.helper.recycler.holder.RecyclerViewHeader;
import com.evil.helper.recycler.holder.RecyclerViewHolder;
import com.evil.helper.recycler.inface.IRecycleData;
import com.evil.helper.recycler.inface.OnAdapterItemClickListener;
import com.evil.helper.recycler.inface.OnFooterClickListener;
import com.evil.helper.recycler.inface.OnHeaderClickListener;
import com.evil.helper.recycler.inface.RecyclerType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 11/6/18
 * @desc 一个可以添加头部跟尾部View的 recyclerview adapter
 * @注意 viewType尽量不要使用负数, 使用也不能小等于 -0x11111
 */
public abstract class ComRecyclerViewAdapter<T extends IRecycleData, V extends RecyclerViewHolder<T>>
        extends RecyclerView.Adapter<BaseRecyclerHolder> implements IExtendAdapter<T> {
    public static final int EXTEND_RECYCLER_HEADER_TYPE = -0x11111;//头部
    public static final int EXTEND_RECYCLER_FOOTER_TYPE = -0xfffff;//脚部

    protected List<T> mDatas;
    protected LinkedList<RecyclerViewHeader> mHeaders;
    protected LinkedList<RecyclerViewFooter> mFooters;

    protected EmptyRecyclerView emptyRecyclerView;//空布局
    protected LoadingRecyclerView loadingRecyclerView;//加载中布局
    protected ErrorRecyclerView errorRecyclerView;//错误布局
    protected boolean emptyCompatHeaderOrFooter = false;//空布局是否兼容头部或者脚步
    protected OnAdapterItemClickListener<T> mTOnAdapterItemClickListener;
    protected OnHeaderClickListener mOnHeaderClickListener;
    protected OnFooterClickListener mOnFooterClickListener;
    private RecyclerType currentShowType = RecyclerType.NORMAL;

    public void setEmptyCompatHeaderOrFooter(boolean emptyCompatHeaderOrFooter) {
        this.emptyCompatHeaderOrFooter = emptyCompatHeaderOrFooter;
    }

    public void showLoadingView() {
        currentShowType = RecyclerType.LOADING;
        notifyDataSetChanged();
    }

    public void showErrorView() {
        currentShowType = RecyclerType.ERROR;
        notifyDataSetChanged();
    }

    public void showEmptyView() {
        currentShowType = RecyclerType.EMPTY;
        notifyDataSetChanged();
    }

    public void showNormalView() {
        currentShowType = RecyclerType.NORMAL;
        notifyDataSetChanged();
    }

    public void hideEmptyView() {
        currentShowType = RecyclerType.NORMAL;
        notifyDataSetChanged();
    }

    private synchronized void checkHeader() {
        if (mHeaders == null) {
            mHeaders = new LinkedList<>();
        }
    }

    public void addHeader(View header) {
        checkHeader();
        mHeaders.add(new RecyclerViewHeader(EXTEND_RECYCLER_HEADER_TYPE - mHeaders.size(), header));
        notifyDataSetChanged();
    }

    public void addHeader(BaseRecyclerHolder header) {
        checkHeader();
        mHeaders.add(new RecyclerViewHeader(EXTEND_RECYCLER_HEADER_TYPE - mHeaders.size(), header));
        notifyDataSetChanged();
    }

    private synchronized void checkFooter() {
        if (mFooters == null) {
            mFooters = new LinkedList<>();
        }
    }

    public void addFooter(View footer) {
        checkFooter();
        mFooters.add(new RecyclerViewFooter(EXTEND_RECYCLER_FOOTER_TYPE - mFooters.size(), footer));
        notifyDataSetChanged();
    }

    public void addFooter(BaseRecyclerHolder footer) {
        checkFooter();
        mFooters.add(new RecyclerViewFooter(EXTEND_RECYCLER_FOOTER_TYPE - mFooters.size(), footer));
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        switch (currentShowType) {
            case EMPTY:
                if (emptyCompatHeaderOrFooter) {
                    if (isHasEmptyView()) {
                        return getHeaderCount() + getFooterCount() + 1;
                    }
                    return getHeaderCount() + getFooterCount();
                } else {
                    if (isHasEmptyView()) {
                        return 1;
                    }
                    return 0;
                }
            case LOADING:
                if (emptyCompatHeaderOrFooter) {
                    if (isHasLoadingView()) {
                        return getHeaderCount() + getFooterCount() + 1;
                    }
                    return getHeaderCount() + getFooterCount();
                } else {
                    if (isHasLoadingView()) {
                        return 1;
                    }
                    return 0;
                }
            case ERROR:
                if (emptyCompatHeaderOrFooter) {
                    if (isHasErrorView()) {
                        return getHeaderCount() + getFooterCount() + 1;
                    }
                    return getHeaderCount() + getFooterCount();
                } else {
                    if (isHasErrorView()) {
                        return 1;
                    }
                    return 0;
                }
            default:
                return getAllItemCount();
        }
    }

    /**
     * 获取所有条目的数量
     *
     * @return
     */
    public int getAllItemCount() {
        return getRealItemCount() + getHeaderCount() + getFooterCount();
    }

    public int getRealItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public int getHeaderCount() {
        return mHeaders == null ? 0 : mHeaders.size();
    }

    public int getFooterCount() {
        return mFooters == null ? 0 : mFooters.size();
    }

    public boolean hasHeader() {
        return mHeaders != null && mHeaders.size() > 0;
    }

    public boolean hasFooter() {
        return mFooters != null && mFooters.size() > 0;
    }

    public void removeHeader(int position) {
        if (hasHeader()) {
            mHeaders.remove(position);
        }
        notifyDataSetChanged();
    }

    public void removeAllHeader() {
        mHeaders = null;
        notifyDataSetChanged();
    }

    public void removeFooter(int position) {
        if (hasFooter()) {
            mFooters.remove(position);
        }
        notifyDataSetChanged();
    }

    public void removeAllFooter() {
        mFooters = null;
        notifyDataSetChanged();
    }

    public boolean isEmptyView(int viewType) {
        return isHasEmptyView() && viewType == emptyRecyclerView.getViewType();
    }

    public boolean isLoadingView(int viewType) {
        return isHasLoadingView() && viewType == loadingRecyclerView.getViewType();
    }

    public boolean isErrorView(int viewType) {
        return isHasErrorView() && viewType == errorRecyclerView.getViewType();
    }

    public boolean isHeader(int viewType) {
        return hasHeader() && viewType <= EXTEND_RECYCLER_HEADER_TYPE &&
               viewType > EXTEND_RECYCLER_FOOTER_TYPE;
    }

    public boolean isHeaderOfPosition(int position) {
        return hasHeader() && position < getHeaderCount();
    }

    public boolean isFooterOfPosition(int position) {
        if (currentShowType == RecyclerType.NORMAL) {
            return hasFooter() && position >= getRealItemCount() + getHeaderCount();
        }
        if (isHasOtherView()) {
            return hasFooter() && position >= 1 + getHeaderCount();
        } else {
            return hasFooter() && position >= getHeaderCount();
        }
    }

    public boolean isFooter(int viewType) {
        return hasFooter() && viewType <= EXTEND_RECYCLER_FOOTER_TYPE;
    }

    BaseRecyclerHolder getHeaderHolder(int viewType) {
        try {
            return mHeaders.get(EXTEND_RECYCLER_HEADER_TYPE - viewType).getHolder();
        } catch (Exception e) {
            return null;
        }
    }

    BaseRecyclerHolder getFooterHolder(int viewType) {
        try {
            return mFooters.get(EXTEND_RECYCLER_FOOTER_TYPE - viewType).getHolder();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            ((GridLayoutManager) manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (currentShowType != RecyclerType.NORMAL) {
                        return ((GridLayoutManager) manager).getSpanCount();
                    }
                    //如果是头布局或者是脚布局返回为1;
                    int itemViewType = getItemViewType(position);
                    if (isHeader(itemViewType)) {
                        return ((GridLayoutManager) manager).getSpanCount();
                    }
                    if (isFooter(itemViewType)) {
                        return ((GridLayoutManager) manager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    /**
     * 针对流式布局
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(@NonNull BaseRecyclerHolder holder) {
        super.onViewAttachedToWindow(holder);
        int layoutPosition = holder.getLayoutPosition();
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null) {
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                boolean b = currentShowType != RecyclerType.NORMAL || isHeaderOfPosition(
                        layoutPosition) || isFooterOfPosition(layoutPosition);
                if (b) {
                    StaggeredGridLayoutManager.LayoutParams params =
                            (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                    //占领全部空间;
                    params.setFullSpan(true);
                }
            }
        }
    }

    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        mOnHeaderClickListener = onHeaderClickListener;
    }

    public void setOnFooterClickListener(OnFooterClickListener onFooterClickListener) {
        mOnFooterClickListener = onFooterClickListener;
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
            return createViewHolder(view, viewType);
        }
    }

    /**
     * 为true 可以解决宽度显示不全的问题
     *
     * @return
     */
    public abstract boolean attachParent();

    public abstract V createViewHolder(View view, int viewType);

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
        holder.onBindData(this, position);
        if (isHeaderOfPosition(position)) {
            if (mOnHeaderClickListener != null) {
                holder.itemView.setOnClickListener(new TOnClickListener<Integer>(position) {
                    @Override
                    public void onClick(View v) {
                        mOnHeaderClickListener.onHeaderClick(v, getData());
                    }
                });
            }
        } else if (isFooterOfPosition(position)) {
            if (mOnFooterClickListener != null) {
                holder.itemView.setOnClickListener(new TOnClickListener<Integer>(
                        position - (getRealItemCount() + getHeaderCount())) {
                    @Override
                    public void onClick(View v) {
                        mOnFooterClickListener.onFooterClick(v, getData());
                    }
                });
            }
        } else {
            if (currentShowType == RecyclerType.NORMAL) {
                int realPosition = position - getHeaderCount();
                if (holder instanceof RecyclerViewHolder) {
                    RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
                    viewHolder.setData(this, getData(realPosition), realPosition);
                }
                if (mTOnAdapterItemClickListener != null) {
                    holder.getItemView().setOnClickListener(new OnItemClick(realPosition));
                }
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (currentShowType != RecyclerType.NORMAL) {
            if (emptyCompatHeaderOrFooter) {
                //兼容
                if (isHeaderOfPosition(position)) {
                    return mHeaders.get(position).getViewType();
                } else if (isFooterOfPosition(position)) {
                    return mFooters.get(position - (1 + getHeaderCount())).getViewType();
                } else {
                    if (currentShowType == RecyclerType.EMPTY) {
                        if (isHasEmptyView()) {
                            return emptyRecyclerView.getViewType();
                        }
                    } else if (currentShowType == RecyclerType.ERROR) {
                        if (isHasEmptyView()) {
                            return errorRecyclerView.getViewType();
                        }
                    } else if (currentShowType == RecyclerType.LOADING) {
                        if (isHasEmptyView()) {
                            return loadingRecyclerView.getViewType();
                        }
                    }
                    return 0;
                }
            } else {
                if (currentShowType == RecyclerType.EMPTY) {
                    if (isHasEmptyView()) {
                        return emptyRecyclerView.getViewType();
                    }
                } else if (currentShowType == RecyclerType.ERROR) {
                    if (isHasEmptyView()) {
                        return errorRecyclerView.getViewType();
                    }
                } else if (currentShowType == RecyclerType.LOADING) {
                    if (isHasEmptyView()) {
                        return loadingRecyclerView.getViewType();
                    }
                }
                return 0;
            }
        } else {
            if (isHeaderOfPosition(position)) {
                return mHeaders.get(position).getViewType();
            } else if (isFooterOfPosition(position)) {
                return mFooters.get(position - (getRealItemCount() + getHeaderCount()))
                               .getViewType();
            } else {
                return getData(position - getHeaderCount()).getRecycleType();
            }
        }
    }

    public void setOnItemClickListener(OnAdapterItemClickListener<T> listener) {
        mTOnAdapterItemClickListener = listener;
    }

    public List<T> getDatas() {
        return mDatas;
    }

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

    public void setDatas(List<T> datas) {
        mDatas = datas;
    }

    public void setDatasAndNotify(T... datas) {
        setDatas(datas);
        notifyDataSetChanged();
    }

    public void setDatasAndNotify(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> datas) {
        if (mDatas == null) {
            mDatas = datas;
        } else {
            mDatas.addAll(datas);
        }
    }

    public void addDatas(T... datas) {
        if (datas != null) {
            setDatas(datas);
        }
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        for (T data : datas) {
            mDatas.add(data);
        }
    }

    public void addData(T data) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.add(data);
    }

    public void clear() {
        mDatas = new ArrayList<>();
    }

    public void clearAndNotify() {
        clear();
        notifyDataSetChanged();
    }

    public T getData(int position) {
        return mDatas.get(position);
    }

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

    public void insertDataAndNotify(int position, T data) {
        insertData(position, data);
        notifyDataSetChanged();
    }

    public void addDataAndNotify(T data) {
        addData(data);
        notifyDataSetChanged();
    }

    public void addDatasAndNotify(List<T> datas) {
        addDatas(datas);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
    }

    public void removeAndNotify(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    public void remove(T t) {
        mDatas.remove(t);
    }

    public void removeAndNotify(T t) {
        mDatas.remove(t);
        notifyDataSetChanged();
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
                for (int size = datas.size(); size > 0; size--) {
                    mDatas.add(position, datas.get(size));
                }
            }
        }
    }

    @Override
    public void insertDatasAndNotify(int position, List<T> datas) {
        insertDatas(position, datas);
        notifyDataSetChanged();
    }

    public void setEmptyView(int type, BaseRecyclerHolder holder) {
        emptyRecyclerView = new EmptyRecyclerView(type, holder);
    }

    public void setEmptyView(View layotu) {
        emptyRecyclerView = new EmptyRecyclerView(layotu);
    }

    public void setEmptyView(int viewType, View rootView) {
        emptyRecyclerView = new EmptyRecyclerView(viewType, rootView);
    }

    public void setEmptyView(BaseRecyclerHolder holder) {
        emptyRecyclerView = new EmptyRecyclerView(holder);
    }

    public void setEmptyView(Context context, int viewType, int layoutId) {
        emptyRecyclerView = new EmptyRecyclerView(context, viewType, layoutId);
    }

    public void setEmptyView(Context context, int layoutId) {
        emptyRecyclerView = new EmptyRecyclerView(context, layoutId);
    }

    public void setLoadingView(int type, BaseRecyclerHolder holder) {
        loadingRecyclerView = new LoadingRecyclerView(type, holder);
    }

    public void setLoadingView(View layotu) {
        loadingRecyclerView = new LoadingRecyclerView(layotu);
    }

    public void setLoadingView(int viewType, View rootView) {
        loadingRecyclerView = new LoadingRecyclerView(viewType, rootView);
    }

    public void setLoadingView(BaseRecyclerHolder holder) {
        loadingRecyclerView = new LoadingRecyclerView(holder);
    }

    public void setLoadingView(Context context, int viewType, int layoutId) {
        loadingRecyclerView = new LoadingRecyclerView(context, viewType, layoutId);
    }

    public void setLoadingView(Context context, int layoutId) {
        loadingRecyclerView = new LoadingRecyclerView(context, layoutId);
    }


    public void setErrorView(int type, BaseRecyclerHolder holder) {
        errorRecyclerView = new ErrorRecyclerView(type, holder);
    }

    public void setErrorView(View layotu) {
        errorRecyclerView = new ErrorRecyclerView(layotu);
    }

    public void setErrorView(int viewType, View rootView) {
        errorRecyclerView = new ErrorRecyclerView(viewType, rootView);
    }

    public void setErrorView(BaseRecyclerHolder holder) {
        errorRecyclerView = new ErrorRecyclerView(holder);
    }

    public void setErrorView(Context context, int viewType, int layoutId) {
        errorRecyclerView = new ErrorRecyclerView(context, viewType, layoutId);
    }

    public void setErrorView(Context context, int layoutId) {
        errorRecyclerView = new ErrorRecyclerView(context, layoutId);
    }


    public boolean isHasEmptyView() {
        return emptyRecyclerView != null;
    }

    public boolean isHasLoadingView() {
        return loadingRecyclerView != null;
    }

    public boolean isHasErrorView() {
        return loadingRecyclerView != null;
    }

    protected boolean isHasOtherView() {
        return isHasEmptyView() || isHasLoadingView() || isHasErrorView();
    }

    public boolean isRealEmpty() {
        return getRealItemCount() == 0;
    }

    public boolean isNotRealEmpty() {
        return getRealItemCount() > 0;
    }

    public boolean isEmpty() {
        return getAllItemCount() == 0;
    }

    public boolean isNotEmpty() {
        return getAllItemCount() > 0;
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    private class OnItemClick implements View.OnClickListener {
        private int position;

        public OnItemClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (mTOnAdapterItemClickListener != null) {
                mTOnAdapterItemClickListener.onItemClick(v, getDatas(), position);
            }
        }
    }
}
