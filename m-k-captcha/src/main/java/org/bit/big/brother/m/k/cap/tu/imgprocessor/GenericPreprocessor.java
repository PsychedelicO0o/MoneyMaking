package org.bit.big.brother.m.k.cap.tu.imgprocessor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bit.big.brother.m.k.cap.t.common.ImageCommons;
import org.bit.big.brother.m.k.cap.t.model.BinaryMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericPreprocessor implements ICaptchaPreprocessor {

	private static Logger logger = LoggerFactory.getLogger(GenericPreprocessor.class);
	
	@Override
	public BinaryMatrix preprocess(BufferedImage im) {
		double Wr = 0.299;
		double Wg = 0.587;
		double Wb = 0.114;
		
		int w = im.getWidth();
		int h = im.getHeight();
		int[][] gray = new int[w][h];
		
		//灰度化
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				Color color = new Color(im.getRGB(x, y));
				int rgb = (int) ((color.getRed()*Wr + color.getGreen()*Wg + color.getBlue()*Wb) / 3);
				gray[x][y] = rgb;
			}
		}
		
		BinaryMatrix res = BinaryMatrix.fromBlank(w, h);
		
		//二值化
		int threshold = ImageCommons.getOstu(gray, w, h);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (gray[x][y] > threshold) {
					res.setFalse(x, y);
				}else{
					res.setTrue(x, y);
				}
				
			}
		}
		
		return res;
	}
	



	public static void test() {
		File f = new File("download/46.jpg");
		try {
			BufferedImage img = ImageIO.read(f);
			GenericPreprocessor g = new GenericPreprocessor();
			logger.error(g.preprocess(img).toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		test();
		
	}
}
