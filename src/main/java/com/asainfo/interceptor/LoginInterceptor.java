package com.asainfo.interceptor;

import com.asainfo.pojo.User;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor
  extends HandlerInterceptorAdapter
{
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    String reqUrl = request.getRequestURI();
    if ((reqUrl.contains("login")) || (reqUrl.contains("toLogin"))) {
      return true;
    }
    User user = (User)request.getSession().getAttribute("user");
    if (user != null) {
      return true;
    }
   /* PrintWriter out =   response.getWriter();
    out.println("<html>");
    out.println("<script>window.location.href='/chinaunicom/user/toLogin'</script>");
    out.println("</html>");*/
    response.setContentType("text/html;charset=UTF-8");  
    response.getWriter().print("<script language=\"javascript\">alert(\"您还没有登录，请先登录!\");if(window.opener==null){window.top.location.href=\"/chinaunicom/user/toLogin\";}else{window.opener.top.location.href=\"/chinaunicom/user/toLogin\";window.close();}</script>");
    response.getWriter().flush();
    response.getWriter().close();
    return false;
  }
}
