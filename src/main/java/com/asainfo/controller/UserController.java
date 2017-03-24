package com.asainfo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asainfo.pojo.Message;
import com.asainfo.pojo.User;
import com.asainfo.service.UserService;

@Controller
@RequestMapping({"user"})
public class UserController
{
  @Autowired
  private UserService userService;
  
  @RequestMapping("/toLogin")
  public String toLogin(){
	  return "login.html";
  }
  
  @RequestMapping(value="/smartBILogin",method = RequestMethod.POST)
  public String sLogin(String user,String password,HttpSession session)
  {     
	    User u = new User();
	    u.setName(user);
	    u.setPassword(password);
	    Message message = null;
	    message = this.userService.login(u);
	    if (message.isStatus()) {
	      session.setAttribute("user", message.getData());
	    }
	    return "index.jsp";
  }
  
  
  @ResponseBody
  @RequestMapping(value={"/login"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public Message login(User user, HttpSession session)
  {
	System.out.println("=========="+user);
    Message message = null;
    message = this.userService.login(user);
    if (message.isStatus()) {
      session.setAttribute("user", message.getData());
    }
    return message;
  }
  
  @RequestMapping(value={"/loginOut"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String loginOut(HttpSession session)
  {
    session.removeAttribute("user");
    return "/login";
  }
}
