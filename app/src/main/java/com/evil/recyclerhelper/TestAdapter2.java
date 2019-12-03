package com.evil.recyclerhelper;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.adapter.ComRecyclerViewAdapter;
import com.evil.recycler.adapter.SwipeRecyclerViewAdapter;
import com.evil.recycler.holder.RecyclerViewHolder;
import com.evil.recycler.holder.SwipeRecyclerViewHolder;

public class TestAdapter2
        extends SwipeRecyclerViewAdapter<RecyclerData, SwipeRecyclerViewHolder<RecyclerData>> {


    @Override
    public boolean attachParent() {
        return true;
    }

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
                mTvTitle = findViewById(R.id.tv_title);
            }

        };
    }


    @Override
    public int onCreateLayoutRes(int viewType) {
        return R.layout.holder_test2;
    }

    @Override
    public SwipeRecyclerViewHolder<RecyclerData> onCreateWithMenuHolder(View layout, int viewType) {
        return new SwipeRecyclerViewHolder<RecyclerData>(layout) {
            @Override
            public void onBindData(RecyclerData data) {
            }

            @Override
            public void initMenu(View menuLayout) {

            }

            @Override
            public void initView(View rootView) {

            }
        };
    }

    @Override
    public int onCreateRightMenuLayout(int viewType) {
        return R.layout.holder_test_menu;
    }
}
