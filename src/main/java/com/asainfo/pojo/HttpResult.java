package com.asainfo.pojo;

public class HttpResult {

	public HttpResult() {
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDes() {
		return resultDes;
	}

	public void setResultDes(String resultDes) {
		this.resultDes = resultDes;
	}

	public String toString() {
		return (new StringBuilder("HttpResult [resultCode="))
				.append(resultCode).append(", resultDes=").append(resultDes)
				.append("]").toString();
	}

	private String resultCode;
	private String resultDes;
}
