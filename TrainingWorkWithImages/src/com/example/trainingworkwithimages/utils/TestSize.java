package com.example.trainingworkwithimages.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestSize {

	@Test
	public void testCreation() {
		Size size = new Size(1, 2);
		assertEquals(1, size.width);
		assertEquals(2, size.height);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalSize() {
		new Size(1, -2);
	}

}
