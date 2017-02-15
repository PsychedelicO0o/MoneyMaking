package org.bit.big.brother.m.k.cap.t.test;

import static org.junit.Assert.*;

import org.bit.big.brother.m.k.cap.t.model.BinaryMatrix;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinaryMatrixTest {
	private  BinaryMatrix matrix;
	
	private static Logger logger = LoggerFactory.getLogger(BinaryMatrixTest.class);
	
	@Before
	public void setUp() {
		boolean[][] array = new boolean[2][3];
		
		matrix = BinaryMatrix.fromArray(array);
	}

	@Test
	public void testIsValid() {
		assertTrue(matrix.isValid());
	}

	@Test
	public void testGetWidth() {
		assertArrayEquals(new int[]{3}, new int[]{matrix.getWidth()});
	}

	@Test
	public void testGetHeight() {
		assertArrayEquals(new int[]{2}, new int[]{matrix.getHeight()});
	}

	@Test
	public void testGetValue() {
		assertFalse(matrix.getValue(0, 0));
	}

	@Test
	public void testSetTrue() {
		matrix.setTrue(0, 0);
		assertTrue(matrix.getValue(0, 0));
	}

	@Test
	public void testSetFalse() {
		matrix.setFalse(0, 0);
		assertFalse(matrix.getValue(0, 0));
	}

	@Test
	public void testScaleTo() {
		BinaryMatrix tmp = matrix.scaleTo(10, 10);
		assertArrayEquals(new int[]{10,10}, new int[]{tmp.getHeight(), tmp.getWidth()});
	}

	@Test
	public void testDump2bitmap() {
		
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		logger.error("====testToString====");
		logger.error(matrix.toString());
	}

	@Test
	public void testFromArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testFromImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testFromBlank() {
		logger.error("====testFromBlank====");
		BinaryMatrix b = BinaryMatrix.fromBlank(10, 10);
		logger.error(b.toString());
	}

}
