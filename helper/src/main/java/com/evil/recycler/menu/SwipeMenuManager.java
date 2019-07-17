package com.evil.recycler.menu;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/6/18
 * @desc ...
 */
public class SwipeMenuManager {
	private static SwipeMenuManager sSwipeMenuManager = new SwipeMenuManager();
	private IMenuDrag swipeDeleteItem;
	
	private SwipeMenuManager() {
	}
	
	public static SwipeMenuManager getInstance() {
		return sSwipeMenuManager;
	}
	
	public void setMenuDragLayout(IMenuDrag s) {
		swipeDeleteItem = s;
	}
	
	public void clear() {
		swipeDeleteItem = null;
	}
	
	public void close() {
		if (swipeDeleteItem != null) {
			swipeDeleteItem.close();
		}
	}
	
	
	/**
	 * 是否有item已经打开
	 */
	public boolean haveOpened() {
		return swipeDeleteItem != null;
	}
	
	/**
	 * 是否有item已经打开
	 */
	public boolean haveOpened(IMenuDrag s) {
		//如果为空 表示没有打开的item
		return swipeDeleteItem != null && swipeDeleteItem == s;
		// true 表示 两个item不是同一个 有一个已经打开的item
	}
}
