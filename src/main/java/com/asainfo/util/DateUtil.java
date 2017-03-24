package com.asainfo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
  public static String getString01()
  {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("mmssSS");
    return sdf.format(date);
  }
  
  public static String getString()
  {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    return sdf.format(date);
  }
  
  public static String getDay()
  {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    return sdf.format(date);
  }
  
  public static String getMonth()
  {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
    return sdf.format(date);
  }
  
  public static String getTime()
  {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(date);
  }
}
