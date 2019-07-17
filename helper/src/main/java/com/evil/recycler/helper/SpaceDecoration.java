package com.evil.recycler.helper;

import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.decoration.SpacesItemDecoration;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 6/6/18
 * @desc ...
 */
public class SpaceDecoration extends IDecoration<SpacesItemDecoration> {
    SpaceDecoration(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    SpacesItemDecoration createDecoration() {
        return new SpacesItemDecoration(0);
    }

    public SpaceDecoration color(int color) {
        itemDecoration.setColor(color);
        return this;
    }

    public SpaceDecoration space(int value) {
        itemDecoration.space(value);
        return this;
    }

    public SpaceDecoration space(int left,int top,int right,int bottom) {
        itemDecoration.space(left,top,right,bottom);
        return this;
    }
}
