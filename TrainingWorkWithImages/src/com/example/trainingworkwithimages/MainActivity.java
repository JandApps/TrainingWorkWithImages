package com.example.trainingworkwithimages;

import java.util.Random;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private int currentImageId;
	private Random random = new Random();

	private final static int[] imageIds = { R.drawable.woman,
			R.drawable.woman2, R.drawable.google, R.drawable.cut,
			R.drawable.kote, R.drawable.pesik, R.drawable.pesik_2 };

	private PuzzlesView drawer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		drawer = new PuzzlesView(this);
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
			currentImageId = getRandomImageId();
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imageIds[currentImageId]);
			drawer.setBitmap(bitmap);
			break;
		}
		return true;
	}

	public int getRandomImageId() {		
		int nextId = random.nextInt(imageIds.length);
		return  (nextId != currentImageId ? nextId : getRandomImageId());
	}
}
