package com.asainfo.service.impl;

import com.asainfo.mapper.TaskMapper;
import com.asainfo.pojo.*;
import com.asainfo.service.QueryServie;
import com.asainfo.util.DateUtil;
import com.asainfo.util.HiveDBHelper;
import com.asainfo.util.SshUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("queryService")
public class QueryServiceImpl implements QueryServie {
	@Autowired
	private TaskMapper taskMapper;

	public Message query(Query query, User u) {
		Message message = null;

		Task task = new Task();
		task.setTaskid(Integer.valueOf(DateUtil.getString01().trim()));
		task.setUsername(query.getName());
		task.setTopic(query.getTopic());
		task.setStatus(Integer.valueOf(0));
		task.setSubmit_time(DateUtil.getTime());
		task.setType(query.getSendType());
		int createTask = this.taskMapper.createTask(task);
		if (createTask < 0) {
			message = new Message();
			message.setData(query);
			message.setMsg("创建任务失败");
			message.setStatus(false);
			return message;
		}
		SshUtil su = new SshUtil();
		String command = Constant.getdata;
		command = command + " -i " + query.getSendType();
		command = command
				+ " -o "
				+ (query.getUserType().endsWith(",") ? query.getUserType()
						.substring(0, query.getUserType().length() - 2) : query
						.getUserType());
		String filename = DateUtil.getString() + ".txt";
		command = command + " -f " + Constant.dataFilePath + filename;
		command = command + " -l " + query.getNum();
		command = command
				+ " -d "
				+ query.getSendType().substring(
						query.getSendType().length() - 1);
		System.out.println("sendtype"
				+ query.getSendType().substring(
						query.getSendType().length() - 1));
		System.out.println("==" + query.getProvincecode());
		if ((query.getProvincecode() != null)
				&& (!"null".equals(query.getProvincecode()))) {
			command = command
					+ " -p "
					+ (query.getProvincecode().endsWith(",") ? query
							.getProvincecode().substring(0,
									query.getProvincecode().length() - 2) : query
							.getProvincecode());
		}
		String datacode = query.getSendType().substring(
				query.getSendType().length() - 1)
				+ ":" + DateUtil.getString();
		query.setDatacode(datacode);
		command = command + " -s " + datacode;

		String[] aa = query.getNocontain().split(",");
		List<String> datacodes = this.taskMapper.getDatacode(aa);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < datacodes.size(); i++) {
			if (i < datacodes.size() - 1) {
				sb.append("datacode='" + (String) datacodes.get(i) + "' or ");
			} else {
				sb.append("datacode='" + (String) datacodes.get(i) + "'");
			}
		}
		String dc = sb.toString();
		if(dc.contains("\t")){
			dc = dc.replace("\t","");
		}
		command = command + " -m \"" + dc + "\"";
		
		//网别
		if(query.getNettype() != null && !"null".equals(query.getNettype()) && !"".equals(query.getNettype())){
			command = command
					+ " -n "
					+ (query.getNettype().endsWith(",") ? query
							.getNettype().substring(0,
									query.getNettype().length() - 2) : query
							.getNettype());
		}
		
		//付费类型
		if(query.getPaytype() != null && !"null".equals(query.getPaytype()) && !"".equals(query.getPaytype())){
			command = command
					+ " -t "
					+ (query.getPaytype().endsWith(",") ? query
							.getPaytype().substring(0,
									query.getPaytype().length() - 2) : query
							.getPaytype());
		}
		
		System.out.println(command);
		String mess = su.exeCommand(command);

		String sqls = "select count(*) as num from DW_USER_SENDRECORDS   where month='"
				+ DateUtil.getMonth() + "' and datacode='" + datacode + "'";

		List<Map<String, Object>> list = HiveDBHelper.executeQuery(sqls, null);
		String count = ((Map) list.get(0)).get("num").toString();
		System.out.println(count);

