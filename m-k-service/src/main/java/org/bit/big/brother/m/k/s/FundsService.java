package org.bit.big.brother.m.k.s;

import java.util.List;
import java.util.Map;

import org.bit.big.brother.m.k.d.domain.LoginBean;

public interface FundsService {

	List<Map<String,String>> getFundsList(LoginBean lb);
}
