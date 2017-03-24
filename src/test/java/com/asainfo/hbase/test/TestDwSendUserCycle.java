package com.asainfo.hbase.test;

import java.util.List;

import com.asainfo.pojo.SendUserCycle;
import com.asainfo.util.HbaseUtils;

public class TestDwSendUserCycle {

	public static void main(String[] args) {
		List list = HbaseUtils.getDataByPrefix("dw_senduser_cycle", "13002475139",
				SendUserCycle.class,"","");
		int i=0;
		for (Object object : list) {
			System.out.println("第"+(++i)+"个");
			System.out.println(object);
		}
	}
	
	
}
