package com.asainfo.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.log4j.Logger;

public class HdfsUtil {
	private static final Logger LOG = Logger.getLogger(HdfsUtil.class);
	private FileSystem fs = null;

	public void init() {
		Configuration conf = new Configuration();
		// conf.addResource("hdfs-site.xml");
		System.setProperty("hadoop.home.dir",
				"E:\\java开发包\\hadoop-2.6.0-x64\\hadoop-2.6.0");
		//conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
		try {
			fs = FileSystem.get(new URI("hdfs://172.16.11.13:8020"), conf,
					"hdfs");
		} catch (Exception e) {
			LOG.info("hdfs初始化失败:" + e.getMessage());
		}
	}

	
	public static void createFile(File localPath, String hdfsPath) throws IOException {    
        InputStream in = null;    
        try {    
            Configuration conf = new Configuration();   
            System.setProperty("hadoop.home.dir",
    				"E:\\java开发包\\hadoop-2.6.0-x64\\hadoop-2.6.0");
            FileSystem fileSystem = FileSystem.get(URI.create(hdfsPath), conf,"hdfs");    
            FSDataOutputStream out = fileSystem.create(new Path(hdfsPath));    
            fileSystem.copyFromLocalFile(new Path(localPath.getAbsolutePath()),new Path(hdfsPath) );
            fileSystem.close();
        }catch(Exception e){
        	
        }
        finally {    
        }    
    }  

	// 删除文件
	public boolean delete(String path) {
		init();
		try {
			fs.delete(new Path(path), true);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
