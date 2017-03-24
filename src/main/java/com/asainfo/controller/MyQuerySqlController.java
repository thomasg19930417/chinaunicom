package com.asainfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asainfo.pojo.ChinaunicomResult;

@Controller
@RequestMapping("myquery")
public class MyQuerySqlController {

	@ResponseBody
	@RequestMapping("query")
	public ChinaunicomResult query() {

		return ChinaunicomResult.ok();
	}
}
