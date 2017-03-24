package com.asainfo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.asainfo.pojo.SendUserCycle;
import com.asainfo.service.UserActiveCycleService;
import com.asainfo.util.HbaseUtils;
@Service("userActiveCycleService")
public class UserActiveCycleServiceImpl implements UserActiveCycleService {

	public List<SendUserCycle> get(String mobile,String start,String end) {
		String starts =start.replace("-", "");
		String ends =end.replace("-", "");
		List list = HbaseUtils.getDataByPrefix("dw_senduser_cycle", mobile,
				SendUserCycle.class,starts,ends);
		return list;
	}

}
