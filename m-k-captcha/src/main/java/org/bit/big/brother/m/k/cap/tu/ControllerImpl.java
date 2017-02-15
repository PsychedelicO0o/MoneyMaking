package org.bit.big.brother.m.k.cap.tu;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.bit.big.brother.m.k.cap.t.common.Constants;
import org.bit.big.brother.m.k.cap.t.model.BinaryMatrix;
import org.bit.big.brother.m.k.cap.t.svm.RobustPredict;
import org.bit.big.brother.m.k.cap.t.svm.Trainning;
import org.bit.big.brother.m.k.cap.tu.imgprocessor.GenericPreprocessor;
import org.bit.big.brother.m.k.cap.tu.imgprocessor.ICaptchaPreprocessor;
import org.bit.big.brother.m.k.cap.tu.imgseg.GenericSegment;
import org.bit.big.brother.m.k.cap.tu.imgseg.ISegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerImpl implements IController{
	
	private static Logger logger = LoggerFactory.getLogger(ControllerImpl.class);
	
	private ICaptchaPreprocessor preProcessor;
	private ISegment segProcessor;
	
	public ControllerImpl() {
		this.preProcessor = new GenericPreprocessor();
		this.segProcessor = new GenericSegment();
	}

	@Override
	public String predict(File f) {
		BufferedReader reader = null;
		String result = "";
		try {
			BufferedImage sourceImage = ImageIO.read(f);
			BinaryMatrix im = preprocess(sourceImage);
			List<BinaryMatrix> interList = split(im);
			
			RobustPredict.predict(interList);

			reader = new BufferedReader(new FileReader(new File(
						Constants.SVM_RESULT_FILE)));
			String buff = null;
				while ((buff = reader.readLine()) != null) {
					float tmp = Float.valueOf(buff);
					int value = (int)tmp;
					if (value >= 0 && value <=9){
						result += value + "";
					}
					else {
						char c = (char) (value + 87);
						result += c;
					}
					
				}
				logger.error(result);
		} catch (IOException e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ignored) {
				}
			}
		}
		return result;
	}


	@Override
	public File download() {
		return null;
	}

	@Override
	public BinaryMatrix preprocess(BufferedImage im) {
		return preProcessor.preprocess(im);
	}

	@Override
	public List<BinaryMatrix> split(BinaryMatrix im) {
		return segProcessor.seg2file(im, null);
	}

}
