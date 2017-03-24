
package com.asainfo.pojo;


public class ScheduleJob
{

    public ScheduleJob()
    {
    }

    public String getJobId()
    {
        return jobId;
    }

    public void setJobId(String jobId)
    {
        this.jobId = jobId;
    }

    public String getJobName()
    {
        return jobName;
    }

    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }

    public String getJobGroup()
    {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup)
    {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus()
    {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus)
    {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression()
    {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression)
    {
        this.cronExpression = cronExpression;
    }

    public String getCommand()
    {
        return command;
    }

    public void setCommand(String command)
    {
        this.command = command;
    }

    public String getTriggerName()
    {
        return triggerName;
    }

    public void setTriggerName(String triggerName)
    {
        this.triggerName = triggerName;
    }

    public String toString()
    {
        return (new StringBuilder("ScheduleJob [jobId=")).append(jobId).append(", jobName=").append(jobName).append(", jobGroup=").append(jobGroup).append(", jobStatus=").append(jobStatus).append(", cronExpression=").append(cronExpression).append(", command=").append(command).append(", triggerName=").append(triggerName).append("]").toString();
    }

    private String jobId;
    private String jobName;
    private String jobGroup;
    private String jobStatus;
    private String cronExpression;
    private String command;
    private String triggerName;
}
