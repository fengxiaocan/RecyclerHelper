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
    public static final int EXTEND_RECYCLER_HEADER_TYPE = -0x111111;//头部
    public static final int EXTEND_RECYCLER_FOOTER_TYPE = -0xffffff;//脚部
    public static final int MAX_EXTEND_RECYCLER_TYPE = Integer.MIN_VALUE + 3000;

    protected List<T> mDatas;
    protected LinkedList<RecyclerViewHeader> mHeaders;
    protected LinkedList<RecyclerViewFooter> mFooters;

    protected EmptyRecyclerView emptyRecyclerView;//空布局
    protected LoadingRecyclerView loadingRecyclerView;//加载中布局
    protected ErrorRecyclerView errorRecyclerView;//错误布局
    protected boolean emptyCompatHeaderOrFooter = false;//空布局是否兼容头部或者脚步
    protected boolean autoShowEmpty = false;//在currentShowType == RecyclerType.DEFAULT 情况下自动显示空布局
    protected OnAdapterItemClickListener<T> mTOnAdapterItemClickListener;
    protected OnHeaderClickListener mOnHeaderClickListener;
    protected OnFooterClickListener mOnFooterClickListener;
    protected RecyclerType currentShowType = RecyclerType.DEFAULT;
    protected int OTHER_TYPE = Integer.MIN_VALUE;

    /**
     * 是否自动显示空布局,如果为false,则空布局只能通过调用showEmptyView来展示,为true,在调用showDefaultView,在数据为空时自动添加上空布局
     *
     * @param autoShowEmpty
     */
    public void setAutoShowEmpty(boolean autoShowEmpty) {
        this.autoShowEmpty = autoShowEmpty;
    }

    protected void decrease() {
        OTHER_TYPE++;
        if (OTHER_TYPE >= MAX_EXTEND_RECYCLER_TYPE) {
            OTHER_TYPE = Integer.MIN_VALUE;
        }
    }

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

    public void showDefaultView() {
        currentShowType = RecyclerType.DEFAULT;
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
                if (autoShowEmpty) {//自动显示空布局
                    if (emptyCompatHeaderOrFooter) {//兼容头部尾巴
                        if (isRealEmpty()) {//数据为空
                            if (isHasEmptyView()) {//有空布局就加1
                                return getHeaderCount() + getFooterCount() + 1;
                            } else {//否则就返回头尾
                                return getHeaderCount() + getFooterCount();
                            }
                        } else {//数据不为空就返回数据条数
                            return getAllItemCount();
                        }
                    } else {
                        //不兼容头尾
                        if (isEmpty()) {//如果是空
                            if (isHasEmptyView()) {
                                //有空布局就返回1
                                return 1;
                            } else {
                                //否则就返回0
                                return 0;
                            }
                        } else {
                            return getAllItemCount();
                        }
                    }
                } else {
                    //不自动显示空布局,返回所有数据条目
                    return getAllItemCount();
                }

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
        if (currentShowType == RecyclerType.DEFAULT) {
            if (autoShowEmpty) {
                if (isRealEmpty()) {
                    return hasFooter() && position >= 1 + getHeaderCount();
                } else {
                    return hasFooter() && position >= getRealItemCount() + getHeaderCount();
                }
            } else {
                return hasFooter() && position >= getRealItemCount() + getHeaderCount();
            }
        }
        if (isHasEmptyView() && currentShowType == RecyclerType.EMPTY) {
            return hasFooter() && position >= 1 + getHeaderCount();
        }
        if (isHasErrorView() && currentShowType == RecyclerType.ERROR) {
            return hasFooter() && position >= 1 + getHeaderCount();
        }
        if (isHasLoadingView() && currentShowType == RecyclerType.LOADING) {
            return hasFooter() && position >= 1 + getHeaderCount();
        }
        return hasFooter() && position >= getHeaderCount();
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

    /**
     * 获取GridLayoutManager情况下的SpanSize
     *
     * @param manager
     * @param position
     * @return
     */
    public int getGridLayoutSpanSize(GridLayoutManager manager, int position) {
        if (currentShowType != RecyclerType.DEFAULT) {
            return manager.getSpanCount();
        }
        //如果是头布局或者是脚布局返回为1;
        int itemViewType = getItemViewType(position);
        if (autoShowEmpty && isRealEmpty()) {
            if (isEmptyView(itemViewType)) {
                return manager.getSpanCount();
            }
        }
        if (isHeader(itemViewType)) {
            return manager.getSpanCount();
        }
        if (isFooter(itemViewType)) {
            return manager.getSpanCount();
        }
        return 1;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            ((GridLayoutManager) manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getGridLayoutSpanSize((GridLayoutManager) manager, position);
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
        //        int layoutPosition = holder.getLayoutPosition();
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null) {
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                if (holder.isStaggeredGridFullSpan()) {
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
            if (currentShowType == RecyclerType.DEFAULT && isNotRealEmpty()) {
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
        if (currentShowType != RecyclerType.DEFAULT) {
            if (emptyCompatHeaderOrFooter) {
                //兼容
                if (isHeaderOfPosition(position)) {
                    return mHeaders.get(position).getViewType();
                } else {
                    if (currentShowType == RecyclerType.EMPTY) {
                        if (isHasEmptyView()) {
                            if (isFooterOfPosition(position)) {
                                return mFooters.get(position - (1 + getHeaderCount()))
                                               .getViewType();
                            }
                            return emptyRecyclerView.getViewType();
                        } else {
                            if (isFooterOfPosition(position)) {
                                return mFooters.get(position - (getHeaderCount())).getViewType();
                            }
                        }
                    } else if (currentShowType == RecyclerType.ERROR) {
                        if (isHasErrorView()) {
                            if (isFooterOfPosition(position)) {
                                return mFooters.get(position - (1 + getHeaderCount()))
                                               .getViewType();
                            }
                            return errorRecyclerView.getViewType();
                        } else {
                            if (isFooterOfPosition(position)) {
                                return mFooters.get(position - (getHeaderCount())).getViewType();
                            }
                        }
                    } else if (currentShowType == RecyclerType.LOADING) {
                        if (isHasLoadingView()) {
                            if (isFooterOfPosition(position)) {
                                return mFooters.get(position - (1 + getHeaderCount()))
                                               .getViewType();
                            }
                            return loadingRecyclerView.getViewType();
                        } else {
                            if (isFooterOfPosition(position)) {
                                return mFooters.get(position - (getHeaderCount())).getViewType();
                            }
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
                    if (isHasErrorView()) {
                        return errorRecyclerView.getViewType();
                    }
                } else if (currentShowType == RecyclerType.LOADING) {
                    if (isHasLoadingView()) {
                        return loadingRecyclerView.getViewType();
                    }
                }
                return 0;
            }
        } else {
            if (isHeaderOfPosition(position)) {
                return mHeaders.get(position).getViewType();
            } else {
                int realItemCount = getRealItemCount();
                if (isFooterOfPosition(position)) {
                    if (autoShowEmpty && realItemCount == 0 && isHasEmptyView()) {
                        realItemCount = 1;
                    }
                    return mFooters.get(position - (realItemCount + getHeaderCount()))
                                   .getViewType();
                } else {
                    if (autoShowEmpty && realItemCount == 0 && isHasEmptyView()) {
                        return emptyRecyclerView.getViewType();
                    }
                    return getData(position - getHeaderCount()).getRecycleType();
                }
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
        decrease();
    }

    public void setEmptyView(View layotu) {
        emptyRecyclerView = new EmptyRecyclerView(OTHER_TYPE, layotu);
        decrease();
    }

    public void setEmptyView(int viewType, View rootView) {
        emptyRecyclerView = new EmptyRecyclerView(viewType, rootView);
        decrease();
    }

    public void setEmptyView(BaseRecyclerHolder holder) {
        emptyRecyclerView = new EmptyRecyclerView(OTHER_TYPE, holder);
        decrease();
    }

    public void setEmptyView(Context context, int viewType, int layoutId) {
        emptyRecyclerView = new EmptyRecyclerView(context, viewType, layoutId);
        decrease();
    }

    public void setEmptyView(Context context, int layoutId) {
        emptyRecyclerView = new EmptyRecyclerView(context, OTHER_TYPE, layoutId);
        decrease();
    }

    public void setLoadingView(int type, BaseRecyclerHolder holder) {
        loadingRecyclerView = new LoadingRecyclerView(type, holder);
        decrease();
    }

    public void setLoadingView(View layotu) {
        loadingRecyclerView = new LoadingRecyclerView(OTHER_TYPE, layotu);
        decrease();
    }

    public void setLoadingView(int viewType, View rootView) {
        loadingRecyclerView = new LoadingRecyclerView(viewType, rootView);
        decrease();
    }

    public void setLoadingView(BaseRecyclerHolder holder) {
        loadingRecyclerView = new LoadingRecyclerView(OTHER_TYPE, holder);
        decrease();
    }

    public void setLoadingView(Context context, int viewType, int layoutId) {
        loadingRecyclerView = new LoadingRecyclerView(context, viewType, layoutId);
        decrease();
    }

    public void setLoadingView(Context context, int layoutId) {
        loadingRecyclerView = new LoadingRecyclerView(context, OTHER_TYPE, layoutId);
        decrease();
    }


    public void setErrorView(int type, BaseRecyclerHolder holder) {
        errorRecyclerView = new ErrorRecyclerView(type, holder);
        decrease();
    }

    public void setErrorView(View layotu) {
        errorRecyclerView = new ErrorRecyclerView(OTHER_TYPE, layotu);
        decrease();
    }

    public void setErrorView(int viewType, View rootView) {
        errorRecyclerView = new ErrorRecyclerView(viewType, rootView);
        decrease();
    }

    public void setErrorView(BaseRecyclerHolder holder) {
        errorRecyclerView = new ErrorRecyclerView(OTHER_TYPE, holder);
        decrease();
    }

    public void setErrorView(Context context, int viewType, int layoutId) {
        errorRecyclerView = new ErrorRecyclerView(context, viewType, layoutId);
        decrease();
    }

    public void setErrorView(Context context, int layoutId) {
        errorRecyclerView = new ErrorRecyclerView(context, OTHER_TYPE, layoutId);
        decrease();
    }


    public boolean isHasEmptyView() {
        return emptyRecyclerView != null;
    }

    public boolean isHasLoadingView() {
        return loadingRecyclerView != null;
    }

    public boolean isHasErrorView() {
        return errorRecyclerView != null;
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
