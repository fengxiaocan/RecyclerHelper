package com.evil.helper.recycler.inface;

import android.graphics.Rect;

public interface OnPositionListener {
	void onLocation(int position,Rect outRect);
	
	void onFirstLocation(Rect outRect);
	
	void onLastLocation(Rect outRect);
}
