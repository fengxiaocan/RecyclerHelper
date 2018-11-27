package com.evil.helper.recycler.decoration;

import com.evil.helper.recycler.inface.OnPositionListener;

public class AroundDecorationProps {
	
	private int leftSpace; // 左边间距
	private int rightSpace; // 右边间距
	private int topSpace; // 上边间距
	private int bottomSpace; // 下边间距
	private OnPositionListener mOnPositionListener;
	
	public OnPositionListener getOnPositionListener() {
		return mOnPositionListener;
	}
	
	public void setOnPositionListener(OnPositionListener onPositionListener) {
		mOnPositionListener = onPositionListener;
	}
	
	public int getLeftSpace() {
		return leftSpace;
	}
	
	public void setLeftSpace(int leftSpace) {
		this.leftSpace = leftSpace;
	}
	
	public int getRightSpace() {
		return rightSpace;
	}
	
	public void setRightSpace(int rightSpace) {
		this.rightSpace = rightSpace;
	}
	
	public int getTopSpace() {
		return topSpace;
	}
	
	public void setTopSpace(int topSpace) {
		this.topSpace = topSpace;
	}
	
	public int getBottomSpace() {
		return bottomSpace;
	}
	
	public void setBottomSpace(int bottomSpace) {
		this.bottomSpace = bottomSpace;
	}
	
	private AroundDecorationProps(Builder builder) {
		leftSpace = builder.leftSpace;
		rightSpace = builder.rightSpace;
		topSpace = builder.topSpace;
		bottomSpace = builder.bottomSpace;
		mOnPositionListener = builder.mOnPositionListener;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static final class Builder {
		private int leftSpace;
		private int rightSpace;
		private int topSpace;
		private int bottomSpace;
		private OnPositionListener mOnPositionListener;
		
		private Builder() {}
		
		public Builder leftSpace(int val) {
			leftSpace = val;
			return this;
		}
		
		public Builder listener(OnPositionListener val) {
			mOnPositionListener = val;
			return this;
		}
		
		public Builder space(int val) {
			leftSpace = val;
			rightSpace = val;
			topSpace = val;
			bottomSpace = val;
			return this;
		}
		
		public Builder rightSpace(int val) {
			rightSpace = val;
			return this;
		}
		
		public Builder topSpace(int val) {
			topSpace = val;
			return this;
		}
		
		public Builder bottomSpace(int val) {
			bottomSpace = val;
			return this;
		}
		
		public AroundDecorationProps build() {
			return new AroundDecorationProps(this);
		}
	}
}
