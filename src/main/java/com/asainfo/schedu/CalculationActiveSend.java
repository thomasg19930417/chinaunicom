package com.asainfo.schedu;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.asainfo.mapper.TaskMapper;
import com.asainfo.pojo.SendUser;
import com.asainfo.pojo.Task;
import com.asainfo.util.DateUtil;
import com.asainfo.util.HiveDBHelper;
import com.google.gson.Gson;
/********************
 * 
 * @author thomasg
 * @descrption 定时计算活动在各维度数量 每天获取没有进行统计的任务进行统计
 * @detail 通过活动数据编码获取对应分区下的数据进行统计
 * 
 */
public class CalculationActiveSend {
	 private static final Logger LOG = Logger.getLogger(CalculationActiveSend.class);
	 @Autowired 
	 TaskMapper taskMapper;
	 public void execute(){
		LOG.info("定时统计群发活动各维度分布情况");
		//获取当前没有进行统计的任务
		List<Task> list  = taskMapper.getDatacodes();
		//循环进行统计
		for (Task task : list) {
			Gson g = new Gson();
			SendUser su = new SendUser();
			HashMap map = getExecute(task);
			String content  = g.toJson(map);
			su.setTaskid(task.getTaskid());
			su.setType(task.getType());
			su.setContent(content);
			taskMapper.saveSend(su);
		}
		 
	 }
	private HashMap getExecute(Task task) {
		HashMap map = new HashMap();
		String tmpTableName01 = "tmp_"+DateUtil.getString();
		//两表进行关联
		String sql="create table "+tmpTableName01 +" as select t3.provincename,t3.provincecode,t3.nettype,t3.paytype,t3.send_user_type,count(t3.user_device) as num   from ";
		sql+="( select t1.user_device,t2.provincename,t2.provincecode,t2.nettype,t2.paytype,t2.send_user_type from";
		sql+=" (select user_device from DW_USER_SENDRECORDS where datacode='"+task.getDatacode()+"' ) t1";
		sql+=" left join ";
		sql+=" (select * from  dw_usable_send_userinfo_d_new) t2 ";
		sql+=" on t1.user_device = t2.user_device ) t3 group by provincename,provincecode,nettype,paytype,send_user_type";
		boolean isTrue = HiveDBHelper.execute(sql);
		System.out.println("================"+isTrue);
		if(isTrue){
			//统计各维度数据
			// 进行计算
			// 1 省份分布
			String prov = "select provincename as columname,sum(num) as number from "
					+ tmpTableName01 + " group by provincename";
			LOG.info("prov"+prov);
			List prov1 = HiveDBHelper.executeQuery(prov, null);
			map.put("prov", prov1);
			// 2 网别
			String net = "select columname,sum(num) as number  from (";
				   net += " select case when t2.col_code is null then   '其它' else  t2.col_content  end as columname,num from ";
				   net+="(select * from "+tmpTableName01+") t1 ";
				   net+="left outer join   ";
				   net+="( select col_num ,col_name,col_code,col_content  from dim_apaas_code where col_name = 'nettype' ) t2 on t1.nettype = t2.col_code ) t3  group by columname";
			LOG.info("net"+net);
			List net1 = HiveDBHelper.executeQuery(net, null);
			map.put("net", net1);
			// 3付费类型
			String pay = "select columname,sum(num) as number  from (";
			pay += " select case when t2.col_code is null then   '其它' else  t2.col_content  end as columname,num from ";
			pay+="(select * from "+tmpTableName01+") t1 ";
			pay+="left outer join   ";
			pay+="( select col_num ,col_name,col_code,col_content  from dim_apaas_code where col_name = 'paytype' ) t2 on t1.paytype = t2.col_code ) t3  group by columname";
			LOG.info("pay"+pay);
			List pay1 = HiveDBHelper.executeQuery(pay, null);
			map.put("pay", pay1);
			// 4用户类型
			
			String user = "select columname,sum(num) as number  from (";
			user += " select case when t2.col_code is null then   '其它' else  t2.col_content  end as columname,num from ";
			user+="(select * from "+tmpTableName01+") t1 ";
			user+="left outer join   ";
			user+="( select col_num ,col_name,col_code,col_content  from dim_apaas_code where col_name = 'send_user_type' ) t2 on t1.send_user_type = t2.col_code ) t3  group by columname";
			List user1 = HiveDBHelper.executeQuery(user, null);
			LOG.info(user+"  : "+user1);
			map.put("user", user1);
			
		}
		return map;
	}
}
