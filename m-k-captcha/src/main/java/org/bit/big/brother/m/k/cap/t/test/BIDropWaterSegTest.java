package org.bit.big.brother.m.k.cap.t.test;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.bit.big.brother.m.k.cap.t.model.BinaryMatrix;
import org.bit.big.brother.m.k.cap.tu.imgprocessor.GenericPreprocessor;
import org.bit.big.brother.m.k.cap.tu.imgseg.BIDropWaterSeg;
import org.bit.big.brother.m.k.cap.tu.imgseg.ColorFillSeg;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BIDropWaterSegTest {
	private List<BinaryMatrix> lb;
	private static Logger logger = LoggerFactory.getLogger(BIDropWaterSegTest.class);

	@Before
	public void setUp() throws Exception {
		File f = new File("download/58.jpg");

		BufferedImage img = null;
		img = ImageIO.read(f);
		GenericPreprocessor g = new GenericPreprocessor();
		BinaryMatrix b = g.preprocess(img);
		lb = ColorFillSeg.cfs(b);
		
	}
	
	@Test
	public void testTrim() {
		for (BinaryMatrix b : lb) {
			for (BinaryMatrix item : BIDropWaterSeg.seg(b)) {
				logger.error(item.trim().toString());
			}
		}
	}
	
//	@Test
//	public void testFind() {
//		for (BinaryMatrix b : lb) {
//			int[] starts = BIDropWaterSeg.find(b);
//			for (int i : starts) {
//				System.out.println(i+" ");
//			}
//			System.out.println();
//		}
//	}
//
//	@Test
//	public void testDrop() {
//		for (BinaryMatrix b : lb) {
//			int[] starts = BIDropWaterSeg.find(b);
//			for (int i : starts) {
//				int[] xs = BIDropWaterSeg.drop(i, b);
//				for (int j : xs) {
//					System.out.println(j+" ");
//				}
//				System.out.println();
//			}
//		}
//	}
	
	@Test
	public void testSeg() {
		for (BinaryMatrix b : lb) {
			for (BinaryMatrix item : BIDropWaterSeg.seg(b)) {
				logger.error(item.toString());
			}
		}
	}


}
