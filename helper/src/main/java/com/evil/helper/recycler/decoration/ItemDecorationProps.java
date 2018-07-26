package com.evil.helper.recycler.decoration;

public class ItemDecorationProps {
    private int farFirstSpace;//最前边的间距
    private int farLastSpace;//最后的间距
    private int verticalSpace; // 纵向间距
    private int horizontalSpace; // 横向间距
    private boolean hasSetFarFirstEdge;//是否启用第一个的最左或最上设置
    private boolean hasSetFarLastEdge;//是否启用最后一个最右边或最下面设置
    private boolean hasVerticalEdge; // 是否给左右边缘设置间距
    private boolean hasHorizontalEdge; // 是否给上下边缘设置间距
    public boolean isHasSetFarFirstEdge() {
        return hasSetFarFirstEdge;
    }

    public void setHasSetFarFirstEdge(boolean hasSetFarFirstEdge) {
        this.hasSetFarFirstEdge = hasSetFarFirstEdge;
    }

    public boolean isHasSetFarLastEdge() {
        return hasSetFarLastEdge;
    }

    public void setHasSetFarLastEdge(boolean hasSetFarLastEdge) {
        this.hasSetFarLastEdge = hasSetFarLastEdge;
    }

    public int getFarFirstSpace() {
        return farFirstSpace;
    }

    public void setFarFirstSpace(int farFirstSpace) {
        this.farFirstSpace = farFirstSpace;
    }

    public int getFarLastSpace() {
        return farLastSpace;
    }

    public void setFarLastSpace(int farLastSpace) {
        this.farLastSpace = farLastSpace;
    }

    public int getHorizontalSpace() {
        return this.horizontalSpace;
    }

    public boolean isHasVerticalEdge() {
        return hasVerticalEdge;
    }

    public boolean isHasHorizontalEdge() {
        return hasHorizontalEdge;
    }

    public ItemDecorationProps setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
        return this;
    }

    public int getVerticalSpace() {
        return this.verticalSpace;
    }

    public ItemDecorationProps setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
        return this;
    }

    public boolean getHasHorizontalEdge() {
        return this.hasHorizontalEdge;
    }

    public ItemDecorationProps setHasHorizontalEdge(boolean hasHorizontalEdge) {
        this.hasHorizontalEdge = hasHorizontalEdge;
        return this;
    }

    public boolean getHasVerticalEdge() {
        return this.hasVerticalEdge;
    }

    public ItemDecorationProps setHasVerticalEdge(boolean hasVerticalEdge) {
        this.hasVerticalEdge = hasVerticalEdge;
        return this;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        ItemDecorationProps mProps;

        public Builder() {
            mProps = new ItemDecorationProps();
        }

        public Builder space(int value) {
            this.mProps.verticalSpace = value;
            this.mProps.horizontalSpace = value;
            this.mProps.hasVerticalEdge = true;
            this.mProps.hasHorizontalEdge = true;
            return this;
        }


        public Builder verticalSpace(int value) {
            this.mProps.verticalSpace = value;
            this.mProps.hasVerticalEdge = true;
            return this;
        }

        public Builder horizontalSpace(int value) {
            this.mProps.horizontalSpace = value;
            this.mProps.hasHorizontalEdge = true;
            return this;
        }

        public Builder horizontalEdge(boolean value) {
            this.mProps.hasHorizontalEdge = value;
            return this;
        }

        public Builder verticalEdge(boolean value) {
            this.mProps.hasVerticalEdge = value;
            return this;
        }

        public Builder firstSpace(int value) {
            this.mProps.farFirstSpace = value;
            this.mProps.hasSetFarFirstEdge = true;
            return this;
        }

        public Builder lastSpace(int value) {
            this.mProps.farLastSpace = value;
            this.mProps.hasSetFarLastEdge = true;
            return this;
        }

        public Builder farFirstEdge(boolean value) {
            this.mProps.hasSetFarFirstEdge = value;
            return this;
        }

        public Builder farLastEdge(boolean value) {
            this.mProps.hasSetFarLastEdge = value;
            return this;
        }

        public ItemDecorationProps build() {
            return this.mProps;
        }
    }
}
