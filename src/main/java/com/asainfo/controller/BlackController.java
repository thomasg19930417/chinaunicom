package com.asainfo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jline.internal.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.asainfo.pojo.ChinaunicomResult;
import com.asainfo.pojo.Phone;
import com.asainfo.service.BlackUserService;

@Controller
@RequestMapping("/black/")
public class BlackController {
	@Autowired
	private BlackUserService blackUserServiceOnHbaseImpl;
	@ResponseBody
	@RequestMapping(value="select",method=RequestMethod.POST)
	public ChinaunicomResult getMobile(Phone phone) throws UnsupportedEncodingException{
		phone = blackUserServiceOnHbaseImpl.getPhone(phone);
		if(null == phone){
			return ChinaunicomResult.build(404, "没有检索到相关记录");
		}
		return ChinaunicomResult.ok(phone);
	}
	
	@ResponseBody
	@RequestMapping(value="modify",method=RequestMethod.POST)
	public ChinaunicomResult modify(Phone phone){
		System.out.println("修改"+phone);
		Integer count =  blackUserServiceOnHbaseImpl.save(phone);
		if(count > 0 ){
			return ChinaunicomResult.ok();
		}
		return ChinaunicomResult.build(404, "修改失败");
	}
	
	@ResponseBody
	@RequestMapping(value="add",method=RequestMethod.POST)
	public ChinaunicomResult add(Phone phone) throws UnsupportedEncodingException{
		System.out.println("添加"+phone);
		if(null!=getMobile(phone).getData()){
			return ChinaunicomResult.build(404, "用户已经存在");
		}
		Integer count =  blackUserServiceOnHbaseImpl.save(phone);
		if(count > 0 ){
			return ChinaunicomResult.ok();
		}
		return ChinaunicomResult.build(404, "添加失败");
	}
	@ResponseBody
	@RequestMapping(value="del",method=RequestMethod.POST)
	public ChinaunicomResult del(Phone phone){
		Integer count =  blackUserServiceOnHbaseImpl.delPhone(phone);
		if(count > 0 ){
			return ChinaunicomResult.ok();
		}
		return ChinaunicomResult.build(404, "删除失败");
	}
	
	@ResponseBody
	@RequestMapping(value="bathInsert",method= RequestMethod.POST)
	public ChinaunicomResult bathInsert(HttpServletRequest request,MultipartFile file,Phone phone){
		try {
			List list = new ArrayList();
			InputStream in =  file.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line ="";
			while((line =br.readLine())!=null){
				//将所有记录保存到list
				Phone  p = new Phone();
				p.setMobile(line.trim());
				p.setInvalid_dt(phone.getInvalid_dt());
				p.setSource(phone.getSource());
				p.setType(phone.getType());
				list.add(p);
			}
			Integer count  = blackUserServiceOnHbaseImpl.bathInsert(list);
			return ChinaunicomResult.ok();
		} catch (IOException e) {
			return ChinaunicomResult.build(404, "操作失败");
		}
	}
}
