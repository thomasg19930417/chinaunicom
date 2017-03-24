package com.asainfo.pojo;

public class SendUser {
	private Integer taskid;
	private String type;
	private String content;

	public Integer getTaskid() {
		return taskid;
	}

	public void setTaskid(Integer integer) {
		this.taskid = integer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "SendUser [taskid=" + taskid + ", type=" + type + ", content="
				+ content + "]";
	}

}
