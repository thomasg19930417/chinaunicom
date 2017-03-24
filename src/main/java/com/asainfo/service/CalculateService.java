package com.asainfo.service;

import java.io.File;
import java.util.HashMap;

import com.asainfo.pojo.Task;

public interface CalculateService {

	HashMap calculate( String  filenaem);

	String getSend(Task task);

	String getTable(String tab1,String t1key, String tb2,String t2key, String label);

}
