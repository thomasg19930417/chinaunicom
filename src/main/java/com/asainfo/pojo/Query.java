package com.asainfo.pojo;


public class Query
{

    public Query()
    {
    }

    public String getDatacode()
    {
        return datacode;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDatacode(String datacode)
    {
        this.datacode = datacode;
    }

    public String getSendType()
    {
        return sendType;
    }

    public void setSendType(String sendType)
    {
        this.sendType = sendType;
    }

    public String getNum()
    {
        return num;
    }

    public void setNum(String num)
    {
        this.num = num;
    }

    public String getProvincecode()
    {
        return provincecode;
    }

    public void setProvincecode(String provincecode)
    {
        this.provincecode = provincecode;
    }

    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }

    public String getTopic()
    {
        return topic;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public String getNocontain()
    {
        return nocontain;
    }

    public void setNocontain(String nocontain)
    {
        this.nocontain = nocontain;
    }

    public String getNettype() {
		return nettype;
	}

	public void setNettype(String nettype) {
		this.nettype = nettype;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	private String sendType;
    private String num;
    private String provincecode;
    private String userType;
    private String datacode;
    private String name;
    private String topic;
    private String nocontain;
    private String nettype;
    private String paytype;
}
