package com.asainfo.controller;

import java.util.List;

import javax.servlet.ServletConfig;

import org.apache.log4j.Logger;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.context.ServletConfigAware;

import com.asainfo.mapper.ScheduMapper;
import com.asainfo.pojo.ScheduleJob;
import com.asainfo.schedu.ErrorScheduing;
import com.asainfo.schedu.QuartzJobFactoryImpl;
import com.asainfo.service.SchedulerService;
import com.asainfo.util.QuartzManager;

@Order(3)
@Configuration
public class WebInit implements InitializingBean, ServletConfigAware {
	@Autowired
	private ScheduMapper scheduMapper;
	@Autowired
	private SchedulerService schedulerService;
	@Autowired
	private QuartzManager quartzManager;
	private static final Logger LOG = Logger.getLogger(ErrorScheduing.class);

	public void setServletConfig(ServletConfig servletConfig) {
		List<ScheduleJob> jobs = scheduMapper.getlistSchedu();
		if (jobs.size() > 0) {
			for (ScheduleJob scheduleJob : jobs) {
				LOG.info("初始化job:"+scheduleJob);
				quartzManager.addJob(scheduleJob, QuartzJobFactoryImpl.class);
			}
		}
	}

	public void afterPropertiesSet() throws Exception {
	}
}
