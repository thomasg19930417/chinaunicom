package com.asainfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

import com.asainfo.pojo.Phone;
import com.asainfo.service.BlackUserService;
import com.asainfo.util.DateUtil;
import com.asainfo.util.HbaseUtils;
@Service("blackUserServiceOnHbaseImpl")
public class BlackUserServiceOnHbaseImpl implements BlackUserService {

	public Phone getPhone(Phone phone) {
		return (Phone) HbaseUtils.getData("dw_black_hbase", phone.getMobile(), null, null,Phone.class);
	}

	public Integer modify(Phone phone) {
		return null;
	}

	public Integer save(Phone phone) {
		List<Put> putList = new ArrayList<Put>();
		Put put = new Put(Bytes.toBytes(phone.getMobile()));
		put.addColumn(Bytes.toBytes("source"), Bytes.toBytes("source"),
				Bytes.toBytes(phone.getSource()));
		
		put.addColumn(Bytes.toBytes("type"), Bytes.toBytes("type"),
				Bytes.toBytes(phone.getType()));
		
		
		String today = DateUtil.getDay();
		//失效日期 默认 30001231  为否时：为当天日期
		if("0".equals(phone.getInvalid_dt())){
			put.addColumn(Bytes.toBytes("invalid_dt"), Bytes.toBytes("invalid_dt"),
					Bytes.toBytes("30001231"));
		}else{
			put.addColumn(Bytes.toBytes("invalid_dt"), Bytes.toBytes("invalid_dt"),
					Bytes.toBytes(today));
		}
		
		//默认为1 代表是页面修改
		put.addColumn(Bytes.toBytes("modify_type"), Bytes.toBytes("modify_type"),
				Bytes.toBytes("1"));
		putList.add(put);
		return HbaseUtils.insertRows("dw_black_hbase", putList);
	}

	public Integer delPhone(Phone phone) {
		return HbaseUtils.deleRow("dw_black_hbase", phone.getMobile(), null, null);
	}

	public Integer bathInsert(List list) {
		List<Put> putList = new ArrayList<Put>();
		Phone phone = null;
		for (int i=0;i<list.size();i++) {
			phone = (Phone) list.get(i);
			Put put = new Put(Bytes.toBytes(phone.getMobile()));
			put.addColumn(Bytes.toBytes("source"), Bytes.toBytes("source"),
					Bytes.toBytes(phone.getSource()));
			put.addColumn(Bytes.toBytes("type"), Bytes.toBytes("type"),
					Bytes.toBytes(phone.getType()));
			String today = DateUtil.getDay();
			//失效日期 默认 30001231  为否时：为当天日期
			if(phone.getInvalid_dt().equals("0")){
				put.addColumn(Bytes.toBytes("invalid_dt"), Bytes.toBytes("invalid_dt"),
						Bytes.toBytes("30001231"));
			}else{
				put.addColumn(Bytes.toBytes("invalid_dt"), Bytes.toBytes("invalid_dt"),
						Bytes.toBytes(today));
			}
			//默认为1 代表是页面修改
			put.addColumn(Bytes.toBytes("modify_type"), Bytes.toBytes("modify_type"),
					Bytes.toBytes("1"));
			putList.add(put);
		}
		return HbaseUtils.insertRows("dw_black_hbase", putList);
	}
}
