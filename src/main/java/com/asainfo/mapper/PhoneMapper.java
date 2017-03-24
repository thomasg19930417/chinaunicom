package com.asainfo.mapper;

import java.util.List;

import com.asainfo.pojo.Phone;

public interface PhoneMapper {

	Phone getPhone(Phone phone);
    Integer modifyPhone(Phone phone);
    Integer addPhone(Phone phone);
    
    Integer delPhone(Phone phone);
	Integer bathInsert(List list);
}
