package org.bit.big.brother.m.k.cap.t.svm;

import java.io.IOException;

import org.bit.big.brother.m.k.cap.t.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import libsvm.svm;
import libsvm.svm_model;

/**
 * ！使用前需确保生成数据svm/svm.model2
 * @author foo
 *
 */
public enum StaticSVM{
    INSTANCE;
  
    private svm_model model;
    

	private StaticSVM() {
		try {
			model = svm.svm_load_model(Constants.SVM_MODEL_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public svm_model getModel() {
		return model;
	}
    
}
