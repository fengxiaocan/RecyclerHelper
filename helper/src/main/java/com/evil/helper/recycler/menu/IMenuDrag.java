package com.evil.helper.recycler.menu;

import android.view.MotionEvent;
import android.view.View;

public interface IMenuDrag {
	void open();
	
	void close();
	
	void setContentView(View contentView);
	
	void setDragView(View dragView);
	
	void onDetachedFromWindow();
	
	void computeScroll();
	
	boolean onTouchEvent(MotionEvent event);
	
	boolean onInterceptTouchEvent(MotionEvent event);
	
	void onSizeChanged(int w,int h,int oldw,int oldh);
	
	void onLayout(boolean changed,int left,int top,int right,int bottom);
	
	void onFinishInflate();
	
	enum State {
		close,
		open
	}
	
}
