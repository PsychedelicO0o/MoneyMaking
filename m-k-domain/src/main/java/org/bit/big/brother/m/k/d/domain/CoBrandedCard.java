package org.bit.big.brother.m.k.d.domain;

public class CoBrandedCard extends LoginBean{

	private String coBrandedCard;					// 公积金联名卡号
	
	public String getCoBrandedCard() {
		return coBrandedCard;
	}
	
	public void setCoBrandedCard(String coBrandedCard) {
		super.setBh(coBrandedCard);
		this.coBrandedCard = coBrandedCard;
	}
}
