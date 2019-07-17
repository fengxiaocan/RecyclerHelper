package com.evil.recyclerhelper;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.adapter.ComRecyclerViewAdapter;
import com.evil.recycler.adapter.DiffRecyclerViewAdapter;
import com.evil.recycler.adapter.RecyclerViewAdapter;
import com.evil.recycler.holder.RecyclerViewHolder;

public class TestAdapter3
        extends DiffRecyclerViewAdapter<RecyclerData, RecyclerViewHolder<RecyclerData>> {


    @Override
    public boolean attachParent() {
        return true;
    }

    @Override
    public RecyclerViewHolder<RecyclerData> createViewHolder(View view, int viewType) {
        return new RecyclerViewHolder<RecyclerData>(view) {
            private TextView mTvTitle;

            @Override
            public void initView(View rootView) {
                mTvTitle = findViewById(R.id.tv_title);
            }

            @Override
            public void setData(RecyclerView.Adapter adapter, RecyclerData data, int position)
            {
                mTvTitle.setText(data.name);
            }
        };
    }


    @Override
    public int onCreateLayoutRes(int viewType) {
        return R.layout.holder_test2;
    }
}
