package org.bit.big.brother.m.k.w.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bit.big.brother.m.k.c.util.BeanUtil;
import org.bit.big.brother.m.k.d.domain.LoginBean;
import org.bit.big.brother.m.k.s.FundsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
@Controller
@RequestMapping("/funds")
public class PublicAccumulationFundsController {

	private static Logger logger = LoggerFactory.getLogger(PublicAccumulationFundsController.class);
	
	@Autowired FundsService fundsService;
	
	@RequestMapping("/get")
	@ResponseBody
	public List<Map<String,String>> get(LoginBean lb){
		
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		
		if(BeanUtil.isLoginBeanWithOutYZMEmpty(lb)){
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("Error","information is not complete");
			logger.error("information is not complete [{}]",JSON.toJSONString(lb));
			result.add(map);
		}else{
			result = fundsService.getFundsList(lb);
		}
		return result;
	}
}
