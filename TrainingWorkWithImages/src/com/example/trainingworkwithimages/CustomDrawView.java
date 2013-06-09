package com.example.trainingworkwithimages;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class CustomDrawView extends View {

	private static final int imageId = R.drawable.google;
	private Bitmap bitmap = null;

	public CustomDrawView(Context context) {
		super(context);
	}

	public CustomDrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	

	public CustomDrawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = scaleBitmap(bitmap);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (bitmap != null) {
			canvas.drawBitmap(bitmap, 0, 0, null);
		}
	}
	
	private Bitmap scaleBitmap(Bitmap bitmap) {
		return Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), true);
	}
}
