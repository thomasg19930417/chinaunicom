package com.asainfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asainfo.mapper.ScheduMapper;
import com.asainfo.pojo.ScheduleJob;
import com.asainfo.schedu.QuartzJobFactoryImpl;
import com.asainfo.service.SchedulerService;
import com.asainfo.util.DateUtil;
import com.asainfo.util.QuartzManager;
@Transactional
@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {
	@Autowired
	private QuartzManager quartzManager;
	@Autowired
	private ScheduMapper scheduMapper;
	public boolean executeJob(ScheduleJob job,
			SchedulerFactoryBean schedulerFactoryBean) {
		job.setTriggerName(job.getJobName() + DateUtil.getString01());
		job = this.quartzManager.addJob(job, QuartzJobFactoryImpl.class);
		job.setJobStatus(1+"");
		int count  = scheduMapper.save(job);
		if(count > 0 ){
			return true;
		}else{
			return false;
		}
	}
}
