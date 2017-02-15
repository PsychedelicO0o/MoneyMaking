package org.bit.big.brother.m.k.cap.t.svm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import org.bit.big.brother.m.k.cap.t.common.Constants;
import org.bit.big.brother.m.k.cap.t.model.BinaryMatrix;
import org.bit.big.brother.m.k.cap.tu.imgprocessor.GenericPreprocessor;
import org.bit.big.brother.m.k.cap.tu.imgseg.GenericSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Trainning {
	private static final String PROCESSEDIMGS = "D:/tmpImg/seg/";
	private static final String RAW_CHAR_DIR = "D:/tmpImg/";
	
	private static Logger logger = LoggerFactory.getLogger(Trainning.class);
	
	public static void batchPreProcess() {
		File dir = new File(RAW_CHAR_DIR);
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.toLowerCase().endsWith(".jpg")){   
				      return true;   
				    }else{   
				      return false;   
				    } 
			}
		});
		
		GenericPreprocessor preProcessor = new GenericPreprocessor();
		GenericSegment segProcessor = new GenericSegment();
		
		for (File f : files) {
			String fileName = f.getName().split("\\.")[0];
				
			BufferedImage image = null;
			try {
				image = ImageIO.read(f);
				BinaryMatrix bm = preProcessor.preprocess(image);
				segProcessor.seg2file(bm, fileName, PROCESSEDIMGS);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void createTraData()  {
		File dir = new File(PROCESSEDIMGS);
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.toLowerCase().endsWith(".png")){   
				      return true;   
				    }else{   
				      return false;   
				    } 
			}
		});
		PrintWriter writer;
		try {
			writer = new PrintWriter(new File(Constants.SVM_TRANS_FILE));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}
		for (File f : files) {
			BufferedImage image;
			BinaryMatrix bm;
			try {
				image = ImageIO.read(f);
				bm = BinaryMatrix.fromImage(image);
				
			} catch (IOException e) {
				logger.error("IOException,skip:" + f.getName());
				continue;
			}
			char valueName = f.getName().charAt(0);
			int value;
			if (valueName >= 'a' && valueName <= 'z') {
				value = valueName - 87;
			}
			else if (valueName >= '0' && valueName <= '9') {
				value = valueName - '0';
			}
			else {
				logger.error("bad filename,skip:" + f.getName());
				continue;
			}
			StringBuilder sb = new StringBuilder();
			sb.append(value);
			sb.append(' ');
			int w = bm.getWidth();
			int h = bm.getHeight();
			int count = 1;
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					//黑色点标记为1
					sb.append(count);
					sb.append(':');
					if (bm.getValue(x, y)) {
						sb.append('1');
					}else {
						sb.append('0');
					}
					sb.append(' ');
					count++;
				}
			}
			sb.append("\r\n");
			writer.write(sb.toString());
			//System.out.println(sb.toString());
		}
		writer.flush();
		writer.close();
	}

	public static void startTrainning() {
		String[] arg = {"-t","0",Constants.SVM_TRANS_FILE, Constants.SVM_MODEL_FILE};
		
		//predict参数
		//String[] parg = {"svm/svmscale.test","svm/svm.model","svm/result.txt"};
		
		logger.error("训练开始");
		try {
			SVMTrain.main(arg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.error("训练结束");
	}
	public static void main(String[] args) {
			//batchPreProcess();
		createTraData();
		startTrainning();
		
	}

}
