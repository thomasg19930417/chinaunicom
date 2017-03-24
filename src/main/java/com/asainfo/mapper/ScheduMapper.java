package com.asainfo.mapper;

import java.util.List;

import com.asainfo.pojo.ScheduleJob;

public interface ScheduMapper {

	int save(ScheduleJob job);

	List<ScheduleJob> getlistSchedu();

}
