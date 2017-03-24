package com.asainfo.util;

import com.asainfo.pojo.Constant;
import com.asainfo.pojo.HttpResult;
import com.google.gson.Gson;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class TcodeUtil
{
  private static Log log = LogFactory.getLog(TcodeUtil.class);
  
  public HttpResult callTwoDimensionCode(String name, String msg)
  {
    String STATIC_URL = Constant.STATIC_URL;
    int MAX_TIME = 150000000;
    
    HttpClient httpClient = new HttpClient();
    
    httpClient.getHttpConnectionManager().getParams()
      .setConnectionTimeout(150000000);
    
   // String url = "http://114.247.0.97:8082/admail/activityPhoneFile/downloadPhoneFile.n";
    PostMethod postMethod = new PostMethod(STATIC_URL);
    
    postMethod.getParams().setParameter("http.socket.timeout", 
      Integer.valueOf(150000000));
    
    postMethod.getParams().setParameter("http.method.retry-handler", 
      new DefaultHttpMethodRetryHandler());
    try
    {
      postMethod.addParameter("fileName", name);
      
      log.debug("内容：" + msg);
      
      postMethod.addParameter("fileDataNumber", msg);
      String retString = "";
      
      int statusCode = httpClient.executeMethod(postMethod);
      if (statusCode != 200)
      {
        log.error("生成静态页二维码错误：" + name);
        Gson g = new Gson();
        String retString1 = "{\"resultCode\":\"1\",\"resultDes\":\"请求超时\"}";
        HttpResult h = (HttpResult)g.fromJson(retString1, HttpResult.class);
        return h;
      }
      byte[] responseBody = postMethod.getResponseBody();
      retString = new String(responseBody, "UTF-8");
      log.debug(">>response String :" + retString);
      
      Gson g = new Gson();
      HttpResult h = (HttpResult)g.fromJson(retString, HttpResult.class);
      return h;
    }
    catch (Exception e)
    {
      HttpResult localHttpResult1;
      Gson g = new Gson();
      String retString = "{\"resultCode\":\"1\",\"resultDes\":\"请求超时\"}";
      HttpResult h = (HttpResult)g.fromJson(retString, HttpResult.class);
      return h;
    }
    finally
    {
      postMethod.releaseConnection();
    }
  }
}
