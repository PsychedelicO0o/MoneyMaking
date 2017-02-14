package org.bit.big.brother.m.k.c.util;

import org.apache.commons.lang3.StringUtils;
import org.bit.big.brother.m.k.d.domain.LoginBean;

public class BeanUtil {

	public static boolean isLoginBeanWithOutYZMEmpty(LoginBean lb){
		
		return StringUtils.isBlank(lb.getBh()) || StringUtils.isBlank(lb.getMm()) || lb.getLb() == 0 || lb.getLb() > 5;
	}
	
	public static boolean isLoginBeanWithYZMEmpty(LoginBean lb){
		
		return isLoginBeanWithOutYZMEmpty(lb) || StringUtils.isBlank(lb.getGjjcxjjmyhpppp());
	}
}
