package com.asainfo.hbase.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.asainfo.pojo.Phone;
import com.asainfo.util.DateUtil;
import com.asainfo.util.HbaseUtils;

public class TestHbaseUtils {
	public static void main(String[] args) throws IOException {
		/*System.out.println("==============tianjai==================");
		HbaseUtils.insterRow("dw_black", "13069586805", "source", "source", "Apaas");
		HbaseUtils.insterRow("dw_black", "13069586805", "class", "classs", "");
		HbaseUtils.insterRow("dw_black", "13069586805", "type", "type", "111111");
		HbaseUtils.insterRow("dw_black", "13069586805", "effective_dt", "effective_dt", "20170213");
	
		System.out.println("============删除==============");
		HbaseUtils.deleRow("dw_black", "13069586805", null, null);
		
		System.out.println("==============tianjai==================");
		HbaseUtils.insterRow("dw_black", "13069586805", "source", "source", "Apaas");
		HbaseUtils.insterRow("dw_black", "13069586805", "class", "classs", "");
		HbaseUtils.insterRow("dw_black", "13069586805", "type", "type", "222222");
		HbaseUtils.insterRow("dw_black", "13069586805", "effective_dt", "effective_dt", "20170213");
		System.out.println("============查询==============");
		obj = (Phone) HbaseUtils.getData("dw_black", "13069586805", null, null,Phone.class);
		System.out.println(obj);*/
		
		/*List<Put> putList = new ArrayList<Put>();
		Put put = new Put(Bytes.toBytes("18610321705"));
		put.addColumn(Bytes.toBytes("source"), Bytes.toBytes("source"),
				Bytes.toBytes("手动维护1"));
		put.addColumn(Bytes.toBytes("class"), Bytes.toBytes("classs"),
				Bytes.toBytes(""));
		put.addColumn(Bytes.toBytes("type"), Bytes.toBytes("type"),
				Bytes.toBytes("333333"));
		put.addColumn(Bytes.toBytes("effective_dt"), Bytes.toBytes("effective_dt"),
				Bytes.toBytes("20170214"));
		putList.add(put);
		System.out.println(HbaseUtils.insertRows("dw_black", putList));*/
		System.out.println("============查询==============");
		Phone obj = (Phone) HbaseUtils.getData("dw_black_hbase", "18664256487", null, null,Phone.class);
		System.out.println(obj);
		
		/*
		System.out.println("==============tianjai==================");
		HbaseUtils.insterRow("dw_black", "13069586805", "source", "source", "Apaas");
		HbaseUtils.insterRow("dw_black", "13069586805", "class", "classs", "");
		HbaseUtils.insterRow("dw_black", "13069586805", "type", "type", "111111");
		HbaseUtils.insterRow("dw_black", "13069586805", "effective_dt", "effective_dt", "20170213");*/
		
		/*List<Put> putList = new ArrayList<Put>();
		Put put = new Put(Bytes.toBytes("13069586805"));
		put.addColumn(Bytes.toBytes("source"), Bytes.toBytes("source"),
				Bytes.toBytes("页面添加"));
		put.addColumn(Bytes.toBytes("class"), Bytes.toBytes("classs"),
				Bytes.toBytes(" "));
		put.addColumn(Bytes.toBytes("type"), Bytes.toBytes("type"),
				Bytes.toBytes("111111"));
		//默认有效期 此列  没有使用
		put.addColumn(Bytes.toBytes("effective_dt"), Bytes.toBytes("effective_dt"),
				Bytes.toBytes("30001231"));
		
		String today = DateUtil.getDay();
		put.addColumn(Bytes.toBytes("invalid_dt"), Bytes.toBytes("invalid_dt"),
					Bytes.toBytes(today));
		put.addColumn(Bytes.toBytes("etl_dt"), Bytes.toBytes("etl_dt"),
				Bytes.toBytes(today));
		//默认为1 代表是页面修改
		put.addColumn(Bytes.toBytes("modify_type"), Bytes.toBytes("modify_type"),
				Bytes.toBytes("0"));
		putList.add(put);
		HbaseUtils.insertRows("dw_black_hbase", putList);*/
	}
}
