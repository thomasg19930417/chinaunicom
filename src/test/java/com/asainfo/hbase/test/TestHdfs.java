package com.asainfo.hbase.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.asainfo.util.HdfsUtil;

public class TestHdfs {
	public static void main(String[] args) throws IOException {
		HdfsUtil hu = new HdfsUtil();
		//FileInputStream input = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\phone.txt"));
		hu.createFile(new File("C:\\Users\\Administrator\\Desktop\\phone.txt"), "hdfs://172.16.11.13:8020/chinaunicom/ttttt02.txt");
	}
}
