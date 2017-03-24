package com.asainfo.pojo;


public class Task
{

    public Task()
    {
    }

    public Integer getTaskid()
    {
        return taskid;
    }

    public void setTaskid(Integer taskid)
    {
        this.taskid = taskid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getSubmit_time()
    {
        return submit_time;
    }

    public void setSubmit_time(String submit_time)
    {
        this.submit_time = submit_time;
    }

    public String getFinish_time()
    {
        return finish_time;
    }

    public void setFinish_time(String finish_time)
    {
        this.finish_time = finish_time;
    }

    public String getFile_path()
    {
        return file_path;
    }

    public void setFile_path(String file_path)
    {
        this.file_path = file_path;
    }

    public String getDatacode()
    {
        return datacode;
    }

    public void setDatacode(String datacode)
    {
        this.datacode = datacode;
    }

    public Integer getFile_linenum()
    {
        return file_linenum;
    }

    public void setFile_linenum(Integer file_linenum)
    {
        this.file_linenum = file_linenum;
    }

    public String getTopic()
    {
        return topic;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String toString()
    {
        return (new StringBuilder("Task [taskid=")).append(taskid).append(", username=").append(username).append(", status=").append(status).append(", submit_time=").append(submit_time).append(", finish_time=").append(finish_time).append(", file_path=").append(file_path).append(", datacode=").append(datacode).append(", file_linenum=").append(file_linenum).append(", topic=").append(topic).append(", type=").append(type).append("]").toString();
    }

    private Integer taskid;
    private String username;
    private Integer status;
    private String submit_time;
    private String finish_time;
    private String file_path;
    private String datacode;
    private Integer file_linenum;
    private String topic;
    private String type;
}
