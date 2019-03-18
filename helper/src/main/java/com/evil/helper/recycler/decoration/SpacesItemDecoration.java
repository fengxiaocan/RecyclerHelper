package com.evil.helper.recycler.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int left;
    private int right;
    private int bottom;
    private int top;
    private int color = -1;

    public SpacesItemDecoration(int space) {
        this.left = space;
        this.right = space;
        this.bottom = space;
        this.top = space;
    }

    public SpacesItemDecoration(int left,int top,int right,int bottom) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
    }

    public SpacesItemDecoration space(int space) {
        this.left = space;
        this.right = space;
        this.bottom = space;
        this.top = space;
        return this;
    }
    public SpacesItemDecoration space(int left,int top,int right,int bottom) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        return this;
    }

    public SpacesItemDecoration left(int value) {
        this.left = value;
        return this;
    }

    public SpacesItemDecoration right(int value) {
        this.right = value;
        return this;
    }

    public SpacesItemDecoration bottom(int value) {
        this.bottom = value;
        return this;
    }

    public SpacesItemDecoration top(int value) {
        this.top = value;
        return this;
    }

    public SpacesItemDecoration setColor(int color) {
        this.color = color;
        return this;
    }

    @Override
    public void getItemOffsets(
            Rect outRect,View view,RecyclerView parent,RecyclerView.State state
    )
    {
        outRect.left = left;
        outRect.right = right;
        outRect.bottom = bottom;
        outRect.top = top;
    }

    @Override
    public void onDraw(
            Canvas c,RecyclerView parent,RecyclerView.State state
    )
    {
        super.onDraw(c,parent,state);
        if (color > -1) {
            c.drawColor(color);
        }
    }


    @Override
    public void onDrawOver(
            Canvas c,RecyclerView parent,RecyclerView.State state
    )
    {
        super.onDrawOver(c,parent,state);
        if (color > -1) {
            c.drawColor(color);
        }
    }

}
