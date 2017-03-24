package com.asainfo.service.impl;

import com.asainfo.pojo.Message;
import com.asainfo.service.EchartService;
import com.asainfo.util.HiveDBHelper;
import java.util.List;
import java.util.Map;

public class EchartServiceImpl
  implements EchartService
{
  public Message mcountnum(String type)
  {
    String sql = "select send_user_type,count(*) from DW_USABLE_SEND_USERINFO_D group by send_user_type order by send_user_type";
    HiveDBHelper hd = new HiveDBHelper();
    List<Map<String, Object>> list = HiveDBHelper.executeQuery(sql, null);
    return null;
  }
}
