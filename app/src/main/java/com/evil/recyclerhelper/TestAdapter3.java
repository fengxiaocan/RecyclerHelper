package com.evil.recyclerhelper;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.evil.recycler.adapter.ComRecyclerViewAdapter;
import com.evil.recycler.holder.RecyclerViewHolder;

public class TestAdapter3 extends ComRecyclerViewAdapter<TestBean,RecyclerViewHolder<TestBean>>{

    @Override
    public boolean attachParent(){
        return true;
    }

    @Override
    public RecyclerViewHolder<TestBean> createViewHolder(View view,int viewType){
        return new RecyclerViewHolder<TestBean>(view){
            ImageView imageView;

            @Override
            public void onBindData(TestBean testBean){
                ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(-1,testBean.getHeight());
                itemView.setLayoutParams(params);
                Glide.with(getContext())
                     .load(testBean.getUrl())
                     .into(imageView);
            }

            @Override
            public void initView(View rootView){
                imageView=(ImageView)rootView;
            }
        };
    }

    @Override
    public int onCreateLayoutRes(int viewType){
        return R.layout.holder_test3;
    }
}
