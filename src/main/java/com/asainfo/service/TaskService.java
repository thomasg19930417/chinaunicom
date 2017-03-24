package com.asainfo.service;

import java.util.List;

import com.asainfo.pojo.Label;
import com.asainfo.pojo.Message;
import com.asainfo.pojo.PageBean;
import com.asainfo.pojo.Task;

public abstract interface TaskService
{
  public abstract Message getTaskByPage(PageBean paramPageBean);
  
  public abstract Message getprov(Integer paramInteger);
  
  public abstract Message submit(Integer paramInteger);
  
  public abstract Message addTask(Task paramTask);
  
  public abstract Message addLabel(Task paramTask, String paramString);

public abstract List<Label> getLabel(Integer taskid);
}
