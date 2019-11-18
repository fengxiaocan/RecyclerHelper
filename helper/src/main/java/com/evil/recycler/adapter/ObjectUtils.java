package com.evil.recycler.adapter;

import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.collection.LongSparseArray;
import androidx.collection.SimpleArrayMap;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/12/24
 *     desc  : utils about object
 * </pre>
 */
final class ObjectUtils {

    public static boolean isEmpty(final Collection obj) {
        return obj == null || obj.isEmpty();
    }

    public static <T> boolean isEmpty(final T... obj) {

        return obj == null || obj.length == 0;
    }
    /**
     * 将未知类型list转成arraylist
     *
     * @param orig
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> convertListToArrayList(List<T> orig) {
        //如果为null直接返回，这里也可以把size=0加上
        if (null == orig) {
            return null;
        }
        if (orig instanceof ArrayList) {//判断是否就是ArrayList,如果是，则强转
            return (ArrayList)orig;
        } else {
            ArrayList<T> returnValue = new ArrayList<>(orig.size());
            for (T t : orig) {
                returnValue.add(t);
            }
            //jdk1.8及以上可以使用这样的循环遍历
            //orig.forEach(t -> returnValue.add(t));
            return returnValue;
        }
    }

    public static <T> T getLastData(List<T> list) {
        if (ObjectUtils.isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }
    public static <T> T getFirstData(List<T> list) {
        if (ObjectUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }
}
