package com.asainfo.service.impl;

import com.asainfo.mapper.UserMapper;
import com.asainfo.pojo.Message;
import com.asainfo.pojo.User;
import com.asainfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl
  implements UserService
{
  @Autowired
  private UserMapper userMapper;
  
  public Message login(User user)
  {
    Message message = null;
    
    User u = this.userMapper.getUser(user);
    if (u != null)
    {
      message = new Message();
      message.setMsg("登录成功");
      message.setStatus(true);
      message.setData(u);
      return message;
    }
    message = new Message();
    message.setMsg("登录失败");
    message.setStatus(false);
    return message;
  }
}
