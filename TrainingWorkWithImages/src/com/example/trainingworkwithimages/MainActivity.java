package com.example.trainingworkwithimages;


import java.util.Random;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private final static int imageId = R.drawable.google;
	
	private Dimension dimension;
	private SizeableLayout mainLayout;
	private Bitmap bitmap;
	private Matrix<Bitmap> puzzles;
	private Matrix<ImageView> imageViews;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			dimension = new Dimension(4, 2);
		} else {
			dimension = new Dimension(2, 4);
		}
		

		mainLayout = (SizeableLayout) findViewById(R.id.mainLayout);
		imageViews = new Matrix<ImageView>(dimension.rows, dimension.columns);
		bitmap = BitmapFactory.decodeResource(getResources(), imageId);
		puzzles = new Matrix<Bitmap>(dimension.rows, dimension.columns);
		for (int row = 0; row < dimension.rows; ++row) {
			for (int column = 0; column < dimension.columns; ++column) {
				Log.d("leonidandand", "row: " + row + " ;  column: " + column);
				ImageView imageView = new ImageView(this);
				imageView.setId(generateId());
				Bitmap puzzle = puzzle(bitmap, row, column);
				puzzles.set(row, column, puzzle);
				imageView.setImageBitmap(puzzle);
				imageViews.set(row, column, imageView);
				mainLayout.addView(imageView);
			}
		}
		for (int row = 0; row < dimension.rows; ++row) {
			for (int column = 0; column < dimension.columns; ++column) {
				setRelative(imageViews.get(row, column), row, column);
			}
		}
		
	}
	
	private void setRelative(ImageView view, int row, int column) {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		if (row == 0) {
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		} else {
			params.addRule(RelativeLayout.BELOW, imageViews.get(row - 1, column).getId());
		}
		if (row == dimension.rows - 1) {
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		}
		if (column == 0) {
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		} else {
			params.addRule(RelativeLayout.RIGHT_OF, imageViews.get(row, column - 1).getId());
		}
		if (column == dimension.columns - 1) {
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		}
	}
	
	private int generateId() {
		Random random = new Random();
		int id;
		do {
			id = random.nextInt();
		} while (findViewById(id) != null);
		return id;
	}

	@Override
	protected void onResume() {
		super.onResume();
		showPuzzles();
	}

	private void showPuzzles() {
		for (int row = 0; row < dimension.rows; ++row) {
			for (int column = 0; column < dimension.columns; ++column) {
				ImageView imageView = new ImageView(this);
			}
		}
	}

	private Bitmap puzzle(Bitmap bitmap, int row, int column) {
		int width = bitmap.getWidth() / dimension.columns;
		int height = bitmap.getHeight() / dimension.rows;
		int x = width * column;
		int y = height * row;
		return Bitmap.createBitmap(bitmap, x, y, width, height);
	}

}
