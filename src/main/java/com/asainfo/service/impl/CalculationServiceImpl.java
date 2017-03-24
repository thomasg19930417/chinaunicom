package com.asainfo.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asainfo.mapper.TaskMapper;
import com.asainfo.pojo.SendUser;
import com.asainfo.pojo.Task;
import com.asainfo.service.CalculateService;
import com.asainfo.util.DateUtil;
import com.asainfo.util.GsonUtil;
import com.asainfo.util.HiveDBHelper;
import com.asainfo.util.SshUtil;

@Service("calculateService")
public class CalculationServiceImpl implements CalculateService {
	private static final Logger LOG = Logger
			.getLogger(CalculationServiceImpl.class);
	@Autowired
	TaskMapper taskMapper;

	public HashMap calculate(String filename) {

		try {
			// 建表
			String tmpTableName = DateUtil.getString01();
			LOG.info("建表：  create table  if not exists tmp_" + tmpTableName
					+ " (mobile string)");
			boolean s01 = HiveDBHelper
					.execute("create table  if not exists tmp_" + tmpTableName
							+ " (mobile string)");
			// 加载数据
			LOG.info("加载数据：  load data local inpath '" + filename
					+ "' into table tmp_" + tmpTableName);
			SshUtil su = new SshUtil();
			String mess = su.exeCommand("hive -e \"load data local inpath '"
					+ filename + "' into table tmp_" + tmpTableName + "\"");
			// 计算用的临时表
			String tmpTableName01 = "tmp" + DateUtil.getString01();
			// 统计
			String sql = "create table if not exists "
					+ tmpTableName01
					+ " as select  t3.provincename,t3.provincecode,t3.nettype,t3.paytype,t3.send_user_type,count(t3.mobile) as num  from ";
			sql += " ( select t1.mobile,t2.provincename,t2.provincecode,t2.nettype,t2.paytype,t2.send_user_type from ";
			sql += " (select mobile from tmp_" + tmpTableName + "  ) t1";
			sql += " inner join (select * from dw_usable_send_userinfo_d_new ) t2 on t1.mobile=t2.user_device ";
			sql += " ) t3 group by provincename,provincecode,nettype,paytype,send_user_type";
			boolean s03 = HiveDBHelper.execute(sql);
			LOG.info("用于统计的表" + sql);
			HashMap map = new HashMap();
			// 进行计算
			// 1 省份分布
			String prov = "select provincename as columname,sum(num) as number from "
					+ tmpTableName01 + " group by provincename";
			LOG.info("prov" + prov);
			List prov1 = HiveDBHelper.executeQuery(prov, null);
			map.put("prov", prov1);
			// 2 网别
			String net = "select columname,sum(num) as number  from (";
			net += " select case when t2.col_code is null then   '其它' else  t2.col_content  end as columname,num from ";
			net += "(select * from " + tmpTableName01 + ") t1 ";
			net += "left outer join   ";
			net += "( select col_num ,col_name,col_code,col_content  from dim_apaas_code where col_name = 'nettype' ) t2 on t1.nettype = t2.col_code ) t3  group by columname";
			LOG.info("net" + net);
			List net1 = HiveDBHelper.executeQuery(net, null);
			map.put("net", net1);
			// 3付费类型
			String pay = "select columname,sum(num) as number  from (";
			pay += " select case when t2.col_code is null then   '其它' else  t2.col_content  end as columname,num from ";
			pay += "(select * from " + tmpTableName01 + ") t1 ";
			pay += "left outer join   ";
			pay += "( select col_num ,col_name,col_code,col_content  from dim_apaas_code where col_name = 'paytype' ) t2 on t1.paytype = t2.col_code ) t3  group by columname";
			LOG.info("pay" + pay);
			List pay1 = HiveDBHelper.executeQuery(pay, null);
			map.put("pay", pay1);
			// 4用户类型

			String user = "select columname,sum(num) as number  from (";
			user += " select case when t2.col_code is null then   '其它' else  t2.col_content  end as columname,num from ";
			user += "(select * from " + tmpTableName01 + ") t1 ";
			user += "left outer join   ";
			user += "( select col_num ,col_name,col_code,col_content  from dim_apaas_code where col_name = 'send_user_type' ) t2 on t1.send_user_type = t2.col_code ) t3  group by columname";
			List user1 = HiveDBHelper.executeQuery(user, null);
			LOG.info(user + "  : " + user1);
			map.put("user", user1);
			// 总体
			// String all="select * from tmpTableName01";
			// 删除表 删除文件
			// HiveDBHelper.execute("drop table if exists tmp_" + tmpTableName);
			// HiveDBHelper.execute("drop table if exists " + tmpTableName01);
			// hu.delete("/chinaunicom/" + filename);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/***************************
	 * weiwancheng
	 */
	public String getSend(Task task) {
		SendUser su = taskMapper.getSend(task);
		//HashMap map = (HashMap) GsonUtil.parseData(su.getContent());
		return su.getContent();
	}

	public String getTable(String tab1,String t1key, String tb2,String t2key, String label) {
		//进行sql 拼接
		String tmpTable = "tmp_"+DateUtil.getString();
		String sql = "create table "+tmpTable+"  as select "+label+",count(t1."+t1key+") from";
		sql+=" (select * from "+tab1+" ) t1 left join";
		sql+="( select * from "+tb2+") t2 on t1."+t1key+"=t2."+t2key +" group by "+label;
		System.out.println(sql);
		boolean isTrue = HiveDBHelper.execute(sql);
		return tmpTable;
	}

}
