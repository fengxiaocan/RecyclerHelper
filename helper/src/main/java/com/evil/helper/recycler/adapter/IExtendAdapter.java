package com.evil.helper.recycler.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.evil.helper.recycler.inface.OnAdapterItemClickListener;

import java.util.List;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 12/6/18
 * @desc ...
 */
public interface IExtendAdapter<T> {
	int EMPTY_VIEW_TYPE = -0x888888;
	int EMPTY_VIEW_TYPE2 = -0x888889;
	
	//设置item点击事件
	void setOnItemClickListener(OnAdapterItemClickListener<T> listener);
	
	//获取数据集
	List<T> getDatas();
	
	//设置数据集
	void setDatas(T... datas);
	
	//设置数据集
	void setDatas(List<T> datas);
	
	//设置数据并刷新
	void setDatasAndNotify(T... datas);
	
	//设置数据并刷新
	void setDatasAndNotify(List<T> datas);
	
	//添加数据集
	void addDatas(List<T> datas);
	
	//添加数据集
	void addDatas(T... datas);
	
	//添加单个数据
	void addData(T data);
	
	//清空
	void clear();
	
	//清空并刷新
	void clearAndNotify();
	
	//获取对应位置的数据
	T getData(int position);
	
	//获取第一个位置的数据
	T getFirstData();
	
	//获取最后一个位置的数据
	T getLastData();
	
	//插入数据集到对应位置
	void insertDatas(int position,List<T> datas);
	
	//插入数据集到对应位置并刷新
	void insertDatasAndNotify(int position,List<T> datas);
	
	//添加数据到对应位置
	void insertData(int position,T data);
	
	//添加数据到对应位置,并刷新
	void insertDataAndNotify(int position,T data);
	
	//添加数据到最后位置,并刷新
	void addDataAndNotify(T data);
	
	//添加数据集到对应位置,并刷新
	void addDatasAndNotify(List<T> datas);
	
	//移除对应位置的数据
	void remove(int position);
	
	//移除对应位置的数据并刷新
	void removeAndNotify(int position);
	
	//移除对应数据
	void remove(T t);
	
	//移除对应数据并刷新
	void removeAndNotify(T t);
	
	/**
	 * 设置空布局
	 *
	 * @param layout 布局id
	 * @param referenceView 参照的View,一般为RecyclerView
	 */
	void setEmptyView(@LayoutRes int layout,ViewGroup referenceView);
	
	/**
	 * 设置空布局
	 *
	 * @param emptyView 空布局
	 */
	void setEmptyView(View emptyView);
	
	
	//是否为空
	boolean isEmpty();
	
	//是否有空布局
	boolean isHasEmptyView();
	
	//获取真实的条目的数量
	int getRealItemCount();
	
	/**
	 * On create layout res int.
	 * 获取创建的布局文件,不需要inflate
	 *
	 * @param viewType the view type
	 * @return the int
	 */
	@LayoutRes
	int onCreateLayoutRes(int viewType);
}
