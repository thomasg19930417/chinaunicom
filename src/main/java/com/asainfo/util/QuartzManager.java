package com.asainfo.util;

import org.apache.logging.log4j.core.config.Order;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.asainfo.pojo.ScheduleJob;

@Component("quartzManager")
public class QuartzManager
{
  @Autowired
  private SchedulerFactoryBean schedulerFactoryBean;
  private static String JOB_NAME = "EXTJWEB_JOB_NAME";
  private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
  private static String TRIGGER_NAME = "EXTJWEB_TRIG_NAME";
  private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
  
  public ScheduleJob addJob(ScheduleJob job, Class cls)
  {
    try
    {
     System.out.println(this.schedulerFactoryBean);
      Scheduler sched = this.schedulerFactoryBean.getScheduler();
      JobDetail jobDetail = JobBuilder.newJob(cls)
        .withIdentity(job.getJobName(), JOB_GROUP_NAME).build();
      
      jobDetail.getJobDataMap().put("path", job.getCommand());
      
      CronScheduleBuilder scheduleBuilder = 
        CronScheduleBuilder.cronSchedule(job.getCronExpression());
      CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity(job.getTriggerName(), TRIGGER_GROUP_NAME).withSchedule(scheduleBuilder).build();
      sched.scheduleJob(jobDetail, trigger);
      if (!sched.isShutdown()) {
        sched.start();
      }
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
    job.setJobGroup(JOB_GROUP_NAME);
    return job;
  }
  
  public void modifyJobTime(ScheduleJob job)
  {
    try
    {
      Scheduler sched = this.schedulerFactoryBean.getScheduler();
      TriggerKey triggers = TriggerKey.triggerKey(job.getTriggerName());
      CronTrigger trigger = (CronTrigger)sched.getTrigger(triggers);
      if (trigger == null) {
        return;
      }
      String oldTime = trigger.getCronExpression();
      if (!oldTime.equalsIgnoreCase(job.getCronExpression()))
      {
        JobKey jobkey = new JobKey(job.getJobName());
        JobDetail jobDetail = sched.getJobDetail(jobkey);
        Class objJobClass = jobDetail.getJobClass();
        removeJob(job);
        addJob(job, objJobClass);
      }
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public void removeJob(ScheduleJob job)
  {
    try
    {
      Scheduler sched = this.schedulerFactoryBean.getScheduler();
      
      TriggerKey triggerKey = TriggerKey.triggerKey(job.getTriggerName(), JOB_GROUP_NAME);
      JobKey jobkey = new JobKey(job.getJobName());
      sched.pauseTrigger(triggerKey);
      sched.unscheduleJob(triggerKey);
      sched.deleteJob(jobkey);
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public void startJobs()
  {
    try
    {
      Scheduler sched = this.schedulerFactoryBean.getScheduler();
      sched.start();
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public void shutdownJobs()
  {
    try
    {
      Scheduler sched = this.schedulerFactoryBean.getScheduler();
      if (!sched.isShutdown()) {
        sched.shutdown();
      }
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
}
