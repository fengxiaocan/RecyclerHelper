package com.evil.recycler.helper;

import android.graphics.drawable.Drawable;

import com.evil.recycler.decoration.RecyclerDividerProps;
import com.evil.recycler.decoration.RecyclerViewDivider;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 6/6/18
 * @desc ...
 */
public class LineDecoration extends IDecoration<RecyclerViewDivider> {

    LineDecoration(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    RecyclerViewDivider createDecoration() {
        return new RecyclerViewDivider();
    }

    public LineDecoration color(int color) {
        itemDecoration.setDecoration(RecyclerDividerProps.with(color)
                                                         .build());
        return this;
    }


    public LineDecoration setDivider(RecyclerDividerProps props) {
        itemDecoration.setDecoration(props);
        return this;
    }

    public LineDecoration addDivider(int type, RecyclerDividerProps props) {
        itemDecoration.addDecoration(type, props);
        return this;
    }

    public LineDecoration addDivider(int type, int color) {
        itemDecoration.addDecoration(type, RecyclerDividerProps.with(color)
                                                               .build());
        return this;
    }

    public LineDecoration addDivider(int type, int color, int height) {
        itemDecoration.addDecoration(type, RecyclerDividerProps.with(color)
                                                               .height(height)
                                                               .build());
        return this;
    }

    public LineDecoration color(int color, int height) {
        itemDecoration.setDecoration(RecyclerDividerProps.with(color)
                                                         .height(height)
                                                         .build());
        return this;
    }

    public LineDecoration color(int color, int height, int marginLeft, int marginRight) {
        itemDecoration.setDecoration(RecyclerDividerProps.with(color)
                                                         .height(height)
                                                         .marginLeft(marginLeft)
                                                         .marginRight(marginRight)
                                                         .build());
        return this;
    }

    public LineDecoration drawable(Drawable drawable) {
        itemDecoration.setDecoration(RecyclerDividerProps.with(drawable)
                                                         .build());
        return this;
    }

    public LineDecoration drawable(Drawable drawable, int height) {
        itemDecoration.setDecoration(RecyclerDividerProps.with(drawable)
                                                         .height(height)
                                                         .build());
        return this;
    }

    public LineDecoration drawable(Drawable drawable, int height, int marginLeft, int marginRight) {
        itemDecoration.setDecoration(RecyclerDividerProps.with(drawable)
                                                         .height(height)
                                                         .marginLeft(marginLeft)
                                                         .marginRight(marginRight)
                                                         .build());
        return this;
    }
}
