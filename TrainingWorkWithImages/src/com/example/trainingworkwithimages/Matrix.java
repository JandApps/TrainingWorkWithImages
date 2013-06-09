package com.example.trainingworkwithimages;

public class Matrix<T> {

	public final int rows;
	public final int columns;
	private T[] values;

	@SuppressWarnings("unchecked")
	public Matrix(int rows, int columns) {
		if (rows <= 0 || columns <= 0) {
			throw new IllegalArgumentException();
		}
		this.rows = rows;
		this.columns = columns;
		values = (T[]) new Object[rows * columns];
	}

	public void set(int row, int column, T value) {
		checkIndexes(row, column);
		values[index(row, column)] = value;
	}
	
	private void checkIndexes(int row, int column) {
		if (row < 0 || column < 0) {
			throw new IllegalArgumentException();
		}
		if (row >= rows || column >= columns) {
			throw new IndexOutOfBoundsException();
		}
	}
	
	private int index(int row, int column) {
		return row * columns + column;
	}

	public T get(int row, int column) {
		checkIndexes(row, column);
		return values[index(row, column)];
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Matrix<?>)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Matrix<T> other = (Matrix<T>) obj;
		if (other.rows != rows || other.columns != columns) {
			return false;
		}
		for (int row = 0; row < rows; ++row) {
			for (int column = 0; column < columns; ++column) {
				T elementOfThis = get(row, column);
				T elementOfOther = other.get(row, column);
				if (!elementOfThis.equals(elementOfOther)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hashCode1 = get(0, 0).hashCode();
		int hashCode2 = get(rows - 1, columns - 1).hashCode();
		int hashCode = hashCode1 * hashCode2;
		hashCode += Math.max(hashCode1, hashCode2) / Math.min(hashCode1, hashCode2);
		return hashCode;
		
	}

}
