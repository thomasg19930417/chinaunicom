package com.asainfo.service;

import java.util.List;

import com.asainfo.pojo.Phone;

public interface BlackUserService {

	Phone getPhone(Phone phone);
	public Integer modify(Phone phone);
	public Integer save(Phone phone);
	public Integer delPhone(Phone phone);
	Integer bathInsert(List list);

}
