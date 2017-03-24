package com.asainfo.controller;

import com.asainfo.pojo.Message;
import com.asainfo.pojo.Query;

public class BaseController
{

    public BaseController()
    {
    }

    public Message checkQuery(Query query)
    {
        Message message = new Message();
        if(query.getUserType() == null || "".equals(query.getUserType()))
        {
            message.setMsg("\u8BF7\u81F3\u5C11\u9009\u62E9\u4E00\u79CD\u7C7B\u578B\u7684\u7528\u6237");
            message.setStatus(false);
            return message;
        }
        if(query.getName() == null || "".equals(query.getName()))
        {
            message.setMsg("\u8BF7\u6DFB\u52A0\u9700\u6C42\u4EBA");
            message.setStatus(false);
            return message;
        }
        if(query.getNum() == null || "".equals(query.getNum()))
        {
            message.setMsg("\u8BF7\u8F93\u5165\u63D0\u53D6\u7684\u6570\u636E\u91CF");
            message.setStatus(false);
            return message;
        }
        if(query.getTopic() == null || "".equals(query.getTopic()))
        {
            message.setMsg("\u8BF7\u8F93\u5165\u6D3B\u52A8\u540D\u79F0");
            message.setStatus(false);
            return message;
        }if("noselect".equals(query.getSendType())){
        	 message.setMsg("请选择群发类型");
             message.setStatus(false);
             return message;
        }else
        {
            message.setMsg("\u53C2\u6570\u6B63\u786E");
            message.setStatus(true);
            return message;
        }
    }
}
