package com.evil.helper.recycler.menu;

import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class MenuDragHelper implements IMenuDrag {
	public float downX, downY;//按下的x y
	public ViewDragHelper viewDragHelper;
	private FrameLayout mMenuDragLayout;
	public View contentView;
	public View dragView;
	public int contentHeight;
	public int contentWidth;
	public int deleteWidth;
	public float moveX;
	public float moveY;
	public float downIX;
	public float downIY;
	
	//默认状态是关闭
	private MenuDragLayout.State state = MenuDragLayout.State.close;
	
	private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
		
		@Override
		public boolean tryCaptureView(View child,int pointerId) {
			return child == contentView || child == dragView;
		}
		
		@Override
		public void onViewDragStateChanged(int state) {
			super.onViewDragStateChanged(state);
		}
		
		@Override
		public void onViewPositionChanged(View changedView,int left,int top,int dx,int dy) {
			super.onViewPositionChanged(changedView,left,top,dx,dy);
			/**伴随移动**/
			if (changedView == contentView) {
				dragView.layout(dragView.getLeft() + dx,dragView.getTop() + dy,
				                dragView.getRight() + dx,dragView.getBottom() + dy);
			}
			else if (changedView == dragView) {
				contentView.layout(contentView.getLeft() + dx,contentView.getTop() + dy,
				                   contentView.getRight() + dx,contentView.getBottom() + dy);
			}
			
			//如果 item getLeft不等于0 就认为item已经打开 不允许其它item滑动
			if (contentView.getLeft() != 0) {
				SwipeMenuManager.getInstance().setMenuDragLayout((IMenuDrag)mMenuDragLayout);
			}
			else {
				SwipeMenuManager.getInstance().clear();
			}
			
			if (contentView.getLeft() == 0 && state != MenuDragLayout.State.close) {
				state = MenuDragLayout.State.close;
			}
			else if (contentView.getLeft() == -deleteWidth && state != MenuDragLayout.State.open) {
				state = MenuDragLayout.State.open;
			}
		}
		
		@Override
		public int getViewHorizontalDragRange(View child) {
			return deleteWidth;
		}
		
		@Override
		public int clampViewPositionHorizontal(View child,int left,int dx) {
			//限定滑动的范围
			if (child == contentView) {
				if (left > 0) {
					left = 0;
				}
				if (left < -deleteWidth) {
					left = -deleteWidth;
				}
			}
			else if (child == dragView) {
				if (left > contentWidth) {
					left = contentWidth;
				}
				if (left < contentWidth - deleteWidth) {
					left = contentWidth - deleteWidth;
				}
			}
			return left;
		}
		
		@Override
		public void onViewReleased(View releasedChild,float xvel,float yvel) {
			super.onViewReleased(releasedChild,xvel,yvel);
			//当速度达到一定的值的时候 直接打开或者关闭
			if (xvel < -2000) {
				((IMenuDrag)mMenuDragLayout).open();
				return;
			}
			if (xvel > 2000) {
				((IMenuDrag)mMenuDragLayout).close();
				return;
			}
			if (contentView.getLeft() > -deleteWidth / 2) {
				((IMenuDrag)mMenuDragLayout).close();
			}
			else {
				((IMenuDrag)mMenuDragLayout).open();
			}
		}
	};
	
	public MenuDragHelper(FrameLayout layout) {
		mMenuDragLayout = layout;
		viewDragHelper = ViewDragHelper.create(layout,1.0f,callback);
	}
	
	@Override
	public void open() {
		viewDragHelper.smoothSlideViewTo(contentView,-deleteWidth,contentView.getTop());
		state = MenuDragLayout.State.open;
		SwipeMenuManager.getInstance().setMenuDragLayout((IMenuDrag)mMenuDragLayout);
		ViewCompat.postInvalidateOnAnimation(mMenuDragLayout);
	}
	
	@Override
	public void close() {
		viewDragHelper.smoothSlideViewTo(contentView,0,contentView.getTop());
		ViewCompat.postInvalidateOnAnimation(mMenuDragLayout);
	}
	
	@Override
	public void setContentView(View contentView) {
		this.contentView = contentView;
	}
	
	@Override
	public void setDragView(View dragView) {
		this.dragView = dragView;
	}
	
	@Override
	public void onDetachedFromWindow() {
		if (SwipeMenuManager.getInstance().haveOpened((IMenuDrag)mMenuDragLayout)) {
			SwipeMenuManager.getInstance().close();
			SwipeMenuManager.getInstance().clear();
			state = MenuDragLayout.State.close;
		}
	}
	
	@Override
	public void computeScroll() {
		if (viewDragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(mMenuDragLayout);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//如果已经有item打开 不允许父控件拦截事件
		if (SwipeMenuManager.getInstance().haveOpened((IMenuDrag)mMenuDragLayout)) {
			//如果触摸的是打开的item 让viewDragHelper处理触摸事件
			//请求父控件不要拦截事件
			mMenuDragLayout.requestDisallowInterceptTouchEvent(true);
		}
		else if (SwipeMenuManager.getInstance().haveOpened()) {
			//如果触摸的不是当前打开的item 直接消耗事件 不让item滑动
			mMenuDragLayout.requestDisallowInterceptTouchEvent(true);
			return true;
		}
		
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downX = event.getX();
				downY = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				float moveX = event.getX();
				float moveY = event.getY();
				float dx = Math.abs(moveX - downX);
				float dy = Math.abs(moveY - downY);
				if (dx > dy) {
					//如果 x距离大于y 则不允许父控件拦截事件
					mMenuDragLayout.requestDisallowInterceptTouchEvent(true);
				}
				downX = moveX;
				downY = moveY;
				break;
		}
		viewDragHelper.processTouchEvent(event);
		return true;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		boolean value = viewDragHelper.shouldInterceptTouchEvent(event);
		if (!SwipeMenuManager.getInstance().haveOpened((IMenuDrag)mMenuDragLayout)) {
			//如果打开的不是当前的item 关闭
			SwipeMenuManager.getInstance().close();
		}
		
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downIX = event.getX();
				downIY = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				moveX = event.getX();
				moveY = event.getY();
				//如果 不是点击事件 拦截事件 这里判断dx dy >1就是判断是否是点击事件
				if (Math.abs(moveX - downIX) > 1 || Math.abs(moveY - downIY) > 1) {
					value = true;
				}
				break;
		}
		return value;
	}
	
	@Override
	public void onSizeChanged(int w,int h,int oldw,int oldh) {
		if (contentView != null) {
			contentHeight = contentView.getMeasuredHeight();
			contentWidth = contentView.getMeasuredWidth();
		}
		if (dragView != null) {
			deleteWidth = dragView.getMeasuredWidth();
		}
	}
	
	@Override
	public void onLayout(boolean changed,int left,int top,int right,int bottom) {
		if (contentView != null) {
			contentView.layout(0,0,contentWidth,contentHeight);
		}
		if (dragView != null) {
			dragView.layout(contentWidth,0,contentWidth + deleteWidth,contentHeight);
		}
	}
	
	@Override
	public void onFinishInflate() {
		contentView = mMenuDragLayout.getChildAt(0);
		dragView = mMenuDragLayout.getChildAt(1);
	}
}
