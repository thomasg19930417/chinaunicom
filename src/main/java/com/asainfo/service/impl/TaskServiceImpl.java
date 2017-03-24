package com.asainfo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asainfo.mapper.TaskMapper;
import com.asainfo.pojo.HttpResult;
import com.asainfo.pojo.Label;
import com.asainfo.pojo.LabelDatacode;
import com.asainfo.pojo.Message;
import com.asainfo.pojo.PageBean;
import com.asainfo.pojo.Prov;
import com.asainfo.pojo.Task;
import com.asainfo.service.TaskService;
import com.asainfo.util.DateUtil;
import com.asainfo.util.HiveDBHelper;
import com.asainfo.util.SshUtil;
import com.asainfo.util.TcodeUtil;

@Transactional
@Service("taskService")
public class TaskServiceImpl implements TaskService {
	@Autowired
	private TaskMapper taskMapper;

	public Message getTaskByPage(PageBean pageBean) {
		pageBean.setCurrentPage((pageBean.getCurrentPage() - 1)
				* pageBean.getPageNum());
		List<Task> task = this.taskMapper.getTaskByPage(pageBean);
		int count = this.taskMapper.getcount();
		count = count % pageBean.getPageNum() == 0 ? count
				/ pageBean.getPageNum() : count / pageBean.getPageNum() + 1;
		pageBean.setCount(count);
		pageBean.setData(task);
		Message message = new Message();
		message.setData(pageBean);
		return message;
	}

	public Message getprov(Integer taskid) {
		Prov p = new Prov();
		p.setTaskid(taskid);
		p = this.taskMapper.getprov(p);
		Map map = new HashMap();
		if (p != null) {
			String s = p.getProv();
			String[] ss = s.split(",");
			String name = "";
			String num = "";
			int i = 0;
			String[] arrayOfString1;
			int j = (arrayOfString1 = ss).length;
			for (int i1 = 0; i1 < j; i1++) {
				String string = arrayOfString1[i1];
				String[] sss = string.split(":");
				name = name + sss[0] + ":";
				num = num + sss[1] + ":";
			}
			map.put("name", name);
			map.put("num", num);
			Message message = new Message();
			message.setData(map);
			message.setStatus(true);
			message.setMsg("操作成功");
			return message;
		}
		Message message = new Message();
		message.setData(map);
		message.setStatus(false);
		message.setMsg("没有检索到相关信息");
		return message;
	}

	public Message submit(Integer taskid) {
		Message message = new Message();
		Task task = this.taskMapper.getTaskById(taskid);
		TcodeUtil tu = new TcodeUtil();
		HttpResult result = tu.callTwoDimensionCode(task.getFile_path(),
				task.getDatacode());
		task.setStatus(Integer.valueOf(2));
		this.taskMapper.updateTask(task);
		message.setData(result);
		return message;
	}

