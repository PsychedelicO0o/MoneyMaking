package org.bit.big.brother.m.k.d.domain;

public class RegisterNO extends LoginBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 783842791873530738L;
	private String register;			// 个人登记号

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		super.setBh(register);
		this.register = register;
	}
	
}
