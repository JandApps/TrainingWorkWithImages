package com.example.trainingworkwithimages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestDimesion {

	@Test
	public void testCreation() {
		final int rows = 1;
		final int columns = 2;
		Dimension dim = new Dimension(rows, columns);
		assertEquals(rows, dim.rows);
		assertEquals(columns, dim.columns);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRowsIsZero() {
		new Dimension(0, 1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRowsIsNegative() {
		new Dimension(-1, 1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testColumnsIsZero() {
		new Dimension(1, 0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testColumnsIsNegative() {
		new Dimension(1, -1);
	}

}