		task.setFinish_time(DateUtil.getTime());
		task.setStatus(Integer.valueOf(1));
		task.setFile_path(filename);
		task.setDatacode(datacode);
		task.setFile_linenum(Integer.valueOf(count.trim()));
		createTask = this.taskMapper.updateTask(task);
		if (createTask < 0) {
			message = new Message();
			message.setData(task);
			message.setMsg("创建任务失败");
			message.setStatus(false);
			return message;
		}
		message = new Message();
		message.setData(task);
		message.setMsg(count);
		message.setStatus(true);
		return message;
	}

	public Message getProv(Task task) {
		Message message = new Message();
		System.out.println("=======" + task);
		
		String sql_new = "insert overwrite table DW_USABLE_SEND_USERINFO_D_NEW ";
		
		sql_new = sql_new + "select t2.user_device,t2.mailroot,t2.provincecode,t2.provincename,";
		if ("usable_num1".equals(task.getType())) {
			
			sql_new = sql_new + "case when t1.user_device is null then ";
			sql_new = sql_new + "cast(t2." + task.getType() + " as int) else (cast(t2." + task.getType()
					+ " as int)-1) end as " + task.getType()
					+ ",t2.usable_num2,t2.usable_num3,";
		}
		if ("usable_num2".equals(task.getType())) {
			
			sql_new = sql_new + "t2.usable_num1,";
			sql_new = sql_new + "case when t1.user_device is null then cast(t2."
					+ task.getType() + " as int) else (cast(t2." + task.getType()
					+ " as int)-1) end as " + task.getType() + ",t2.usable_num3,";
		}
		if ("usable_num3".equals(task.getType())) {
			
			sql_new = sql_new + "t2.usable_num1,t2.usable_num2,";
			sql_new = sql_new + "case when t1.user_device is null then cast(t2."
					+ task.getType() + " as int) else (cast(t2." + task.getType()
					+ " as int)-1) end as " + task.getType() + ",";
		}
		

		sql_new = sql_new + "t2.send_user_type, t2.nettype , t2.paytype , t2.user_status from ";
		sql_new = sql_new
				+ "(select user_device from DW_USER_SENDRECORDS   where month='"
				+ DateUtil.getMonth() + "' and datacode='" + task.getDatacode()
				+ "' ) t1 ";
		sql_new = sql_new + "right outer join ";
		sql_new = sql_new + "(select * from DW_USABLE_SEND_USERINFO_D_NEW ) t2 ";
		sql_new = sql_new + "on t1.user_device = t2.user_device";
		
		System.out.println(sql_new);
		int status_new = HiveDBHelper.update(sql_new);
		if (status_new == 404) {
			message = new Message();
			message.setMsg("任务失败");
			message.setStatus(false);
			return message;
		}
	
		String sqls = "select provincename,count(provincename) as num from  DW_USER_SENDRECORDS   where month='"
				+ DateUtil.getMonth()
				+ "' and datacode='"
				+ task.getDatacode()
				+ "' group by provincename";
		List<Map<String, Object>> list = HiveDBHelper.executeQuery(sqls, null);

		StringBuilder sb = new StringBuilder();
		for (Map<String, Object> map : list) {
			sb.append(map.get("provincename") + ":" + map.get("num") + ",");
		}
		Prov p = new Prov();
		p.setTaskid(task.getTaskid());
		p.setProv(sb.toString());
		this.taskMapper.saveProv(p);
		task.setFinish_time(DateUtil.getTime());
		this.taskMapper.updateTaskFinshTime(task);
		message.setData(list);
		message.setMsg("操作成功");
		message.setStatus(true);
		return message;
	}

	public Message delTask(Task task) {
		String sql = "alter table DW_USER_SENDRECORDS drop partition(month='"
				+ DateUtil.getMonth() + "',datacode='" + task.getDatacode()
				+ "')";

		this.taskMapper.delTask(task);
		Message message = new Message();
		int status = HiveDBHelper.update(sql);
		if (status == 404) {
			message = new Message();
			message.setMsg("任务失败");
			message.setStatus(false);
			return message;
		}
		message.setMsg("操作成功");
		message.setStatus(true);
		return message;
	}

	public Message getUserProp(String type) {
		String sql = "select * from dim_apaas_code where col_name = '" + type +"'";
		List<Map<String, Object>> list = HiveDBHelper.executeQuery(sql, null);
		System.out.println(list);
		Message message = new Message();
		message.setData(list);
		return message;
	}
}
