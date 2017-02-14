package org.bit.big.brother.m.k.s.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bit.big.brother.m.k.d.domain.LoginBean;
import org.bit.big.brother.m.k.s.FundsService;
import org.bit.big.brother.m.k.s.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FundsServiceImpl implements FundsService{

	private static Logger logger = LoggerFactory.getLogger(FundsServiceImpl.class);
	@Autowired
	private HttpService httpService;

	@Override
	public List<Map<String, String>> getFundsList(LoginBean lb) {
		
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		
		String doc = httpService.getResultDocuments(lb);
		logger.error(doc);
		return result;
	}

}
