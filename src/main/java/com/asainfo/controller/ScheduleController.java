package com.asainfo.controller;

import com.asainfo.pojo.Message;
import com.asainfo.pojo.ScheduleJob;
import com.asainfo.service.SchedulerService;
import java.io.PrintStream;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "/schedu/" })
public class ScheduleController {
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private SchedulerService schedulerService;

	@ResponseBody
	@RequestMapping({ "execute" })
	public Message executeJob(ScheduleJob scheduleJOb)
			throws SchedulerException {
		Message message = new Message();
		System.out.println("==============" + scheduleJOb);
		boolean isSuccess = this.schedulerService.executeJob(scheduleJOb,
				this.schedulerFactoryBean);
		if (!isSuccess) {
			message.setStatus(isSuccess);
			message.setMsg("fail");
			return message;
		}
		message.setStatus(isSuccess);
		message.setMsg("success");
		return message;
	}
}
