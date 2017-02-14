package org.bit.big.brother.m.k.w.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/error")
public class Error {

	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request,HttpServletResponse response){
		ModelAndView error = new ModelAndView("/common/error");
		Map<String,String> result = new HashMap<String,String>();
		result.put("code",response.getStatus()+"");
		if(response.getStatus() == 404){
			result.put("data","resouece not found");
		}else{
			result.put("data","server error");
		}
		error.addObject("result",result);
		return error;
	}
}
