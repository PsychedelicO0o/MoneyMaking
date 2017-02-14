package org.bit.big.brother.m.k.s.impl;

import org.bit.big.brother.m.k.c.util.HttpClientUtil;
import org.bit.big.brother.m.k.d.domain.LoginBean;
import org.bit.big.brother.m.k.s.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpServiceImpl implements HttpService{

	@Autowired
    private HttpClientUtil httpClientUtil;
	@Value("${big.brother.funds.url.beijing}")
	private String baseUrl;
	
	@Override
	public String getResultDocuments(LoginBean lb) {

		return httpClientUtil.post(baseUrl, lb);
	}

}
