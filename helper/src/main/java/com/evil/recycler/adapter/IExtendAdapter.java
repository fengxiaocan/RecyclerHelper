package com.evil.recycler.adapter;

import androidx.annotation.LayoutRes;

import java.util.List;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 12/6/18
 * @desc ...
 */
interface IExtendAdapter<T> {
    int EMPTY_VIEW_TYPE = -Integer.MIN_VALUE;

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

    //插入数据集到对应位置
    void insertDatas(int position, List<T> datas);

    //插入数据集到对应位置并刷新
    void insertDatasAndNotify(int position, List<T> datas);

    //添加数据到对应位置
    void insertData(int position, T data);

    //添加数据到对应位置,并刷新
    void insertDataAndNotify(int position, T data);

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

    //获取data数据条目的数量
    int getDataCount();

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
