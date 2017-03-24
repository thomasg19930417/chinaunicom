package com.asainfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asainfo.pojo.ChinaunicomResult;
import com.asainfo.service.UserActiveCycleService;

@Controller
@RequestMapping("userActicve")
public class UserActiveCycleController {
	@Autowired
	private UserActiveCycleService userActiveCycleService;
	
	@ResponseBody
	@RequestMapping(value="get",method=RequestMethod.POST)
	public ChinaunicomResult get(String mobile,String start,String end){
	    List list  = userActiveCycleService.get(mobile, start, end);
		return ChinaunicomResult.ok(list);
	}
}
