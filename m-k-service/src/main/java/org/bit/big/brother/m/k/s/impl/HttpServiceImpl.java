package org.bit.big.brother.m.k.s.impl;

import org.bit.big.brother.m.k.c.util.HttpClientUtil;
import org.bit.big.brother.m.k.d.domain.LoginBean;
import org.bit.big.brother.m.k.s.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpServiceImpl implements HttpService{

	private static Logger logger = LoggerFactory.getLogger(HttpServiceImpl.class);
	@Autowired
    private HttpClientUtil httpClientUtil;
	@Value("${big.brother.funds.cookie.url.beijing}")
	private String cookieUrl;
	@Value("${big.brother.funds.inclk.url.beijing}")
	private String lkIncUrl;
	@Value("${big.brother.funds.login.url.beijing}")
	private String loginUrl;
	@Value("${big.brother.funds.img.url.beijing}")
	private String imgUrl;
	
	@Override
	public String getResultDocuments(LoginBean lb) {

		String reStr = null;
		try{
			reStr = httpClientUtil.post(cookieUrl,lkIncUrl,imgUrl,loginUrl, lb);
		}catch(Exception e){
			logger.error("get result documents error",e);
		}
		return reStr;
	}

}
