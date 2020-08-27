package com.evil.recyclerhelper;

import android.view.View;
import android.widget.TextView;

import com.evil.recycler.adapter.ComRecyclerViewAdapter;
import com.evil.recycler.adapter.SwipeRecyclerViewAdapter;
import com.evil.recycler.holder.RecyclerViewHolder;
import com.evil.recycler.holder.SwipeRecyclerViewHolder;

public class TestAdapter4
        extends ComRecyclerViewAdapter<RecyclerData, RecyclerViewHolder<RecyclerData>> {

    @Override
    public RecyclerViewHolder<RecyclerData> createViewHolder(View view, int viewType) {
        return new RecyclerViewHolder<RecyclerData>(view) {
            @Override
            public void onBindData(RecyclerData data) {
                mTvTitle.setText(data.name);
            }

            private TextView mTvTitle;

            @Override
            public void initView(View rootView) {
                mTvTitle = (TextView) findViewById(R.id.tv_title);
            }

        };
    }


    @Override
    public int onCreateLayoutRes(int viewType) {
        return R.layout.holder_test2;
    }
}
