package com.asainfo.pojo;

import java.util.List;

public class Message
{

    public Message()
    {
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public List getList()
    {
        return list;
    }

    public void setList(List list)
    {
        this.list = list;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public boolean isStatus()
    {
        return status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public String toString()
    {
        return (new StringBuilder("Message [data=")).append(data).append(", list=").append(list).append(", msg=").append(msg).append(", status=").append(status).append("]").toString();
    }

    private Object data;
    private List list;
    private String msg;
    private boolean status;
}
