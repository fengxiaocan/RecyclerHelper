package com.evil.recycler.adapter;

import android.view.View;

import com.evil.recycler.holder.BaseRecyclerHolder;

class FooterViewHolder extends BaseRecyclerHolder {
    public FooterViewHolder(View itemView) {
        super(itemView);
        isStaggeredGridFullSpan = true;
    }
    @Override
    public void initView(View rootView) { }
}
