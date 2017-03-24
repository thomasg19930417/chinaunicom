package com.asainfo.service;

import java.util.List;

import com.asainfo.pojo.SendUserCycle;

public interface UserActiveCycleService {
	public List<SendUserCycle>  get(String mobile,String start,String end);
}
