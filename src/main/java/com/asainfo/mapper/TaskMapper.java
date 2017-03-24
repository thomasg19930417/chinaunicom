package com.asainfo.mapper;

import com.asainfo.pojo.Label;
import com.asainfo.pojo.LabelDatacode;
import com.asainfo.pojo.PageBean;
import com.asainfo.pojo.Prov;
import com.asainfo.pojo.SendUser;
import com.asainfo.pojo.Task;

import java.util.List;

public interface TaskMapper {
	public List<Task> getTaskByPage(PageBean paramPageBean);

	public int getcount();

	public int createTask(Task paramTask);

	public int updateTask(Task paramTask);

	public void delTask(Task paramTask);

	public int saveProv(Prov paramProv);

	public void updateTaskFinshTime(Task paramTask);

	public Prov getprov(Prov paramProv);

	public Task getTaskById(Integer paramInteger);

	public int createTask01(Task paramTask);

	public Label getLableByName(String paramString);

	public void save(LabelDatacode paramLabelDatacode);

	public void createLabel(Label paramLabel);

	public List<String> getDatacode(String[] paramArrayOfString);

	public List<Label> getLabel(Task task);

	public List<Task> getDatacodes();

	public void saveSend(SendUser su);

	public SendUser getSend(Task task);
}
