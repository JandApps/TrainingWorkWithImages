package com.example.trainingworkwithimages.utils;

import java.util.Random;

public class GameFill {
	private static Dimension dim = new Dimension(6, 4);
	private static int[][] gameFill = new int[dim.rows][dim.columns];

	public static int[][] getGameFill() {
		return gameFill;
	}

	public static int[][] generateRandomGameMatr() {
		gameFill = new int[dim.rows][dim.columns];

		for (int i = 0; i < dim.rows; ++i) {
			for (int j = 0; j < dim.columns; ++j) {
				gameFill[i][j] = i * dim.columns + j;
			}
		}

		swap(gameFill);

		return gameFill;
	}

	private static void swap(int[][] a) {
		Random r = new Random(System.nanoTime());
		int t = 0;
		int rowOne = 0;
		int columnOne = 0;
		int rowTwo = 0;
		int columnTwo = 0;

		for (int i = 0; i < a.length; ++i) {
			for (int j = 0; j < a[i].length; ++j) {
				rowOne = r.nextInt(dim.rows);
				columnOne = r.nextInt(dim.columns);

				rowTwo = r.nextInt(dim.rows);
				columnTwo = r.nextInt(dim.columns);

				t = a[rowOne][columnOne];
				a[rowOne][columnOne] = a[rowTwo][columnTwo];
				a[rowTwo][columnTwo] = t;

			}
		}
	}

	public static boolean ended() {
		boolean end = false;
		
		for (int row = 0; row < dim.rows; ++row) {
			for (int column = 0; column < dim.columns; ++column) {
				if(gameFill[row][column] == row * dim.rows + column){
					end = true;
				} else {
					return false;
				}
			}
		}
		
		return end;
	}
}
