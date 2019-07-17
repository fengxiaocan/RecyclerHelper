package com.evil.recycler.decoration;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;

public class RecyclerDividerProps {
    private Drawable drawable;
    private Paint mPaint;
    private int height;
    private int marginLeft;
    private int marginRight;
    private int marginTop;
    private int marginBottom;
    private int type;
    private boolean isSetType;

    public static Builder with(@ColorInt int color) {
        return new Builder(color);
    }

    public static Builder with(Drawable drawable) {
        return new Builder(drawable);
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public int getHeight() {
        return height;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public int getType() {
        return type;
    }

    public boolean isSetType() {
        return isSetType;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public static final class Builder {
        private int height = 2;
        private int type;
        private Paint paint;
        private Drawable drawable;
        private boolean isSetType = false;
        private int marginLeft;
        private int marginRight;
        private int marginTop;
        private int marginBottom;

        private Builder(int color) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
        }

        private Builder(Drawable drawable) {
            this.drawable = drawable;
            this.height = drawable.getIntrinsicHeight();
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder marginRight(int marginRight) {
            this.marginRight = marginRight;
            return this;
        }

        public Builder marginLeft(int marginLeft) {
            this.marginLeft = marginLeft;
            return this;
        }

        public Builder marginTop(int marginTop) {
            this.marginTop = marginTop;
            return this;
        }

        public Builder marginBottom(int marginBottom) {
            this.marginBottom = marginBottom;
            return this;
        }

        public Builder type(int type) {
            this.type = type;
            this.isSetType = true;
            return this;
        }

        public RecyclerDividerProps build() {
            RecyclerDividerProps recyclerDividerProps = new RecyclerDividerProps();
            recyclerDividerProps.isSetType = this.isSetType;
            recyclerDividerProps.type = this.type;
            recyclerDividerProps.height = this.height;
            recyclerDividerProps.drawable = this.drawable;
            recyclerDividerProps.marginLeft = this.marginLeft;
            recyclerDividerProps.marginRight = this.marginRight;
            recyclerDividerProps.marginTop = this.marginTop;
            recyclerDividerProps.marginBottom = this.marginBottom;
            recyclerDividerProps.mPaint = this.paint;
            return recyclerDividerProps;
        }
    }
}
