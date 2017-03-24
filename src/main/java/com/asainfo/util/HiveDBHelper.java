package com.asainfo.util;

import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class HiveDBHelper
{
  private static Logger logger = Logger.getLogger(HiveDBHelper.class);
  private static String DRIVERNAME;
  private static String URL;
  private static String USERNAME;
  private static String PASSWORD;
  private static String DATABASE;
  private static Properties properties = new Properties();
  
  static
  {
    try
    {
      InputStream inputStream = HiveDBHelper.class.getClassLoader()
        .getResourceAsStream("hive.properties");
      properties.load(inputStream);
      DRIVERNAME = properties.getProperty("DriverName");
      DATABASE = properties.getProperty("HiveDataBase");
      URL = properties.getProperty("HiveURL") + DATABASE;
      USERNAME = properties.getProperty("username");
      PASSWORD = properties.getProperty("password");
      Class.forName(DRIVERNAME);
    }
    catch (Exception e)
    {
      logger.error(e.getMessage());
    }
  }
  
  public static Connection getConnection()
  {
    try
    {
      return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    catch (SQLException e)
    {
      logger.error(e.getMessage());
    }
    return null;
  }
  
  public static boolean execute(String sql){
	    try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			st.execute(sql);
			st.close();
			conn.close();
			return true;
		} catch (Exception e) {
			logger.info("执行sql失败："+sql+" 失败原因:"+e.getMessage());
			return false;
		}
  }
  
  
  
  public static int update(String sql)
  {
    Connection conn = null;
    Statement statement = null;
    try
    {
      conn = getConnection();
      statement = conn.createStatement();
      return statement.executeUpdate(sql);
    }
    catch (Exception e)
    {
      logger.info("更新失败:" + e.getMessage());
      return 404;
    }
    finally
    {
      try
      {
        statement.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      closeConn(conn, null, null);
    }
  }
  
  public static List<Map<String, Object>> executeQuery(String sql, List params)
  {
    List<Map<String, Object>> list = new ArrayList();
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;
    Connection conn = null;
    try
    {
      preparedStatement = getConnection().prepareStatement(sql);
      if (params != null) {
        setParams(preparedStatement, params);
      }
      rs = preparedStatement.executeQuery();
      while (rs.next())
      {
        ResultSetMetaData md = rs.getMetaData();
        Map<String, Object> map = new HashMap();
        for (int i = 1; i <= md.getColumnCount(); i++) {
          map.put(md.getColumnLabel(i), rs.getObject(i));
        }
        list.add(map);
      }
      return list;
    }
    catch (SQLException e)
    {
    	logger.info("执行查询sql失败"+e.getMessage());
    }
    finally
    {
      closeConn(conn, preparedStatement, rs);
    }
    return list;
  }
  
  private static void setParams(PreparedStatement preparedStatement, List params)
  {
    for (int i = 0; i < params.size(); i++) {
      try
      {
        logger.info("给占位符" + (i + 1) + "注值");
        preparedStatement.setObject(i + 1, params.get(i));
        logger.info("给占位符" + (i + 1) + "注值成功");
      }
      catch (SQLException e)
      {
        logger.info("给占位符" + i + "注值失败" + e.getMessage());
      }
    }
  }
  
  public static void closeConn(Connection conn, PreparedStatement ps, ResultSet rs)
  {
    if (rs != null) {
      try
      {
        rs.close();
      }
      catch (SQLException e)
      {
        logger.error(e.getMessage());
      }
    }
    if (ps != null) {
      try
      {
        ps.close();
      }
      catch (SQLException e)
      {
        logger.error(e.getMessage());
      }
    }
    if (conn != null) {
      try
      {
        conn.close();
      }
      catch (SQLException e)
      {
        logger.error(e.getMessage());
      }
    }
  }
  
  public static void main(String[] args)
  {
    String sql = "select provincecode,count(provincecode) as num from test_dw_sendrecord   where month='201611' and datacode='1:0427499' group by provincecode";
    
    List<Map<String, Object>> list = executeQuery(sql, null);
    for (Map<String, Object> map : list) {
      System.out.println(map);
    }
  }
}
