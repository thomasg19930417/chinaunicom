package com.asainfo.hbase.test;

import java.io.IOException;

import org.apache.derby.iapi.store.access.conglomerate.Conglomerate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.ClusterConnection;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;

public class TestHbase {
	private static Configuration configuration; // 配置对象
	static{
		System.setProperty("hadoop.home.dir", "E:\\java开发包\\hadoop-2.6.0-x64\\hadoop-2.6.0");
		configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.quorum", "dmp-storm-01.ocdp.asiainfo.com,dmp-storm-02.ocdp.asiainfo.com,dmp-storm-03.ocdp.asiainfo.com,dmp-storm-04.ocdp.asiainfo.com,dmp-storm-05.ocdp.asiainfo.com,dmp-dn-01.ocdp.asiainfo.com,dmp-dn-02.ocdp.asiainfo.com,dmp-dn-03.ocdp.asiainfo.com,dmp-dn-04.ocdp.asiainfo.com,dmp-dn-05.ocdp.asiainfo.com,dmp-dn-06.ocdp.asiainfo.com,dmp-dn-07.ocdp.asiainfo.com,dmp-dn-08.ocdp.asiainfo.com,dmp-dn-09.ocdp.asiainfo.com,dmp-dn-10.ocdp.asiainfo.com,dmp-dn-11.ocdp.asiainfo.com,dmp-nn-01.ocdp.asiainfo.com,dmp-nn-02.ocdp.asiainfo.com");
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		configuration.set("zookeeper.znode.parent", "/hbase-unsecure");
		//configuration.
		 
	}
	
	public static void main(String[] args) {
		try {
			HBaseAdmin admin = new HBaseAdmin(configuration);
			System.out.println(admin.tableExists("xyz"));
			/*HTableDescriptor desc = new HTableDescriptor(Bytes.toBytes("testtable1"));
			HColumnDescriptor coldef = new HColumnDescriptor(Bytes.toBytes("colfam1"));
			desc.addFamily(coldef);
			admin.createTable(desc);
			
			boolean avail = admin.isTableAvailable(Bytes.toBytes("testtable1"));
			System.out.println("Table available: "+ avail);*/
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
