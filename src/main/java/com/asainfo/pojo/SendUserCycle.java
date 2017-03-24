package com.asainfo.pojo;

public class SendUserCycle {
	private String user_device;
	private String target;
	private String timestreap ;
	public String getUser_device() {
		return user_device;
	}
	public void setUser_device(String user_device) {
		this.user_device = user_device;
	}
	public String getTarget() {
		return target;
	}
	@Override
	public String toString() {
		return "SendUserCycle [user_device=" + user_device + ", target="
				+ target + ", timestreap=" + timestreap + "]";
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTimestreap() {
		return timestreap;
	}
	public void setTimestreap(String timestreap) {
		this.timestreap = timestreap;
	}
	
}
