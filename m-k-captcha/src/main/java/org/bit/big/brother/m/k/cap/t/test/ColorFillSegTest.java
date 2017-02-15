package org.bit.big.brother.m.k.cap.t.test;

import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.bit.big.brother.m.k.cap.t.common.ImageCommons;
import org.bit.big.brother.m.k.cap.t.model.BinaryMatrix;
import org.bit.big.brother.m.k.cap.tu.imgprocessor.GenericPreprocessor;
import org.bit.big.brother.m.k.cap.tu.imgseg.ColorFillSeg;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorFillSegTest {

	private static Logger logger = LoggerFactory.getLogger(ColorFillSegTest.class);
	@Test
	public void testCfsBinaryMatrix() {
		File f = new File("download/58.jpg");

		BufferedImage img = null;
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			fail("载入失败");
		}
		GenericPreprocessor g = new GenericPreprocessor();
		BinaryMatrix b = g.preprocess(img);
		List<BinaryMatrix> lb = ColorFillSeg.cfs(b);
		
		for (BinaryMatrix item : lb) {
			logger.error(item.toString());
		}

	}

}