	public Message addTask(Task task) {
		Message message = null;
		task.setTaskid(Integer.valueOf(DateUtil.getString01().trim()));
		task.setDatacode(task.getType().substring(task.getType().length() - 1)
				+ ":" + DateUtil.getString());
		task.setSubmit_time(DateUtil.getTime());
		task.setStatus(Integer.valueOf(1));

		String tmpTable = "tmp_" + DateUtil.getString01();
		String sqls = "create table " + tmpTable + "(col1 string)";
		String load = "hive -e \"load data local inpath '/data/export_data/batch_mail/"
				+ task.getFile_path() + "' into table " + tmpTable + "\"";
		String ssql = "alter table DW_USER_SENDRECORDS add if not exists partition(month='"
				+ DateUtil.getMonth()
				+ "',datacode='"
				+ task.getDatacode()
				+ "')";
		String loadend = "insert into DW_USER_SENDRECORDS partition(month='"
				+ DateUtil.getMonth() + "',datacode='" + task.getDatacode()
				+ "') select distinct substr(a.col1,1,11) as user_device,"
				+ task.getType().substring(task.getType().length() - 1) + ",";
		loadend = loadend + "substr(col1,13,length(a.col1)) as mailroot";
		loadend = loadend
				+ ",b.provincecode ,b.provincename from "
				+ tmpTable
				+ " a inner join tbl_phone_zone b on substr(a.col1,1,7) =b.number";
		System.out.println("建表" + sqls);
		System.out.println("load" + load);
		System.out.println("创建分区" + ssql);
		System.out.println("load数据到分区" + loadend);
		HiveDBHelper.update(sqls);
		SshUtil su = new SshUtil();
		String mess = su.exeCommand(load);
		HiveDBHelper.update(ssql);
		HiveDBHelper.update(loadend);

		String sql_new = "insert overwrite table DW_USABLE_SEND_USERINFO_D_NEW ";

		sql_new = sql_new
				+ "select t2.user_device,t2.mailroot,t2.provincecode,t2.provincename,";
		if ("usable_num1".equals(task.getType())) {

			sql_new = sql_new + "case when t1.user_device is null then ";
			sql_new = sql_new + "cast(t2." + task.getType()
					+ " as int) else (cast(t2." + task.getType()
					+ " as int)-1) end as " + task.getType()
					+ ",t2.usable_num2,t2.usable_num3,";
		}
		if ("usable_num2".equals(task.getType())) {

			sql_new = sql_new + "t2.usable_num1,";
			sql_new = sql_new
					+ "case when t1.user_device is null then cast(t2."
					+ task.getType() + " as int) else (cast(t2."
					+ task.getType() + " as int)-1) end as " + task.getType()
					+ ",t2.usable_num3,";
		}
		if ("usable_num3".equals(task.getType())) {

			sql_new = sql_new + "t2.usable_num1,t2.usable_num2,";
			sql_new = sql_new
					+ "case when t1.user_device is null then cast(t2."
					+ task.getType() + " as int) else (cast(t2."
					+ task.getType() + " as int)-1) end as " + task.getType()
					+ ",";
		}

		sql_new = sql_new
				+ "t2.send_user_type, t2.nettype , t2.paytype , t2.user_status from ";
		sql_new = sql_new
				+ "(select user_device from DW_USER_SENDRECORDS   where month='"
				+ DateUtil.getMonth() + "' and datacode='" + task.getDatacode()
				+ "' ) t1 ";
		sql_new = sql_new + "right outer join ";
		sql_new = sql_new
				+ "(select * from DW_USABLE_SEND_USERINFO_D_NEW ) t2 ";
		sql_new = sql_new + "on t1.user_device = t2.user_device";

		System.out.println(sql_new);
		int status_new = HiveDBHelper.update(sql_new);
		if (status_new == 404) {
			message = new Message();
			message.setMsg("任务失败");
			message.setStatus(false);
			return message;
		}
		String sqlsss = "select provincename,count(provincename) as num from  DW_USER_SENDRECORDS   where month='"
				+ DateUtil.getMonth()
				+ "' and datacode='"
				+ task.getDatacode()
				+ "' group by provincename";
		List<Map<String, Object>> list = HiveDBHelper
				.executeQuery(sqlsss, null);

		StringBuilder sb = new StringBuilder();
		for (Map<String, Object> map : list) {
			sb.append(map.get("provincename") + ":" + map.get("num") + ",");
		}
		Prov p = new Prov();
		p.setTaskid(task.getTaskid());
		p.setProv(sb.toString());
		this.taskMapper.saveProv(p);

		task.setFinish_time(DateUtil.getTime());
		int count = this.taskMapper.createTask01(task);
		if (count <= 0) {
			message = new Message();
			message.setMsg("添加失败");
			message.setStatus(false);
			return message;
		}
		message = new Message();
		message.setMsg("添加成功");
		message.setStatus(true);
		return message;
	}

	public Message addLabel(Task task, String labelName) {
		task = this.taskMapper.getTaskById(task.getTaskid());
		String[] labels = labelName.split(",");
		System.out.println("参数" + labelName);
		String[] arrayOfString1;
		int j = (arrayOfString1 = labels).length;
		for (int i = 0; i < j; i++) {
			String lab = arrayOfString1[i];
			System.out.println(lab);
			Label labe = this.taskMapper.getLableByName(lab);
			if (labe != null) {
				LabelDatacode l = new LabelDatacode();
				l.setDatacode(task.getDatacode());
				l.setLabelId(labe.getId());
				this.taskMapper.save(l);
			} else {
				labe = new Label();
				labe.setLabelname(lab);
				this.taskMapper.createLabel(labe);
				labe = this.taskMapper.getLableByName(lab);
				LabelDatacode l = new LabelDatacode();
				l.setDatacode(task.getDatacode());
				l.setLabelId(labe.getId());
				this.taskMapper.save(l);
			}
		}
		Message message = new Message();
		message.setStatus(true);
		message.setMsg("操作成功");
		return message;
	}

	public List<Label> getLabel(Integer taskid) {
		Task task = new Task();
		task.setTaskid(taskid);
		List<Label> list = taskMapper.getLabel(task);
		return list;
	}
}
