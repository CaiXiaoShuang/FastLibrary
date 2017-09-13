package com.intelligence.androidapplication.ui.navigation.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 取消页面滚动
 *
 */
public class NoSlidingViewPaper extends ViewPager {
	public NoSlidingViewPaper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public NoSlidingViewPaper(Context context) {
		super(context);
	}
	/*
	 * 表示把滑动事件传递给下一个view
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}
	/*
	 * 可以啥都不做
	 */
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}
}
