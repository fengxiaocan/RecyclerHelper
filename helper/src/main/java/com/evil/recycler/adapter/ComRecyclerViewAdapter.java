package com.evil.recycler.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.holder.BaseRecyclerHolder;
import com.evil.recycler.holder.RecyclerViewHolder;
import com.evil.recycler.inface.IRecyclerData;
import com.evil.recycler.inface.OnFooterItemClickListener;
import com.evil.recycler.inface.OnHeaderItemClickListener;

import java.util.List;

public abstract class ComRecyclerViewAdapter<T extends IRecyclerData,V extends RecyclerViewHolder<T>>
        extends BaseRecyclerViewAdapter<T,V>{

    public static final int EXTEND_RECYCLER_EXTENSION_TYPE=-0xFFFFFD;//扩展布局
    public static final int EXTEND_RECYCLER_HEADER_TYPE=-0xFFFFFE;//头部
    public static final int EXTEND_RECYCLER_FOOTER_TYPE=-0xFFFFFF;//脚部
    public static final int EMPTY=0;
    public static final int LOADING=1;
    public static final int ERROR=2;

    protected LinearLayout mHeaderLayouts;
    protected LinearLayout mFooterLayouts;
    protected FrameLayout mContainerLayouts;//中间的容器布局

    protected SparseArray<View> mContainerView;//中间容器布局需要容纳的View的集合

    protected boolean emptyCompatHeaderOrFooter=true;//中间的布局是否兼容头部或者脚部
    protected boolean useEmpty=true;//是否需要自动使用空布局???

    protected OnHeaderItemClickListener mOnHeaderItemClickListener;
    protected OnFooterItemClickListener mOnFooterItemClickListener;

    public void setUseEmpty(boolean useEmpty){
        this.useEmpty=useEmpty;
        initEmpty();
    }

    public void setEmptyCompatHeaderOrFooter(boolean emptyCompatHeaderOrFooter){
        this.emptyCompatHeaderOrFooter=emptyCompatHeaderOrFooter;
    }

    public void showLoadingView(){
        showExtensionView(LOADING);
    }

    public void showErrorView(){
        showExtensionView(ERROR);
    }

    public void showEmptyView(){
        showExtensionView(EMPTY);
    }

    public void showExtensionView(int type){
        clear();
        setContainer(getContainerView(type));
        notifyDataSetChanged();
    }

    public void showDefaultView(){
        setContainer(null);
        notifyDataSetChanged();
    }

    public View getContainerView(int type){
        if(mContainerView!=null){
            return mContainerView.get(type);
        }
        return null;
    }

    private void checkContainer(){
        if(mContainerView==null){
            mContainerView=new SparseArray<>();
        }
    }

    public LinearLayout getHeaderLayout(){
        return mHeaderLayouts;
    }

    public LinearLayout getFooterLayout(){
        return mFooterLayouts;
    }

    public FrameLayout getContainerLayout(){
        return mContainerLayouts;
    }

    public void addContainerView(int type,View view){
        if(view==null){
            return;
        }
        checkContainer();
        mContainerView.put(type,view);
    }

    public void addContainerView(int type,ViewGroup parent,int resourid){
        if(parent==null||resourid==0){
            return;
        }
        checkContainer();
        View view=LayoutInflater.from(parent.getContext())
                                .inflate(resourid,parent,false);
        mContainerView.put(type,view);
        if(type==EMPTY){
            setContainer(view);
        }
    }

    public void setEmptyView(View view){
        addContainerView(EMPTY,view);
        setContainer(view);
    }

    public void setLoadingView(View view){
        addContainerView(LOADING,view);
    }

    public void setErrorView(View view){
        addContainerView(ERROR,view);
    }

    public void setEmptyView(ViewGroup parent,int resourid){
        addContainerView(EMPTY,parent,resourid);
    }

    public void setLoadingView(ViewGroup parent,int resourid){
        addContainerView(LOADING,parent,resourid);
    }

    public void setErrorView(ViewGroup parent,int resourid){
        addContainerView(ERROR,parent,resourid);
    }

    /**
     * 是否能展示头部或者尾巴
     *
     * @return
     */
    private boolean canShowHealderOrFooter(){
        if(emptyCompatHeaderOrFooter){
            //带有兼容的
            return true;
        } else{
            //不兼容并且是空的情况下
            if(getEmptyViewCount()==1){
                return false;
            } else{
                return true;
            }
        }
    }

    protected int getHeaderLayoutOrientation(){
        return LinearLayout.VERTICAL;
    }

    protected int getFooterLayoutOrientation(){
        return LinearLayout.VERTICAL;
    }

    private void checkHeader(Context context){
        if(mHeaderLayouts==null){
            mHeaderLayouts=new LinearLayout(context);
            mHeaderLayouts.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                         ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        mHeaderLayouts.setOrientation(getHeaderLayoutOrientation());
    }

    public int getHeaderChildSize(){
        if(mHeaderLayouts==null){
            return 0;
        }
        return mHeaderLayouts.getChildCount();
    }

    public void addHeader(View header){
        if(header==null){
            return;
        }
        checkHeader(header.getContext());
        for(int i=0;i<mHeaderLayouts.getChildCount();i++){
            View child=mHeaderLayouts.getChildAt(i);
            if(child.equals(header)){
                return;
            }
        }
        BaseRecyclerHolder.removeParent(header);
        mHeaderLayouts.addView(header);
        addHeaderClick(header);
        if(mHeaderLayouts.getChildCount()==1){
            if(canShowHealderOrFooter()){
                notifyItemInserted(0);
            }
        }
    }

    public void addHeader(View header,int index){
        if(header==null){
            return;
        }
        checkHeader(header.getContext());
        for(int i=0;i<mHeaderLayouts.getChildCount();i++){
            View child=mHeaderLayouts.getChildAt(i);
            if(child.equals(header)){
                return;
            }
        }
        BaseRecyclerHolder.removeParent(header);
        if(index<0){
            index=0;
        } else if(index>mHeaderLayouts.getChildCount()){
            index=mHeaderLayouts.getChildCount();
        }
        mHeaderLayouts.addView(header,index);
        addHeaderClick(header);
        if(mHeaderLayouts.getChildCount()==1){
            if(canShowHealderOrFooter()){
                notifyItemInserted(0);
            }
        }
    }

    public void addHeader(ViewGroup parent,int layoutRes){
        View header=LayoutInflater.from(parent.getContext())
                                  .inflate(layoutRes,parent,false);
        addHeader(header);
    }

    public void addHeader(ViewGroup parent,int layoutRes,int index){
        View header=LayoutInflater.from(parent.getContext())
                                  .inflate(layoutRes,parent,false);
        addHeader(header,index);
    }

    public void removeHeader(View header){
        if(getHeaderChildSize()>0){
            mHeaderLayouts.removeView(header);
            if(mHeaderLayouts.getChildCount()==0){
                if(canShowHealderOrFooter()){
                    myNotifyItemRemoved(0);
                }
            }
        }
    }

    public void removeHeader(int index){
        if(getHeaderChildSize()>0&&index<getHeaderChildSize()){
            mHeaderLayouts.removeViewAt(index);
            if(mHeaderLayouts.getChildCount()==0){
                if(canShowHealderOrFooter()){
                    myNotifyItemRemoved(0);
                }
            }
        }
    }

    public void removeAllHeader(){
        if(getHeaderChildSize()>0){
            mHeaderLayouts.removeAllViews();
            if(canShowHealderOrFooter()){
                myNotifyItemRemoved(0);
            }
        }
    }

    private void checkFooter(Context context){
        if(mFooterLayouts==null){
            mFooterLayouts=new LinearLayout(context);
            RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                           ViewGroup.LayoutParams.WRAP_CONTENT);
            mFooterLayouts.setLayoutParams(params);
        }
        mFooterLayouts.setOrientation(getFooterLayoutOrientation());
    }

    public int getFooterChildSize(){
        if(mFooterLayouts==null){
            return 0;
        }
        return mFooterLayouts.getChildCount();
    }

    public void addFooter(View footer){
        if(footer==null){
            return;
        }
        checkFooter(footer.getContext());
        for(int i=0;i<mFooterLayouts.getChildCount();i++){
            View child=mFooterLayouts.getChildAt(i);
            if(child.equals(footer)){
                return;
            }
        }
        BaseRecyclerHolder.removeParent(footer);
        mFooterLayouts.addView(footer);
        addFooterClick(footer);
        if(mFooterLayouts.getChildCount()==1){
            if(canShowHealderOrFooter()){
                notifyItemInserted(getItemCount()-1);
            }
        }
    }

    public void addFooter(View footer,int index){
        if(footer==null){
            return;
        }
        checkFooter(footer.getContext());
        for(int i=0;i<mFooterLayouts.getChildCount();i++){
            View child=mFooterLayouts.getChildAt(i);
            if(child.equals(footer)){
                return;
            }
        }
        BaseRecyclerHolder.removeParent(footer);
        if(index<0){
            index=0;
        } else if(index>mFooterLayouts.getChildCount()){
            index=mFooterLayouts.getChildCount();
        }
        mFooterLayouts.addView(footer,index);
        addFooterClick(footer);
        if(mFooterLayouts.getChildCount()==1){
            if(canShowHealderOrFooter()){
                notifyItemInserted(getItemCount()-1);
            }
        }
    }

    public void addFooter(ViewGroup parent,int layoutRes){
        View footer=LayoutInflater.from(parent.getContext())
                                  .inflate(layoutRes,parent,false);
        addFooter(footer);
    }

    public void addFooter(ViewGroup parent,int layoutRes,int index){
        View footer=LayoutInflater.from(parent.getContext())
                                  .inflate(layoutRes,parent,false);
        addFooter(footer,index);
    }

    public void removeFooter(View footer){
        if(getFooterChildSize()>0){
            mFooterLayouts.removeView(footer);
            if(mFooterLayouts.getChildCount()==0){
                if(canShowHealderOrFooter()){
                    myNotifyItemRemoved(getItemCount()-1);
                }
            }
        }
    }

    public void removeFooter(int index){
        if(getFooterChildSize()>0&&index<getFooterChildSize()){
            mFooterLayouts.removeViewAt(index);
            if(mFooterLayouts.getChildCount()==0){
                if(canShowHealderOrFooter()){
                    myNotifyItemRemoved(getItemCount()-1);
                }
            }
        }
    }

    public void removeAllFooter(){
        if(getFooterChildSize()>0){
            mFooterLayouts.removeAllViews();
            if(canShowHealderOrFooter()){
                myNotifyItemRemoved(getItemCount()-1);
            }
        }
    }

    private void checkContainer(Context context){
        if(mContainerLayouts==null){
            mContainerLayouts=new FrameLayout(context);
            mContainerLayouts.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                            ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    protected void setContainer(View view){
        if(view==null){
            clearContainer();
        } else{
            checkContainer(view.getContext());
            if(mContainerLayouts.indexOfChild(view)<0){
                mContainerLayouts.removeAllViews();
                mContainerLayouts.addView(view);
            }
        }
    }

    public void clearContainer(){
        if(mContainerLayouts!=null){
            mContainerLayouts.removeAllViews();
        }
    }

    protected int getContainerSize(){
        return mContainerLayouts!=null&&mContainerLayouts.getChildCount()>0 ? 1 : 0;
    }

    public int getHeaderCount(){
        return (getHeaderChildSize()>0) ? 1 : 0;
    }

    public int getFooterCount(){
        return (getFooterChildSize()>0) ? 1 : 0;
    }

    public boolean hasHeader(){
        return getHeaderCount()>0;
    }

    public boolean hasFooter(){
        return getFooterCount()>0;
    }

    public boolean isContainer(int viewType){
        return viewType==EXTEND_RECYCLER_EXTENSION_TYPE;
    }

    public boolean isHeader(int viewType){
        return viewType==EXTEND_RECYCLER_HEADER_TYPE;
    }

    public boolean isFooter(int viewType){
        return viewType==EXTEND_RECYCLER_FOOTER_TYPE;
    }

    public int getGridLayoutSpanSize(GridLayoutManager manager,int position){
        //如果是头布局或者是脚布局返回为1;
        int itemViewType=getItemViewType(position);
        if(isHeader(itemViewType)){
            return manager.getSpanCount();
        } else if(isFooter(itemViewType)){
            return manager.getSpanCount();
        } else if(isContainer(itemViewType)){
            return manager.getSpanCount();
        }
        return 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager manager=recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            ((GridLayoutManager)manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
                @Override
                public int getSpanSize(int position){
                    return getGridLayoutSpanSize((GridLayoutManager)manager,position);
                }
            });
        }
    }

    public void setOnHeaderClickListener(OnHeaderItemClickListener onHeaderItemClickListener){
        mOnHeaderItemClickListener=onHeaderItemClickListener;
        addHeaderClicks();
    }

    public void setOnFooterClickListener(OnFooterItemClickListener onFooterItemClickListener){
        mOnFooterItemClickListener=onFooterItemClickListener;
        addFooterClicks();
    }

    protected int getEmptyViewCount(){
        if(mContainerLayouts==null||mContainerLayouts.getChildCount()==0){
            return 0;
        }
        if(!useEmpty){
            return 0;
        }
        if(getDataCount()!=0){
            return 0;
        }
        return 1;
    }


    @Override
    public int getItemViewType(int position){
        if(getEmptyViewCount()==1){
            if(emptyCompatHeaderOrFooter){
                if(position==0&&hasHeader()){
                    return EXTEND_RECYCLER_HEADER_TYPE;
                } else if(hasFooter()&&position==getAddViewCount()-1){
                    return EXTEND_RECYCLER_FOOTER_TYPE;
                } else{
                    return EXTEND_RECYCLER_EXTENSION_TYPE;
                }
            } else{
                return EXTEND_RECYCLER_EXTENSION_TYPE;
            }
        } else{
            if(position==0&&hasHeader()){
                return EXTEND_RECYCLER_HEADER_TYPE;
            } else if(hasFooter()&&position==getAllItemCount()-1){
                return EXTEND_RECYCLER_FOOTER_TYPE;
            } else{
                if(position<0){
                    return 0;
                }
                return getData(position-getHeaderCount()).recycleType();
            }
        }
    }

    @Override
    public int getItemCount(){
        if(getEmptyViewCount()==0){
            return getDataCount()+getHeaderCount()+getFooterCount();
        } else{
            return emptyCompatHeaderOrFooter ? getAddViewCount() : getContainerSize();
        }
    }


    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        if(isContainer(viewType)){
            return getExtensionHolder();
        } else if(isFooter(viewType)){
            return getFooterHolder();
        } else if(isHeader(viewType)){
            return getHeaderHolder();
        } else{
            return super.onCreateViewHolder(parent,viewType);
        }
    }

    private void addFooterClicks(){
        if(mFooterLayouts!=null){
            for(int i=0;i<mFooterLayouts.getChildCount();i++){
                addFooterClick(mFooterLayouts.getChildAt(i));
            }
        }
    }

    private void addHeaderClicks(){
        if(mFooterLayouts!=null){
            for(int i=0;i<mHeaderLayouts.getChildCount();i++){
                addFooterClick(mHeaderLayouts.getChildAt(i));
            }
        }
    }

    private void addFooterClick(View child){
        if(mOnFooterItemClickListener!=null){
            child.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(mOnFooterItemClickListener!=null){
                        mOnFooterItemClickListener.onFooterClick(v,mFooterLayouts.indexOfChild(v));
                    }
                }
            });
        }
    }

    private void addHeaderClick(View child){
        if(mOnHeaderItemClickListener!=null){
            child.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(mOnHeaderItemClickListener!=null){
                        mOnHeaderItemClickListener.onHeaderClick(v,mHeaderLayouts.indexOfChild(v));
                    }
                }
            });
        }
    }

    protected int getAddViewCount(){
        return getHeaderCount()+getFooterCount()+getContainerSize();
    }

    protected ExtensionViewHolder getExtensionHolder(){
        return new ExtensionViewHolder(mContainerLayouts,this);
    }

    protected HeaderViewHolder getHeaderHolder(){
        return new HeaderViewHolder(mHeaderLayouts,this);
    }

    protected FooterViewHolder getFooterHolder(){
        return new FooterViewHolder(mFooterLayouts,this);
    }


    protected void initEmpty(){
        if(useEmpty){
            setContainer(getContainerView(EMPTY));
        } else{
            setContainer(null);
        }
    }

    public boolean isRealEmpty(){
        return getDataCount()==0;
    }

    public boolean isNotRealEmpty(){
        return getDataCount()>0;
    }

    public boolean isEmpty(){
        return getAllItemCount()==0;
    }

    public boolean isNotEmpty(){
        return getAllItemCount()>0;
    }

    protected int getAllItemCount(){
        return getDataCount()+getHeaderCount()+getFooterCount();
    }

    @Override
    public void setDatas(T... datas){
        initEmpty();
        super.setDatas(datas);
    }

    @Override
    public void setDatas(List<T> datas){
        initEmpty();
        super.setDatas(datas);
    }

    @Override
    public void setDatasAndNotify(T... datas){
        initEmpty();
        super.setDatasAndNotify(datas);
    }

    @Override
    public void setDatasAndNotify(List<T> datas){
        initEmpty();
        super.setDatasAndNotify(datas);
    }

    @Override
    public void addDatas(T... datas){
        initEmpty();
        super.addDatas(datas);
    }

    @Override
    public void addDatas(List<T> datas){
        initEmpty();
        super.addDatas(datas);
    }

    @Override
    public void addData(T data){
        initEmpty();
        super.addData(data);
    }

    @Override
    public void addDataAndNotify(T data){
        initEmpty();
        super.addDataAndNotify(data);
    }

    @Override
    public void addDatasAndNotify(List<T> datas){
        initEmpty();
        super.addDatasAndNotify(datas);
    }

    @Override
    public void insertData(int position,T data){
        initEmpty();
        super.insertData(position,data);
    }

    @Override
    public void insertDataAndNotify(int position,T data){
        initEmpty();
        super.insertDataAndNotify(position,data);
    }

    @Override
    public void insertDatas(int position,List<T> datas){
        initEmpty();
        super.insertDatas(position,datas);
    }

    @Override
    public void insertDatasAndNotify(int position,List<T> datas){
        initEmpty();
        super.insertDatasAndNotify(position,datas);
    }

    @Override
    public void clear(){
        initEmpty();
        super.clear();
    }

    @Override
    public void clearAndNotify(){
        initEmpty();
        super.clearAndNotify();
    }

    @Override
    public void remove(int position){
        initEmpty();
        super.remove(position);
    }

    @Override
    public void removeAndNotify(int position){
        initEmpty();
        if(position >= 0&&position<mDatas.size()){
            mDatas.remove(position);
            myNotifyItemRemoved(position);
        }
    }

    private void myNotifyItemRemoved(int position){
        if(getEmptyViewCount()==1){
            notifyDataSetChanged();
        } else{
            int itemCount=getDataCount()-position+getFooterCount();
            if(itemCount<=1){
                notifyDataSetChanged();
            } else{
                int index=position+getHeaderCount();
                notifyItemRemoved(index);
            }
        }
    }

    @Override
    public void remove(T t){
        initEmpty();
        if(mDatas!=null){
            mDatas.remove(t);
        }
    }

    @Override
    public void removeAndNotify(T t){
        initEmpty();
        if(mDatas!=null){
            int index=mDatas.indexOf(t);
            if(index >= 0){
                mDatas.remove(index);
                myNotifyItemRemoved(index);
            }
        }
    }

    @Override
    protected int itemOffset(){
        return getHeaderCount();
    }
}
