package org.bit.big.brother.m.k.w.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/t")
public class BaseController {

	@RequestMapping("/echo")
	@ResponseBody
	public String echo(String str){
		return str;
	}
}
