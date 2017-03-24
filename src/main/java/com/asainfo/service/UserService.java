package com.asainfo.service;

import com.asainfo.pojo.Message;
import com.asainfo.pojo.User;

public abstract interface UserService
{
  public abstract Message login(User paramUser);
}
