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
