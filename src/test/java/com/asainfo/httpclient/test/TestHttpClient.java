package com.asainfo.httpclient.test;

import java.util.List;

import com.asainfo.util.HiveDBHelper;

public class TestHttpClient {
	public static void main(String[] args) {  
		 HiveDBHelper.execute("create table  if not exists tmp_5956982 (mobile string)");
		 boolean b = HiveDBHelper.execute("create table if not exists tmp5958297 as select  t3.provincename,t3.provincecode,t3.nettype,t3.paytype,t3.send_user_type,count(t3.mobile) as num  from  ( select t1.mobile,t2.provincename,t2.provincecode,t2.nettype,t2.paytype,t2.send_user_type from  (select mobile from tmp_5956982  ) t1 inner join (select * from dw_usable_send_userinfo_d_new ) t2 on t1.mobile=t2.user_device  ) t3 group by provincename,provincecode,nettype,paytype,send_user_type");
		System.out.println(b);
		 //List list = HiveDBHelper.executeQuery("select count(*) from dw_usable_send_userinfo_d_new", null); 
		//System.out.println(list);
	}
}
