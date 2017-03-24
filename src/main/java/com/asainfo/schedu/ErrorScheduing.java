package com.asainfo.schedu;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;

public class ErrorScheduing
{
  private static final Logger LOG = Logger.getLogger(ErrorScheduing.class);
  
  public void before()
  {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cd = Calendar.getInstance();
    cd.setTime(date);
    int day = cd.get(5);
    cd.set(5, day - 1);
    String dir = "/data/" + sdf.format(cd.getTime()) + ".txt";
    
    String cm1 = "cd /data;python /data/mail_parse.py;";
    String cm2 = "scp " + dir + " 172.16.11.1:/log/Wrong_code/";
    LOG.info("===============================================");
    LOG.info("执行徐彬脚本");
    execute(cm1);
    execute(cm2);
  }
  
  public void execute(String command)
  {
    Connection con = new Connection("172.16.11.10");
    try
    {
      con.connect();
      boolean isLogin = con.authenticateWithPassword("aimcpro", "aimcpro@CUWO");
      System.out.println(isLogin);
      Session session = con.openSession();
      
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
        sb.append(new String(arr, 0, read));
      }
      int read;
      System.out.println(sb.toString());
      LOG.info("徐彬脚本【执行信息】:" + sb.toString());
      session.close();
      con.close();
    }
    catch (Exception localException) {}
  }
  
  public static void main(String[] args)
  {
    ErrorScheduing es = new ErrorScheduing();
    es.before();
  }
}
