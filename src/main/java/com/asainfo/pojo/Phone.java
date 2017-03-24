package com.asainfo.pojo;

public class Phone {
	private String mobile;
	private String source;
	private String type;
	private String invalid_dt;
	private String modify_type;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInvalid_dt() {
		return invalid_dt;
	}
	public void setInvalid_dt(String invalid_dt) {
		this.invalid_dt = invalid_dt;
	}
	public String getModify_type() {
		return modify_type;
	}
	public void setModify_type(String modify_type) {
		this.modify_type = modify_type;
	}
	@Override
	public String toString() {
		return "Phone [mobile=" + mobile + ", source=" + source + ", type="
				+ type + ", invalid_dt=" + invalid_dt + ", modify_type="
				+ modify_type + "]";
	}
}
