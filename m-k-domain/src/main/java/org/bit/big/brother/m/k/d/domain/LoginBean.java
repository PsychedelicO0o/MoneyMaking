package org.bit.big.brother.m.k.d.domain;

import java.io.Serializable;

public class LoginBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8334876093089194896L;
	private String lk = "ddqwewqacs12q21qwe21e1e1212e12e";
	private int lb;							// 用户名类型
	private String bh;						// 用户名
	private String mm;						// 密码
	private String gjjcxjjmyhpppp;			// 验证码
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public String getMm() {
		return mm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}
	public String getGjjcxjjmyhpppp() {
		return gjjcxjjmyhpppp;
	}
	public void setGjjcxjjmyhpppp(String gjjcxjjmyhpppp) {
		this.gjjcxjjmyhpppp = gjjcxjjmyhpppp;
	}
	public int getLb() {
		return lb;
	}
	public void setLb(int lb) {
		this.lb = lb;
	}
	public String getLk() {
		return lk;
	}
	public void setLk(String lk) {
		this.lk = lk;
	}
}
