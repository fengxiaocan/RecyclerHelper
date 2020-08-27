package com.evil.recycler.adapter;

import android.view.View;

import com.evil.recycler.holder.BaseRecyclerHolder;

class HeaderViewHolder extends BaseRecyclerHolder {
    public HeaderViewHolder(View itemView,BaseRecyclerViewAdapter adapter) {
        super(itemView);
        selfAdapter = adapter;
        isStaggeredGridFullSpan = true;
    }
    @Override
    public void initView(View rootView) { }

    @Override
    public final boolean canDrag(){
        return false;
    }
    @Override
    public final boolean canSwipe(){
        return false;
    }
}
