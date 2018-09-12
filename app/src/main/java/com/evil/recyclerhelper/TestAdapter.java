package com.evil.recyclerhelper;

import android.view.View;

import com.evil.helper.recycler.adapter.ComRecyclerViewAdapter;
import com.evil.helper.recycler.holder.BaseRecyclerHolder;
import com.evil.helper.recycler.holder.ComRecyclerViewHolder;
import com.evil.helper.recycler.inface.IRecycleData;

public class TestAdapter extends ComRecyclerViewAdapter<IRecycleData,ComRecyclerViewHolder<IRecycleData>>{
    @Override
    public boolean attachParent() {
        return false;
    }

    @Override
    public ComRecyclerViewHolder<IRecycleData> createViewHolder(View view,int viewType) {
        return new ComRecyclerViewHolder<IRecycleData>(view) {
            @Override
            public void setData(
                    ComRecyclerViewAdapter adapter,IRecycleData iRecycleData,int position
            )
            {

            }

            @Override
            public void initView(View rootView) {

            }
        };
    }
    
    @Override
    protected void setDefaultItemData(
            BaseRecyclerHolder holder,int position) {
    
    }
    
    @Override
    public int onCreateLayoutRes(int viewType) {
        return R.layout.holder_test;
    }
}
