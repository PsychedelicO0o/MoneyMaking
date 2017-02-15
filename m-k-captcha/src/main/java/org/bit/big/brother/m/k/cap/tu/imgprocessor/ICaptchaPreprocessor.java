package org.bit.big.brother.m.k.cap.tu.imgprocessor;

import java.awt.image.BufferedImage;

import org.bit.big.brother.m.k.cap.t.model.BinaryMatrix;

/**
 * 验证码二值化
 * @author foo
 *
 */
public interface ICaptchaPreprocessor {

	/**
	 * 将图片转换为二维数组，true表示黑点，false表示白点
	 * @param im
	 * @return
	 */
	public BinaryMatrix preprocess(BufferedImage im);
	
}




