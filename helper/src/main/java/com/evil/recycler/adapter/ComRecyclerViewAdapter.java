package com.evil.recycler.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.holder.BaseRecyclerHolder;
import com.evil.recycler.holder.RecyclerViewHolder;
import com.evil.recycler.holder.ViewHolderHepler;
import com.evil.recycler.inface.IRecyclerData;
import com.evil.recycler.inface.OnAdapterItemClickListener;
import com.evil.recycler.inface.OnFooterItemClickListener;
import com.evil.recycler.inface.OnHeaderItemClickListener;
import com.evil.recycler.inface.OnItemChildClickListener;
import com.evil.recycler.inface.OnItemChildLongClickListener;

import java.util.ArrayList;
import java.util.List;

public abstract class ComRecyclerViewAdapter<T extends IRecyclerData, V extends RecyclerViewHolder<T>>
        extends RecyclerView.Adapter<BaseRecyclerHolder> implements IExtendAdapter<T> {

    public static final int EXTEND_RECYCLER_EXTENSION_TYPE = -0xFFFFFD;//扩展布局
    public static final int EXTEND_RECYCLER_HEADER_TYPE = -0xFFFFFE;//头部
    public static final int EXTEND_RECYCLER_FOOTER_TYPE = -0xFFFFFF;//脚部
    public static final int EMPTY = 0;
    public static final int LOADING = 1;
    public static final int ERROR = 2;

    protected List<T> mDatas;
    protected LinearLayout mHeaderLayouts;
    protected LinearLayout mFooterLayouts;
    protected FrameLayout mContainerLayouts;//中间的容器布局

    protected SparseArray<View> mContainerView;//中间容器布局需要容纳的View的集合

    protected boolean emptyCompatHeaderOrFooter = false;//中间的布局是否兼容头部或者脚部
    protected boolean useEmpty = true;

    protected OnHeaderItemClickListener mOnHeaderItemClickListener;
    protected OnFooterItemClickListener mOnFooterItemClickListener;
    protected OnAdapterItemClickListener<T> mOnItemClickListener;
    protected OnItemChildClickListener<T> mOnItemChildClickListener;
    protected OnItemChildLongClickListener<T> mOnItemChildLongClickListener;

    public void setOnItemClickListener(OnAdapterItemClickListener<T> mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener<T> mOnItemChildClickListener)
    {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    public void setOnItemChildLongClickListener(
            OnItemChildLongClickListener<T> mOnItemChildlongClickListener)
    {
        this.mOnItemChildLongClickListener = mOnItemChildlongClickListener;
    }

    public void setUseEmpty(boolean useEmpty) {
        this.useEmpty = useEmpty;
    }

    public void setEmptyCompatHeaderOrFooter(boolean emptyCompatHeaderOrFooter) {
        this.emptyCompatHeaderOrFooter = emptyCompatHeaderOrFooter;
    }

    public void showLoadingView() {
        showExtensionView(LOADING);
    }

    public void showErrorView() {
        showExtensionView(ERROR);
    }

    public void showEmptyView() {
        showExtensionView(EMPTY);
    }

    public void showExtensionView(int type) {
        clear();
        setContainer(getContainerView(type));
        notifyDataSetChanged();
    }

    public void showDefaultView() {
        setContainer(null);
        notifyDataSetChanged();
    }

    public View getContainerView(int type) {
        if (mContainerView != null) {
            return mContainerView.get(type);
        }
        return null;
    }

    private void checkContainer() {
        if (mContainerView == null) {
            mContainerView = new SparseArray<>();
        }
    }

    public LinearLayout getHeaderLayout() {
        return mHeaderLayouts;
    }

    public LinearLayout getFooterLayout() {
        return mFooterLayouts;
    }

    public FrameLayout getContainerLayout() {
        return mContainerLayouts;
    }

    public void addContainerView(int type, View view) {
        if (view == null) {
            return;
        }
        checkContainer();
        mContainerView.put(type, view);
    }

    public void addContainerView(int type, ViewGroup parent, int resourid) {
        if (parent == null || resourid == 0) {
            return;
        }
        checkContainer();
        View view = LayoutInflater.from(parent.getContext()).inflate(resourid, parent, false);
        mContainerView.put(type, view);
    }

    public void setEmptyView(View view) {
        addContainerView(EMPTY, view);
    }

    public void setLoadingView(View view) {
        addContainerView(LOADING, view);
    }

    public void setErrorView(View view) {
        addContainerView(ERROR, view);
    }

    public void setEmptyView(ViewGroup parent, int resourid) {
        addContainerView(EMPTY, parent, resourid);
    }

    public void setLoadingView(ViewGroup parent, int resourid) {
        addContainerView(LOADING, parent, resourid);
    }

    public void setErrorView(ViewGroup parent, int resourid) {
        addContainerView(ERROR, parent, resourid);
    }

    private boolean canShowHealderOrFooter() {
        return emptyCompatHeaderOrFooter || !isRealEmpty();
    }

    protected int getHeaderLayoutOrientation() {
        return LinearLayout.VERTICAL;
    }

    protected int getFooterLayoutOrientation() {
        return LinearLayout.VERTICAL;
    }

    private void checkHeader(Context context) {
        if (mHeaderLayouts == null) {
            mHeaderLayouts = new LinearLayout(context);
            mHeaderLayouts.setLayoutParams(
                    new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        mHeaderLayouts.setOrientation(getHeaderLayoutOrientation());
    }

    public int getHeaderSize() {
        if (mHeaderLayouts == null) {
            return 0;
        }
        return mHeaderLayouts.getChildCount();
    }

    public void addHeader(View header) {
        checkHeader(header.getContext());
        for (int i = 0; i < mHeaderLayouts.getChildCount(); i++) {
            View child = mHeaderLayouts.getChildAt(i);
            if (child.equals(header)) {
                return;
            }
        }
        BaseRecyclerHolder.removeParent(header);
        mHeaderLayouts.addView(header);
        if (mHeaderLayouts.getChildCount() == 1) {
            if (canShowHealderOrFooter()) {
                notifyItemInserted(0);
            }
        }
    }

    public void addHeader(View header, int index) {
        checkHeader(header.getContext());
        for (int i = 0; i < mHeaderLayouts.getChildCount(); i++) {
            View child = mHeaderLayouts.getChildAt(i);
            if (child.equals(header)) {
                return;
            }
        }
        BaseRecyclerHolder.removeParent(header);
        if (index < 0) {
            index = 0;
        } else if (index > mHeaderLayouts.getChildCount()) {
            index = mHeaderLayouts.getChildCount();
        }
        mHeaderLayouts.addView(header, index);
        if (mHeaderLayouts.getChildCount() == 1) {
            if (canShowHealderOrFooter()) {
                notifyItemInserted(0);
            }
        }
    }

    public void addHeader(ViewGroup parent, int layoutRes) {
        View header = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        addHeader(header);
    }

    public void addHeader(ViewGroup parent, int layoutRes, int index) {
        View header = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        addHeader(header, index);
    }

    public void removeHeader(View header) {
        if (mHeaderLayouts != null) {
            mHeaderLayouts.removeView(header);
            if (mHeaderLayouts.getChildCount() == 0) {
                if (canShowHealderOrFooter()) {
                    myNotifyItemRemoved(0, true);
                }
            }
        }
    }

    public void removeHeader(int index) {
        if (mHeaderLayouts != null) {
            mHeaderLayouts.removeViewAt(index);
            if (mHeaderLayouts.getChildCount() == 0) {
                if (canShowHealderOrFooter()) {
                    myNotifyItemRemoved(0, true);
                }
            }
        }
    }

    public void removeAllHeader() {
        if (mHeaderLayouts != null) {
            mHeaderLayouts.removeAllViews();
            if (canShowHealderOrFooter()) {
                myNotifyItemRemoved(0, true);
            }
        }
    }

    private void checkFooter(Context context) {
        if (mFooterLayouts == null) {
            mFooterLayouts = new LinearLayout(context);
            mFooterLayouts.setLayoutParams(
                    new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        mFooterLayouts.setOrientation(getFooterLayoutOrientation());
    }

    public int getFooterSize() {
        if (mFooterLayouts == null) {
            return 0;
        }
        return mFooterLayouts.getChildCount();
    }

    public void addFooter(View footer) {
        checkFooter(footer.getContext());
        for (int i = 0; i < mFooterLayouts.getChildCount(); i++) {
            View child = mFooterLayouts.getChildAt(i);
            if (child.equals(footer)) {
                return;
            }
        }
        BaseRecyclerHolder.removeParent(footer);
        mFooterLayouts.addView(footer);
        if (getHeaderSize() == 1) {
            if (canShowHealderOrFooter()) {
                notifyItemInserted(getItemCount() - 1);
            }
        }
    }

    public void addFooter(View footer, int index) {
        checkFooter(footer.getContext());
        for (int i = 0; i < mFooterLayouts.getChildCount(); i++) {
            View child = mFooterLayouts.getChildAt(i);
            if (child.equals(footer)) {
                return;
            }
        }
        BaseRecyclerHolder.removeParent(footer);
        if (index < 0) {
            index = 0;
        } else if (index > mFooterLayouts.getChildCount()) {
            index = mFooterLayouts.getChildCount();
        }
        mFooterLayouts.addView(footer, index);
        if (getHeaderSize() == 1) {
            if (canShowHealderOrFooter()) {
                notifyItemInserted(getItemCount() - 1);
            }
        }
    }

    public void addFooter(ViewGroup parent, int layoutRes) {
        View footer = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        addFooter(footer);
    }

    public void addFooter(ViewGroup parent, int layoutRes, int index) {
        View footer = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        addFooter(footer, index);
    }

    public void removeFooter(View footer) {
        if (mFooterLayouts != null) {
            mFooterLayouts.removeView(footer);
            if (mFooterLayouts.getChildCount() == 0) {
                if (canShowHealderOrFooter()) {
                    myNotifyItemRemoved(getItemCount() - 1, false);
                }
            }
        }
    }

    public void removeFooter(int index) {
        if (mFooterLayouts != null) {
            mFooterLayouts.removeViewAt(index);
            if (mFooterLayouts.getChildCount() == 0) {
                if (canShowHealderOrFooter()) {
                    myNotifyItemRemoved(getItemCount() - 1, false);
                }
            }
        }
    }

    public void removeAllFooter() {
        if (mFooterLayouts != null) {
            mFooterLayouts.removeAllViews();
            if (canShowHealderOrFooter()) {
                myNotifyItemRemoved(getItemCount() - 1, false);
            }
        }
    }

    private void checkContainer(Context context) {
        if (mContainerLayouts == null) {
            mContainerLayouts = new FrameLayout(context);
            mContainerLayouts.setLayoutParams(
                    new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    protected void setContainer(View view) {
        if (view == null) {
            clearContainer();
        } else {
            checkContainer(view.getContext());
            if (mContainerLayouts.indexOfChild(view) < 0) {
                mContainerLayouts.removeAllViews();
                mContainerLayouts.addView(view);
            }
        }
    }

    public void clearContainer() {
        if (mContainerLayouts != null) {
            mContainerLayouts.removeAllViews();
        }
    }

    protected int getContainerSize() {
        return mContainerLayouts != null && mContainerLayouts.getChildCount() > 0 ? 1 : 0;
    }

    @Override
    public int getDataCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public int getHeaderCount() {
        return (mHeaderLayouts != null && mHeaderLayouts.getChildCount() > 0) ? 1 : 0;
    }

    public int getFooterCount() {
        return (mFooterLayouts != null && mFooterLayouts.getChildCount() > 0) ? 1 : 0;
    }

    public boolean hasHeader() {
        return getHeaderCount() > 0;
    }

    public boolean hasFooter() {
        return getFooterCount() > 0;
    }

    public boolean isContainer(int viewType) {
        return viewType == EXTEND_RECYCLER_EXTENSION_TYPE;
    }

    public boolean isHeader(int viewType) {
        return viewType == EXTEND_RECYCLER_HEADER_TYPE;
    }

    public boolean isFooter(int viewType) {
        return viewType == EXTEND_RECYCLER_FOOTER_TYPE;
    }

    public int getGridLayoutSpanSize(GridLayoutManager manager, int position) {
        //如果是头布局或者是脚布局返回为1;
        int itemViewType = getItemViewType(position);
        if (isHeader(itemViewType)) {
            return manager.getSpanCount();
        } else if (isFooter(itemViewType)) {
            return manager.getSpanCount();
        } else if (isFooter(itemViewType)) {
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

    public void setOnHeaderClickListener(OnHeaderItemClickListener onHeaderItemClickListener) {
        mOnHeaderItemClickListener = onHeaderItemClickListener;
    }

    public void setOnFooterClickListener(OnFooterItemClickListener onFooterItemClickListener) {
        mOnFooterItemClickListener = onFooterItemClickListener;
    }


    protected int getEmptyViewCount() {
        if (mContainerLayouts == null || mContainerLayouts.getChildCount() == 0) {
            return 0;
        }
        if (!useEmpty) {
            return 0;
        }
        if (getDataCount() != 0) {
            return 0;
        }
        return 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (getEmptyViewCount() == 1) {
            if (emptyCompatHeaderOrFooter) {
                if (position == 0 && hasHeader()) {
                    return EXTEND_RECYCLER_HEADER_TYPE;
                } else if (hasFooter() && position == getAllItemCount() - 1) {
                    return EXTEND_RECYCLER_FOOTER_TYPE;
                } else {
                    return EXTEND_RECYCLER_EXTENSION_TYPE;
                }
            } else {
                return EXTEND_RECYCLER_EXTENSION_TYPE;
            }
        } else {
            if (position == 0 && hasHeader()) {
                return EXTEND_RECYCLER_HEADER_TYPE;
            } else if (hasFooter() && position == getAllItemCount() - 1) {
                return EXTEND_RECYCLER_FOOTER_TYPE;
            } else {
                if (position < 0) {
                    return 0;
                }
                return getData(position - getHeaderCount()).recycleType();
            }
        }
    }

    @Override
    public int getItemCount() {
        if (getEmptyViewCount() == 0) {
            return getDataCount() + getHeaderCount() + getFooterCount();
        } else {
            return emptyCompatHeaderOrFooter ? getAddViewCount() : getContainerSize();
        }
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
            return createViewHolder(view, viewType);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
        holder.onBindData(this, position);
        if (holder instanceof HeaderViewHolder) {
            if (mOnHeaderItemClickListener != null) {
                for (int i = 0; i < mHeaderLayouts.getChildCount(); i++) {
                    View child = mHeaderLayouts.getChildAt(i);
                    child.setOnClickListener(new TOnClickListener<Integer>(position) {
                        @Override
                        public void onClick(View v) {
                            mOnHeaderItemClickListener.onHeaderClick(v, t);
                        }
                    });
                }
            }
        } else if (holder instanceof FooterViewHolder) {
            if (mOnFooterItemClickListener != null) {
                for (int i = 0; i < mFooterLayouts.getChildCount(); i++) {
                    View child = mFooterLayouts.getChildAt(i);
                    child.setOnClickListener(new TOnClickListener<Integer>(position) {
                        @Override
                        public void onClick(View v) {
                            mOnFooterItemClickListener.onFooterClick(v, t);
                        }
                    });
                }
            }
        } else if (holder instanceof RecyclerViewHolder) {
            final int realPosition = position - getHeaderCount();
            RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
            T t = getData(realPosition);

            ViewHolderHepler.setData(viewHolder, t);
            ViewHolderHepler.setRealPosition(viewHolder, position);
            ViewHolderHepler.setOnItemChildClickListener(viewHolder, mOnItemChildClickListener);
            ViewHolderHepler.setOnItemChildLongClickListener(viewHolder,
                    mOnItemChildLongClickListener);

            viewHolder.setData(this, t, realPosition);
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new OnItemClick(realPosition));
            }
        }
    }

    protected int getAddViewCount() {
        return getHeaderCount() + getFooterCount() + getContainerSize();
    }

    protected ExtensionViewHolder getExtensionHolder() {
        return new ExtensionViewHolder(mContainerLayouts);
    }

    protected HeaderViewHolder getHeaderHolder() {
        return new HeaderViewHolder(mHeaderLayouts);
    }

    protected FooterViewHolder getFooterHolder() {
        return new FooterViewHolder(mFooterLayouts);
    }

    public abstract boolean attachParent();

    public abstract V createViewHolder(View view, int viewType);


    @Override
    public T getData(int position) {
        return mDatas.get(position);
    }

    @Override
    public T getFirstData() {
        return ObjectUtils.getFirstData(mDatas);
    }

    @Override
    public T getLastData() {
        return ObjectUtils.getLastData(mDatas);
    }

    @Override
    public List<T> getDatas() {
        return mDatas;
    }

    @Override
    public void setDatas(List<T> datas) {
        initEmpty();
        mDatas = datas;
    }

    @Override
    public void setDatas(T... datas) {
        initEmpty();
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
    public void setDatasAndNotify(T... datas) {
        initEmpty();
        if (datas != null) {
            if (mDatas == null) {
                mDatas = new ArrayList<>();
            }
            mDatas.clear();
            for (T data : datas) {
                mDatas.add(data);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void setDatasAndNotify(List<T> datas) {
        initEmpty();
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public void addDatas(T... datas) {
        initEmpty();
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
        initEmpty();
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
        initEmpty();
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
        initEmpty();
        if (data == null) {
            return;
        }
        if (mDatas == null) {
            mDatas = new ArrayList<>();
            mDatas.add(data);
            notifyDataSetChanged();
        } else {
            mDatas.add(data);
            notifyItemInserted(getDataCount() - 1 + getHeaderCount());
        }
    }

    @Override
    public void addDatasAndNotify(List<T> datas) {
        initEmpty();
        if (ObjectUtils.isEmpty(datas)) {
            return;
        }
        if (mDatas == null) {
            mDatas = datas;
            notifyDataSetChanged();
        } else {
            mDatas.addAll(datas);
            notifyItemRangeInserted(getDataCount() - datas.size() + getHeaderCount(), datas.size());
        }
    }

    @Override
    public void insertData(int position, T data) {
        initEmpty();
        if (data == null) {
            return;
        }
        if (mDatas == null) {
            mDatas = new ArrayList<>();
            mDatas.add(data);
        } else {
            if (position <= 0) {
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
        initEmpty();
        if (data == null) {
            return;
        }
        if (mDatas == null) {
            mDatas = new ArrayList<>();
            mDatas.add(data);
            notifyDataSetChanged();
        } else {
            if (position < 0) {
                mDatas.add(0, data);
                notifyItemInserted(getHeaderCount());
            } else if (position >= mDatas.size()) {
                mDatas.add(data);
                notifyItemInserted(getDataCount() - 1 + getHeaderCount());
            } else {
                mDatas.add(position, data);
                notifyItemInserted(position + getHeaderCount());
            }
        }
    }

    @Override
    public void insertDatas(int position, List<T> datas) {
        initEmpty();
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
        initEmpty();
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
                notifyItemRangeInserted(getHeaderCount(), itemCount);
            } else if (position >= size1) {
                //position位于最后
                mDatas.addAll(datas);
                notifyItemRangeInserted(size1 + getHeaderCount(), itemCount);
            } else {
                //position位于中间
                mDatas.addAll(position, datas);
                notifyItemRangeInserted(position + getHeaderCount(), itemCount);
            }
        }
    }

    @Override
    public void remove(int position) {
        initEmpty();
        if (mDatas != null && position >= 0 && position < mDatas.size()) {
            mDatas.remove(position);
        }
    }

    @Override
    public void removeAndNotify(int position) {
        initEmpty();
        if (mDatas != null && position >= 0 && position < mDatas.size()) {
            mDatas.remove(position);
            if (isRealEmpty()) {
                notifyDataSetChanged();
            } else {
                myNotifyItemRemoved(position, false);
            }
        }
    }

    private void myNotifyItemRemoved(int position, boolean isHeader) {
        int index = position;
        if (!isHeader) {
            index += getHeaderCount();
        }
        notifyItemRemoved(index);
        int i = getDataCount() - position;
        notifyItemRangeChanged(index, i + getFooterCount());
    }

    @Override
    public void remove(T t) {
        initEmpty();
        if (mDatas != null) {
            mDatas.remove(t);
        }
    }

    @Override
    public void removeAndNotify(T t) {
        initEmpty();
        if (mDatas != null) {
            int index = mDatas.indexOf(t);
            if (index >= 0) {
                mDatas.remove(index);
                if (isRealEmpty()) {
                    notifyDataSetChanged();
                } else {
                    myNotifyItemRemoved(index, false);
                }
            }
        }
    }

    private void initEmpty() {
        if (useEmpty) {
            setContainer(getContainerView(EMPTY));
        } else {
            setContainer(null);
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

    public boolean isRealEmpty() {
        return getDataCount() == 0;
    }

    public boolean isNotRealEmpty() {
        return getDataCount() > 0;
    }

    public boolean isEmpty() {
        return getAllItemCount() == 0;
    }

    public boolean isNotEmpty() {
        return getAllItemCount() > 0;
    }

    protected int getAllItemCount() {
        return getDataCount() + getHeaderCount() + getFooterCount();
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
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

    private class OnItemClick implements View.OnClickListener {
        private int position;

        public OnItemClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getDatas(), position);
            }
        }
    }

}
