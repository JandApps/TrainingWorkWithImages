package com.example.trainingworkwithimages;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class CustomDrawView extends View {

	private final int LATTICE_WIDTH = 2;
	private Dimension dim = new Dimension(4, 2);
	private Matrix<Bitmap> puzzles = new Matrix<Bitmap>(dim);
	private Bitmap bitmap = null;
	private int puzzleHeight;
	private int puzzleWidth;
	private int screenWidth;
	private int screenHeight;

	public CustomDrawView(Context context) {
		super(context);
	}

	public CustomDrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	

	public CustomDrawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void setDimension(Dimension dim) {
		this.dim = dim;
	}
	
	public void setBitmap(Bitmap bitmap) {
		calculateSizes();
		this.bitmap = scaleBitmap(bitmap);
		fillPuzzles();
		invalidate();
	}
	
	private void calculateSizes() {
		int width = getWidth() - LATTICE_WIDTH * (dim.columns - 1);
		int height = getHeight() - LATTICE_WIDTH * (dim.rows - 1);
		this.puzzleWidth = width / dim.columns;
		this.puzzleHeight = height / dim.rows;
		this.screenWidth = puzzleWidth * dim.columns;
		this.screenHeight = puzzleHeight * dim.rows;
	}
	
	private Bitmap scaleBitmap(Bitmap bitmap) {
		return Bitmap.createScaledBitmap(bitmap, screenWidth, screenHeight, true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (bitmap != null) {
			drawPuzzles(canvas);
		}
	}
	
	private void drawPuzzles(Canvas canvas) {
		for (int row = 0; row < dim.rows; ++row) {
			for (int column = 0; column < dim.columns; ++column) {
				int x = (puzzleWidth + LATTICE_WIDTH) * column;
				int y = (puzzleHeight + LATTICE_WIDTH) * row;
				canvas.drawBitmap(puzzles.get(row, column), x, y, null);
			}
		}
	}

	private void fillPuzzles() {
		for (int row = 0; row < dim.rows; ++row) {
			for (int column = 0; column < dim.columns; ++column) {
				Bitmap puzzle = puzzle(bitmap, row, column);
				puzzles.set(row, column, puzzle);
			}
		}
	}
	
	private Bitmap puzzle(Bitmap bitmap, int row, int column) {
		int x = column * puzzleWidth;
		int y = row * puzzleHeight;
		return Bitmap.createBitmap(bitmap, x, y, puzzleWidth, puzzleHeight);
	}
}
