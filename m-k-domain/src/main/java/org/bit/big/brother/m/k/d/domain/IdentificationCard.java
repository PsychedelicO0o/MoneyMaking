package org.bit.big.brother.m.k.d.domain;

public class IdentificationCard extends LoginBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2792829759662014415L;
	private String identification;			// 身份证

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		super.setBh(identification);
		this.identification = identification;
	}
}
