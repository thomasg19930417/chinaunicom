package com.asainfo.service;

import com.asainfo.pojo.Message;
import com.asainfo.pojo.Query;
import com.asainfo.pojo.Task;
import com.asainfo.pojo.User;

public abstract interface QueryServie
{
  public abstract Message query(Query paramQuery, User paramUser);
  
  public abstract Message getProv(Task paramTask);
  
  public abstract Message delTask(Task paramTask);
  
  public abstract Message getUserProp(String type);
}
