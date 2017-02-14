package org.bit.big.brother.m.k.d.domain;

public class IdentificationCard extends LoginBean{

	private String identification;			// 身份证

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		super.setBh(identification);
		this.identification = identification;
	}
}
