package com.evil.helper.recycler.holder;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 12/6/18
 * @desc ...
 */
public abstract class BaseRecyclerHolder extends RecyclerView.ViewHolder {
    public BaseRecyclerHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public abstract void initView(View rootView);
    
    public View getItemView(){
        return itemView;
    }
    
    public void onBindData(RecyclerView.Adapter adapter,int position){}
    
    public final <T extends View> T findViewById(@IdRes int id) {
        if (id == View.NO_ID) {
            return null;
        }
        return itemView.findViewById(id);
    }

}
