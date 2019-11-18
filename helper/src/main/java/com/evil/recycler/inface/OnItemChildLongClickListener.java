package com.evil.recycler.inface;

import android.view.View;

public interface OnItemChildLongClickListener<T> {
    boolean onItemChildLongClick(View view, T data, int position);
}
