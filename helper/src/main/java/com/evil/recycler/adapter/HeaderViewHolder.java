package com.evil.recycler.adapter;

import android.view.View;

import com.evil.recycler.holder.BaseRecyclerHolder;

class HeaderViewHolder extends BaseRecyclerHolder {
    public HeaderViewHolder(View itemView) {
        super(itemView);
        isStaggeredGridFullSpan = true;
    }
    @Override
    public void initView(View rootView) { }
}