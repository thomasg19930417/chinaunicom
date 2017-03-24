package com.asainfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asainfo.mapper.PhoneMapper;
import com.asainfo.pojo.Phone;
import com.asainfo.service.BlackUserService;

@Service("blackUserService")
public class BlackUserServiceImpl implements BlackUserService {

	@Autowired
	private PhoneMapper phoneMapper;

	public Phone getPhone(Phone phone) {
		return phoneMapper.getPhone(phone);
	}

	public Integer modify(Phone phone) {
		return phoneMapper.modifyPhone(phone);
	};

	public Integer save(Phone phone) {
		return phoneMapper.addPhone(phone);
	};
	public Integer delPhone(Phone phone) {
		return phoneMapper.delPhone(phone);
	}

	public Integer bathInsert(List list) {
		return phoneMapper.bathInsert(list);
	};

}
