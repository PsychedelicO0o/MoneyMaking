package org.bit.big.brother.m.k.cap.tu.imgseg;

import java.util.List;

import org.bit.big.brother.m.k.cap.t.model.BinaryMatrix;

import com.sun.istack.internal.Nullable;

@SuppressWarnings("restriction")
public interface ISegment {
	
	public List<BinaryMatrix> seg(BinaryMatrix im);
	
	public List<BinaryMatrix> seg2file(BinaryMatrix im, @Nullable String prefix); 

}
