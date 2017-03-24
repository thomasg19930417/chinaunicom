package com.asainfo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.asainfo.pojo.Message;
import com.asainfo.pojo.PageBean;
import com.asainfo.pojo.Shell;
import com.asainfo.service.ShellService;
import com.asainfo.service.impl.IndexService;
import com.asainfo.util.DateUtil;

@RequestMapping("shell")
@Controller
public class ShellController extends IndexService{
	@Autowired
	private ShellService shellService;
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Message add(HttpServletRequest request,Shell shell,MultipartFile file){
		Message  message  = new Message();
		//文件上传部分
		//源文件名  file.getOriginalFilename();
		//文件重命名 防止文件重名
		String oldFileName = file.getOriginalFilename();
		String filename = UUID.randomUUID().toString()+oldFileName.substring(oldFileName.lastIndexOf("."));
		System.out.println("新文件名"+filename);
		System.out.println(request.getServletContext().getRealPath("/file"));
		File files = new File(request.getServletContext().getRealPath("/file"),filename);
		try {
			file.transferTo(files);
		} catch (Exception e) {
			message.setStatus(false);
			message.setMsg("上传文件发生错误");
			return message;
		} 
		//将记录保存到数据库
		shell.setFileName(filename);
		shell.setTime(DateUtil.getTime());
		System.out.println(shell);
		message = shellService.save(shell);
		//添加索引
		if(message.isStatus()){
			addIndex((Shell)message.getData());
		}
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/getpage")
	public Message getList(PageBean pageBean){
		Message message  = new Message();
		message = shellService.getlist(pageBean);
		return message;
	}
	
	@ResponseBody
	@RequestMapping(value="/getByIndex",method=RequestMethod.POST)
	public Message getByIndex(PageBean pageBean){
		System.out.println(pageBean);
		Message message  = new Message();
		pageBean  = searchPageByAfter(pageBean.getCondition(), pageBean.getCurrentPage(), pageBean.getPageNum());
		message.setData(pageBean);
		message.setStatus(true);
		message.setMsg("success");
		return message;
	}
	
	@ResponseBody
	@RequestMapping(value="/download/{id}")
	public String  download(@PathVariable Integer id,HttpServletRequest request,
			HttpServletResponse response){
		Message message = new Message();
		Shell shell = shellService.getShellById(id);
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="+ shell.getFileName());
		String path = request.getServletContext().getRealPath("/file");
		try {
			 if(shell==null){
				response.getWriter().println("<script>alert('没有检索到相关文件');</script>");
				response.getWriter().flush();
				response.getWriter().close();
		       }else{
		    	   InputStream inputStream = new FileInputStream(new File(path
							+ File.separator + shell.getFileName()));
					OutputStream os = response.getOutputStream();
					byte[] b = new byte[2048];
					int length;
					while ((length = inputStream.read(b)) > 0) {
						os.write(b, 0, length);
					}
					 // 这里主要关闭。
					os.close();
					inputStream.close(); 
		       }
		} catch (Exception e) {
			try {
				response.getWriter().println("<script>alert('文件下载发生异常，请稍后再试');</script>");
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
		return null;
	}
}
