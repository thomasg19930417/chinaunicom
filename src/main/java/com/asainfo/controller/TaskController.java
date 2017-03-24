package com.asainfo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asainfo.pojo.ChinaunicomResult;
import com.asainfo.pojo.Label;
import com.asainfo.pojo.Message;
import com.asainfo.pojo.PageBean;
import com.asainfo.pojo.Task;
import com.asainfo.service.TaskService;

@Controller
@RequestMapping({"task"})
public class TaskController
{
  @Autowired
  private TaskService taskService;
  
  @ResponseBody
  @RequestMapping(value={"gettask"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public Message getTaskByPage(PageBean pageBean, HttpServletRequest request)
  {
    Message message = null;
    message = this.taskService.getTaskByPage(pageBean);
    request.setAttribute("data", message.getData());
    System.out.println(message.getData());
    return message;
  }
  
  @ResponseBody
  @RequestMapping(value={"getProvBaobiao"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public Message getProvBaobiao(Integer taskids, HttpServletRequest request,HttpSession session)
  {
    Message message = null;
    /* String taskid = (String) session.getAttribute("taskid");
    Integer taskids = Integer.valueOf(taskid);
    System.out.println("======session====================="+taskids);*/
    message = this.taskService.getprov(taskids);
    session.removeAttribute("taskid");
    System.out.println(message.getData());
    return message;
  }
  
  @ResponseBody
  @RequestMapping(value={"submit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public Message submit(Integer taskid, HttpServletRequest request)
  {
    Message message = null;
    message = this.taskService.submit(taskid);
    return message;
  }
  
  @ResponseBody
  @RequestMapping(value={"addtask"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public Message addtask(Task task, HttpServletRequest requst)
  {
    Message message = null;
    message = this.taskService.addTask(task);
    return message;
  }
  
  @ResponseBody
  @RequestMapping(value={"addLabel"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public Message addLabel(Task task, String labelName, HttpServletRequest requst)
  {
    Message message = null;
    message = this.taskService.addLabel(task, labelName);
    return message;
  }
  
  @ResponseBody
  @RequestMapping(value="getLabel",method=RequestMethod.POST)
  public ChinaunicomResult getLabel(Integer taskid){
	  List<Label> label =  taskService.getLabel(taskid);
	  return ChinaunicomResult.ok(label);
  }
  
}
