package com.example.trainingworkwithimages;


import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private final static int[] imageIds = new int[] {
		R.drawable.woman, R.drawable.woman2, R.drawable.google, R.drawable.ic_launcher
	};
	
	private Random random = new Random();

	private CustomDrawView drawer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		drawer = new CustomDrawView(this);
		setContentView(drawer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.showImage:
			int imageId = imageIds[random.nextInt(imageIds.length)];
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageId);
			drawer.setBitmap(bitmap);
			break;
		}
		return true;
	}

}
