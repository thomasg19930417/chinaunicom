package com.asainfo.controller;

import io.netty.handler.codec.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asainfo.pojo.ChinaunicomResult;
import com.asainfo.pojo.Message;
import com.asainfo.pojo.Query;
import com.asainfo.pojo.Task;
import com.asainfo.pojo.User;
import com.asainfo.service.QueryServie;

@Controller
@RequestMapping(value = { "query" })
public class QueryController extends BaseController {

	public QueryController() {
	}

	@RequestMapping(value = { "index" })
	public String toPage(String jobId,  String pagename, HttpServletRequest request , HttpSession session)

	{ 
		return pagename;
	}
	
		
	@ResponseBody
	@RequestMapping(value = "menu",method=RequestMethod.POST )
	public String menu( String jobid, HttpServletRequest request , HttpSession session){
		System.out.println(jobid+"************************");
		session.setAttribute("taskid", jobid);
		return "redirect:/index?pagename=Menu/detail.html"; 
	}
	
	
	@ResponseBody
	@RequestMapping(value = { "getUserProp" })
	public Message getUserProp(String type) {
		Message message = queryService.getUserProp(type);
		return message;
	}

	@ResponseBody
	@RequestMapping(value = { "query" })
	public Message query(Query query, HttpSession session) {
		User u = (User) session.getAttribute("user");
		Message message = super.checkQuery(query);
		if (!message.isStatus()) {
			return message;
		} else {
			message = queryService.query(query, u);
			return message;
		}

	}

	@ResponseBody
	@RequestMapping(value = { "getProv" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public Message getProv(Task task, HttpSession session) {
		User u = (User) session.getAttribute("user");
		Message message = queryService.getProv(task);
		return message;
	}

	@ResponseBody
	@RequestMapping(value = { "delTask" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public Message delTask(Task task, HttpSession session) {
		User u = (User) session.getAttribute("user");
		Message message = queryService.delTask(task);
		return message;
	}

	@Autowired
	private QueryServie queryService;
}
