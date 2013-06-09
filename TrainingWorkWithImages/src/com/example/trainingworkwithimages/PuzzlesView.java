package com.example.trainingworkwithimages;

import com.example.trainingworkwithimages.utils.Dimension;
import com.example.trainingworkwithimages.utils.Matrix;
import com.example.trainingworkwithimages.utils.Size;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class PuzzlesView extends View {

	private final int LATTICE_WIDTH = 1;
	private Dimension dim = new Dimension(6, 4);
	private Matrix<Bitmap> puzzles = new Matrix<Bitmap>(dim);
	private Bitmap fullImage = null;
	private Size puzzleSize;
	private Size fullImageSize;
	private Point lastTouch;
	private Point touchedLeftUpper;
	private int touchedRow = -1;
	private int touchedColumn = -1;

	public PuzzlesView(Context context) {
		super(context);
	}

	public PuzzlesView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	

	public PuzzlesView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void setBitmap(Bitmap bitmap, Dimension dim) {
		this.dim = dim;
		setBitmap(bitmap);
	}
	
	public void setBitmap(Bitmap bitmap) {
		calculateSizes();
		this.fullImage = scaleBitmap(bitmap);
		fillPuzzles();
		invalidate();
	}
	
	private void calculateSizes() {
		int width = getWidth() - LATTICE_WIDTH * (dim.columns - 1);
		int height = getHeight() - LATTICE_WIDTH * (dim.rows - 1);
		puzzleSize = new Size(width / dim.columns, height / dim.rows);
		fullImageSize = new Size(puzzleSize.width * dim.columns, puzzleSize.height * dim.rows);
	}
	
	private Bitmap scaleBitmap(Bitmap bitmap) {
		return Bitmap.createScaledBitmap(bitmap, fullImageSize.width, fullImageSize.height, true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (fullImage != null) {
			drawPuzzles(canvas);
		}
	}
	
	private void drawPuzzles(Canvas canvas) {
		for (int row = 0; row < dim.rows; ++row) {
			for (int column = 0; column < dim.columns; ++column) {
				if (!touchedPuzzlePosition(row, column)) {
					Point pt = leftUpperOfPuzzle(row, column);
					canvas.drawBitmap(puzzles.get(row, column), pt.x, pt.y, null);
				}
			}
		}
		if (existTouchedPuzzle()) {
			Bitmap touchedPuzzle = puzzles.get(touchedRow, touchedColumn);
			canvas.drawBitmap(touchedPuzzle, touchedLeftUpper.x, touchedLeftUpper.y, null);
		}
	}
	
	private Point leftUpperOfPuzzle(int row, int column) {
		int x = (puzzleSize.width + LATTICE_WIDTH) * column;
		int y = (puzzleSize.height + LATTICE_WIDTH) * row;
		return new Point(x, y);
	}

	private boolean touchedPuzzlePosition(int row, int column) {
		return row == touchedRow && column == touchedColumn;
	}
	
	@SuppressWarnings("unused")
	private Paint colorFilterPaint() {
		Paint p = new Paint(Color.BLUE);
		ColorFilter filter = new LightingColorFilter(Color.argb(120, 20, 60, 250), 1);
		p.setColorFilter(filter);
		return p;
	}

	private void fillPuzzles() {
		for (int row = 0; row < dim.rows; ++row) {
			for (int column = 0; column < dim.columns; ++column) {
				Bitmap puzzle = puzzle(fullImage, row, column);
				puzzles.set(row, column, puzzle);
			}
		}
	}
	
	private Bitmap puzzle(Bitmap bitmap, int row, int column) {
		int x = column * puzzleSize.width;
		int y = row * puzzleSize.height;
		return Bitmap.createBitmap(bitmap, x, y, puzzleSize.width, puzzleSize.height);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			onDownTouch(event);
			break;
			
		case MotionEvent.ACTION_MOVE:
			onMoveTouch(event);
			break;
			
		case MotionEvent.ACTION_UP:
			onUpTouch(event);
		}
		return true;
	}

	private void onDownTouch(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		int column = x / (puzzleSize.width + LATTICE_WIDTH);
		int row = y / (puzzleSize.height + LATTICE_WIDTH);
		if (row < dim.rows && column < dim.columns && insidePuzzle(x, y)) {
			lastTouch = new Point(x, y);
			touchedLeftUpper = leftUpperOfPuzzle(row, column);
			setTouchedPuzzlePosition(row, column);
			Log.d("leonidandand", "row: " + row + "; column: " + column);
		}
	}

	private boolean insidePuzzle(int x, int y) {
		return ((x % (puzzleSize.width + LATTICE_WIDTH)) < puzzleSize.width) &&
			   ((y % (puzzleSize.height + LATTICE_WIDTH)) < puzzleSize.height);
	}

	private void setTouchedPuzzlePosition(int row, int column) {
		touchedRow = row;
		touchedColumn = column;
	}

	private void onMoveTouch(MotionEvent event) {
		if (existTouchedPuzzle()) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			int dx = x - lastTouch.x;
			int dy = y - lastTouch.y;
			touchedLeftUpper = new Point(touchedLeftUpper.x + dx, touchedLeftUpper.y + dy);
			lastTouch = new Point(x, y);
			invalidate();
		}
	}

	private boolean existTouchedPuzzle() {
		return touchedRow != -1 && touchedColumn != -1;
	}

	private void onUpTouch(MotionEvent event) {
		if (existTouchedPuzzle()) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			int column = x / (puzzleSize.width + LATTICE_WIDTH);
			int row = y / (puzzleSize.height + LATTICE_WIDTH);
			if (row < dim.rows && column < dim.columns && insidePuzzle(x, y)) {
				swapWithTouchedPuzzle(row, column);
			}
			touchedRow = -1;
			touchedColumn = -1;
			invalidate();
		}
	}

	private void swapWithTouchedPuzzle(int row, int column) {
		Bitmap temp = puzzles.get(row, column);
		Bitmap touchedPuzzle = puzzles.get(touchedRow, touchedColumn);
		puzzles.set(row, column, touchedPuzzle);
		puzzles.set(touchedRow, touchedColumn, temp);
	}
}
