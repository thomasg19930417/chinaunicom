package com.asainfo.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class SshUtil
{
  private static InputStream in;
  private static Properties properties;
  private static Connection conn;
  private static Session session;
  private static final Logger LOG = Logger.getLogger(SshUtil.class);
  
  static
  {
    try
    {
      in = SshUtil.class.getClassLoader().getResourceAsStream(
        "ssh.properties");
      properties = new Properties();
      properties.load(in);
    }
    catch (IOException e)
    {
      LOG.error("SSH:��ȡ�����ļ�ʧ��");
    }
  }
  
  public Connection getConnection()
  {
    try
    {
      String hostname = properties.getProperty("hostname");
      String username = properties.getProperty("username");
      String password = properties.getProperty("password");
      conn = new Connection(hostname);
      
      conn.connect();
      boolean isConn = conn.authenticateWithPassword(username, password);
      if (isConn)
      {
        LOG.info("SSH:登录成功");
        return conn;
      }
      LOG.error("SSH:登录失败");
      return null;
    }
    catch (IOException e)
    {
      LOG.error("SSh登录异常:" + e.getMessage());
    }
    return null;
  }
  
  public Session getSession()
  {
    try
    {
      conn = getConnection();
      session = conn.openSession();
      return session;
    }
    catch (IOException e)
    {
      LOG.error("SSH 打开会话失败");
    }
    return null;
  }
  
  public void close()
  {
    LOG.info("ssh:关闭连接");
    if (session != null) {
      session.close();
    }
    if (conn != null) {
      conn.close();
    }
  }
  
  public String exeCommand(String command)
  {
    try
    {
      session = getSession();
      session.requestPTY("vt100", 80, 24, 640, 480, null);
      Thread.sleep(2000L);
      session.execCommand(command);
      long start = System.currentTimeMillis();
      InputStream stdout = new StreamGobbler(session.getStdout());
      BufferedReader br = new BufferedReader(new InputStreamReader(
        stdout, "utf-8"));
      char[] arr = new char['Ѐ'];
      StringBuffer sb = new StringBuffer();
      for (;;)
      {
        int read = br.read(arr, 0, arr.length);
        if ((read < 0) || (System.currentTimeMillis() - start > 500000L)) {
          break;
        }
        System.out.println(new String(arr, 0, read));
        sb.append(new String(arr, 0, read));
      }
      int read;
      return sb.toString();
    }
    catch (Exception e)
    {
      LOG.error("ssh:执行命令失败:" + e.getMessage());
      return "";
    }
    finally
    {
      close();
    }
  }
}
