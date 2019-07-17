package com.evil.recycler.inface;

import android.graphics.Rect;

public interface OnPositionListener {
	void onLocation(int position,int spanIndex,Rect outRect);
	
	void onFirstLocation(int spanIndex,Rect outRect);
	
	void onLastLocation(int spanIndex,Rect outRect);
}
