package com.asainfo.controller;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.asainfo.pojo.ChinaunicomResult;

@Controller
@RequestMapping("file")
public class FileController {

	@RequestMapping("upLoad")
	public ChinaunicomResult upLoadFile(HttpServletRequest request,
			MultipartFile file) {
		// 文件上传部分
		// 源文件名 file.getOriginalFilename();
		// 文件重命名 防止文件重名
		String oldFileName = file.getOriginalFilename();
		String filename = UUID.randomUUID().toString()
				+ oldFileName.substring(oldFileName.lastIndexOf("."));
		System.out.println("新文件名" + filename);
		System.out.println(request.getServletContext().getRealPath("/file"));
		File files = new File(request.getServletContext().getRealPath("/file"),
				filename);
		try {
			file.transferTo(files);
		} catch (Exception e) {
			return ChinaunicomResult.build(404, "上传文件失败:"+e.getMessage());
		}
		
		//建表入库  回传  生成的表名
		return ChinaunicomResult.ok(files.getAbsolutePath());
	}

}
