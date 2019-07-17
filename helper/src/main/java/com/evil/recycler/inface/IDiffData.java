package com.evil.recycler.inface;

public interface IDiffData<T> {
    boolean isEquals(T obj);

    boolean isSameData(T obj);
}
