package com.asainfo.schedu;

import com.asainfo.util.SshUtil;

import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJobFactoryImpl implements Job {
	private static final Logger LOG = Logger.getLogger(QuartzJobFactoryImpl.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		JobDataMap map = context.getJobDetail().getJobDataMap();

		SshUtil su = new SshUtil();
		String command = (String) map.get("path");
		LOG.info("执行定时任务,脚本名称:"+command);
		System.out.println(command);
		String  mess  = su.exeCommand(command);
		LOG.info("执行定时任务信息:"+mess);
	}
}
