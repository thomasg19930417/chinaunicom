package com.asainfo.service;

import com.asainfo.pojo.ScheduleJob;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public abstract interface SchedulerService
{
  public abstract boolean executeJob(ScheduleJob paramScheduleJob, SchedulerFactoryBean paramSchedulerFactoryBean);
}
