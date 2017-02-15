package org.bit.big.brother.m.k.cap.tu;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import org.bit.big.brother.m.k.cap.t.model.BinaryMatrix;

public interface IController {
	
	public File download();
	
	public BinaryMatrix preprocess(BufferedImage im);
	
	public List<BinaryMatrix> split(BinaryMatrix im); 
	
	public String predict(File f);
	
	

}
