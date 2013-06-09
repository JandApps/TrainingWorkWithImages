package com.example.trainingworkwithimages;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SizeableLayout extends RelativeLayout {

	private int width;
	private int height;

	public SizeableLayout(Context context) {
		super(context);
	}

	public SizeableLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SizeableLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		this.width = Math.abs(r - l);
		this.height = Math.abs(b - t);
	}
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}

}
