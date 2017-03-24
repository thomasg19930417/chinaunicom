package com.asainfo.util;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {
	
	/***
	 *对象转为json 
	 */
	public static String toJson(Object obj){
		return new Gson().toJson(obj);
	}
	
	  /**
    *
    * 函数名称: parseData
    * 函数描述: 将json字符串转换为map
    * @param data
    * @return
    */
   public static Map<String, String> parseData(String data){
	   System.out.println(data);
       GsonBuilder gb = new GsonBuilder();
       Gson g = gb.create();
       Map<String, String> map = g.fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
       return map;
   }
}
