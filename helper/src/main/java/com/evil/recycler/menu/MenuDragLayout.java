package com.evil.recycler.menu;

import android.content.Context;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/6/18
 * @desc 一个RecyclerView的右侧滑菜单
 */
public class MenuDragLayout extends FrameLayout implements IMenuDragView {
	MenuDragHelper mMenuDragHelper;
	
	public MenuDragLayout( Context context) {
		super(context);
		mMenuDragHelper = new MenuDragHelper(this);
	}
	
	public MenuDragLayout(
			 Context context,@Nullable AttributeSet attrs)
	{
		super(context,attrs);
		mMenuDragHelper = new MenuDragHelper(this);
	}
	
	public MenuDragLayout( Context context,@Nullable AttributeSet attrs,int defStyleAttr) {
		super(context,attrs,defStyleAttr);
		mMenuDragHelper = new MenuDragHelper(this);
	}
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public MenuDragLayout(
			 Context context,@Nullable AttributeSet attrs,int defStyleAttr,int defStyleRes)
	{
		super(context,attrs,defStyleAttr,defStyleRes);
		mMenuDragHelper = new MenuDragHelper(this);
	}
	
	@Override
	public void open() {
		if (mMenuDragHelper != null) {
			mMenuDragHelper.open();
		}
	}
	
	@Override
	public void close() {
		if (mMenuDragHelper != null) {
			mMenuDragHelper.close();
		}
	}
	
	@Override
	public void setContentView(View contentView) {
		if (mMenuDragHelper != null) {
			mMenuDragHelper.setContentView(contentView);
		}
	}
	
	@Override
	public void setDragView(View dragView) {
		if (mMenuDragHelper != null) {
			mMenuDragHelper.setDragView(dragView);
		}
	}
	
	@Override
	public void onDetachedFromWindow() {
		if (mMenuDragHelper != null) {
			mMenuDragHelper.onDetachedFromWindow();
		}
		super.onDetachedFromWindow();
	}
	
	@Override
	public void computeScroll() {
		if (mMenuDragHelper != null) {
			mMenuDragHelper.computeScroll();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mMenuDragHelper != null) {
			return mMenuDragHelper.onTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (mMenuDragHelper != null) { return mMenuDragHelper.onInterceptTouchEvent(event); }
		return super.onInterceptTouchEvent(event);
	}
	
	@Override
	public void onSizeChanged(int w,int h,int oldw,int oldh) {
		super.onSizeChanged(w,h,oldw,oldh);
		if (mMenuDragHelper != null) { mMenuDragHelper.onSizeChanged(w,h,oldw,oldh); }
	}
	
	@Override
	public void onLayout(boolean changed,int left,int top,int right,int bottom) {
		super.onLayout(changed,left,top,right,bottom);
		if (mMenuDragHelper != null) { mMenuDragHelper.onLayout(changed,left,top,right,bottom); }
	}
	
	@Override
	public void onFinishInflate() {
		super.onFinishInflate();
		if (mMenuDragHelper != null) { mMenuDragHelper.onFinishInflate(); }
	}
}
