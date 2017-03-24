package com.asainfo.controller;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.asainfo.pojo.ChinaunicomResult;
import com.asainfo.pojo.Task;
import com.asainfo.service.CalculateService;
import com.asainfo.util.DateUtil;

@Controller
@RequestMapping("/calculate/")
public class CalculateController {
	private static final Logger LOG =Logger.getLogger(CalculateController.class);
	@Autowired
	private CalculateService calculateService;
	@ResponseBody
	@RequestMapping(value="get",method=RequestMethod.POST)
	public ChinaunicomResult get(HttpServletRequest request,MultipartFile file){
		
	   //上传文件到hdfs
		/*HdfsUtil hu = new HdfsUtil();
		try {
			  CommonsMultipartFile cf= (CommonsMultipartFile)file;   
              DiskFileItem fi = (DiskFileItem)cf.getFileItem();   
              File inputFile = fi.getStoreLocation();  
              hu.createFile(inputFile, "hdfs://172.16.11.13:8020/chinaunicom/"+filename);              
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	    
	    String oldFileName = file.getOriginalFilename();
		String filename = DateUtil.getString()+oldFileName.substring(oldFileName.lastIndexOf("."));
		System.out.println("新文件名"+filename);
		System.out.println(request.getServletContext().getRealPath("/file"));
		File files = new File(request.getServletContext().getRealPath("/file"),filename);
		try {
			file.transferTo(files);
		} catch (Exception e) {
			return ChinaunicomResult.build(404, "上传文件失败");
		} 
		//LOG.info("hadoop fs -put " +path+" /chinaunicom/"+files.getName());
		//ssh.exeCommand("hadoop fs -put " +path+" /chinaunicom/"+files.getName());
		
		//HashMap map = calculateService.calculate(files);
		String path = files.getAbsolutePath();
		return ChinaunicomResult.ok(path);
	}
	
	@ResponseBody
	@RequestMapping(value="count",method=RequestMethod.POST)
	public  ChinaunicomResult calculate(String path){
		
		HashMap map = calculateService.calculate(path);
		return ChinaunicomResult.ok(map);
	}
	
	@ResponseBody
	@RequestMapping(value="display",method=RequestMethod.POST)
	public ChinaunicomResult display(Task task){
		String map = calculateService.getSend(task);
		if(map == null){
			return ChinaunicomResult.build(404, "没有检索到相关记录");
		}
		return ChinaunicomResult.ok(map);
	}
	/****************************
	 * 
	 * @param tab1 原始数据表
	 * @param tb2  标签维度表
	 * @param label 要进行匹配的标签
	 * @return  返回值注入生成临时的表名
	 */
	@ResponseBody
	@RequestMapping(value="createTable",method=RequestMethod.POST)
	public ChinaunicomResult createTable(String tab1,String t1Key,String tb2,String t2Key,String label){
	    String tableName = calculateService.getTable( tab1,t1Key, tb2,t2Key, label);
		return ChinaunicomResult.ok(tableName); 
	}
	
	
}
