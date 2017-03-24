package com.asainfo.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.asainfo.pojo.ChinaunicomResult;
import com.asainfo.pojo.Phone;

public class HbaseUtils {
	public static Configuration configuration;
	public static Connection connection;
	public static Admin admin;

	static {
		System.setProperty("hadoop.home.dir",
				"E:\\java开发包\\hadoop-2.6.0-x64\\hadoop-2.6.0");
		configuration = HBaseConfiguration.create();
		configuration
				.set("hbase.zookeeper.quorum",
						"dmp-storm-01.ocdp.asiainfo.com,dmp-storm-02.ocdp.asiainfo.com,dmp-storm-03.ocdp.asiainfo.com,dmp-storm-04.ocdp.asiainfo.com,dmp-storm-05.ocdp.asiainfo.com,dmp-dn-01.ocdp.asiainfo.com,dmp-dn-02.ocdp.asiainfo.com,dmp-dn-03.ocdp.asiainfo.com,dmp-dn-04.ocdp.asiainfo.com,dmp-dn-05.ocdp.asiainfo.com,dmp-dn-06.ocdp.asiainfo.com,dmp-dn-07.ocdp.asiainfo.com,dmp-dn-08.ocdp.asiainfo.com,dmp-dn-09.ocdp.asiainfo.com,dmp-dn-10.ocdp.asiainfo.com,dmp-dn-11.ocdp.asiainfo.com,dmp-nn-01.ocdp.asiainfo.com,dmp-nn-02.ocdp.asiainfo.com");
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		configuration.set("zookeeper.znode.parent", "/hbase-unsecure");
	}

	// 初始化链接
	/*public static void init() {
		try {
			//connection = HConnectionManager.createConnection(configuration);
			connection = ConnectionFactory.createConnection(
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	public static synchronized Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				ExecutorService pool = Executors.newFixedThreadPool(5);//建立一个数量为5的线程池  
				connection = ConnectionFactory.createConnection(configuration,pool);
			}
		} catch (IOException e) {
			return null;
		}
		return connection;
	}

	// 关闭连接
	public static void close() {
		try {
			if (null != connection){
				System.out.println("连接关闭");
				connection.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 建表
	public static ChinaunicomResult createTable(String tableNmae, String[] cols) {
		connection = getConnection();
		TableName tableName = TableName.valueOf(tableNmae);

		try {
			admin = connection.getAdmin();
			if (admin.tableExists(tableName)) {
				return ChinaunicomResult.build(404, "表已经存在");
			}
			HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
			for (String col : cols) {
				HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
				hTableDescriptor.addFamily(hColumnDescriptor);
			}
			admin.createTable(hTableDescriptor);
			if (null != admin)
				admin.close();
		} catch (IOException e) {
			return ChinaunicomResult.build(404, "建表失败:" + e.getMessage());
		}
		
		return ChinaunicomResult.build(200, "表创建成功");
	}

	// 删表
	public static ChinaunicomResult deleteTable(String tableName) {
		connection = getConnection();
		TableName tn = TableName.valueOf(tableName);
		try {
			if (admin.tableExists(tn)) {
				admin.disableTable(tn);
				admin.deleteTable(tn);
			}
		} catch (IOException e) {
			return ChinaunicomResult.build(404, "删除表失败");
		}
		close();
		return ChinaunicomResult.build(200, "删除表成功");
	}

	// 查看已有表
	public static void listTables() throws IOException {
		connection = getConnection();
		HTableDescriptor hTableDescriptors[] = admin.listTables();
		for (HTableDescriptor hTableDescriptor : hTableDescriptors) {
			System.out.println(hTableDescriptor.getNameAsString());
		}
		close();
	}

	public static Integer insertRows(String tableName, List<Put> list) {
		connection = getConnection();
		try {

			Table table = connection.getTable(TableName.valueOf(tableName));
			table.put(list);
			table.close();
			close();
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	// 插入数据
	public static Integer insterRow(String tableName, String rowkey,
			String colFamily, String col, String val) {
		connection = getConnection();
		try {
			Table table = connection.getTable(TableName.valueOf(tableName));

			Put put = new Put(Bytes.toBytes(rowkey));
			put.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(col),
					Bytes.toBytes(val));
			table.put(put);
			table.close();
			close();
			return 1;
		} catch (IOException e) {
			return 0;
		}
	}

	// 删除数据
	public static Integer deleRow(String tableName, String rowkey,
			String colFamily, String col) {
		connection = getConnection();
		try {
			Table table = connection.getTable(TableName.valueOf(tableName));
			Delete delete = new Delete(Bytes.toBytes(rowkey));
			// 删除指定列族
			if (null != colFamily) {
				delete.addFamily(Bytes.toBytes(colFamily));
				// 删除指定列
				if (null != col) {
					delete.addColumn(Bytes.toBytes(colFamily),
							Bytes.toBytes(col));
				}
			}
			table.delete(delete);
			// 批量删除
			/*
			 * List<Delete> deleteList = new ArrayList<Delete>();
			 * deleteList.add(delete); table.delete(deleteList);
			 */
			table.close();
			close();
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	// 根据rowkey查找数据
	public static Object getData(String tableName, String rowkey,
			String colFamily, String col, Class clazz) {
		connection = getConnection();
		Object obj = null;
		try {
			Table table;
			table = connection.getTable(TableName.valueOf(tableName));
			Get get = new Get(Bytes.toBytes(rowkey));
			// 获取指定列族数据
			if (null != colFamily) {
				get.addFamily(Bytes.toBytes(colFamily));
				// 获取指定列数据
				if (null != col) {
					get.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(col));
				}
			}
			Result result = table.get(get);
			obj = showCell(result, clazz);
			table.close();
			close();
		} catch (IOException e) {
			return null;
		}
		return obj;
	}

	// 格式化输出
	public static Object showCell(Result result, Class clazz) {
		try {
			Object  p  = clazz.newInstance();
			Cell[] cells = result.rawCells();
			if (cells.length <= 0) {
				return null;
			}
			Method[] m = clazz.getMethods();
			for (Cell cell : cells) {
				for (Method method : m) {
					String mn = "set"
							+ captureName(Bytes.toString(CellUtil.cloneQualifier(cell)));
					if (method.getName().equals(mn)) {
						String value = Bytes.toString(CellUtil.cloneValue(cell));
						if (null != value || "".equals(value.trim())) {
							method.invoke(p, value);
						}
					}
				}
			}
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 批量查找数据
	public static void scanData(String tableName, String startRow,
			String stopRow) throws IOException {
		connection = getConnection();
		Table table = connection.getTable(TableName.valueOf(tableName));
		Scan scan = new Scan();
		// scan.setStartRow(Bytes.toBytes(startRow));
		// scan.setStopRow(Bytes.toBytes(stopRow));
		ResultScanner resultScanner = table.getScanner(scan);
		for (Result result : resultScanner) {
			showCell(result, null);
		}
		table.close();
		close();
	}

	//条件批量查询 根据前缀查询
	public static List<Object>  getDataByPrefix(String tablename,String prefix,Class clazz,String startrow,String endrow){
		try {
			connection = getConnection();
			Table table = connection.getTable(TableName.valueOf(tablename));
			Scan scan = new Scan();
			//设置查询的前缀
			scan.setRowPrefixFilter(Bytes.toBytes(prefix));
			if(startrow != null && endrow != null && !"".equals(startrow)&&!"".equals(endrow)){
				
				System.out.println("======================"+startrow);
				System.out.println("======================"+endrow);
				//设置开始
				scan.setStartRow(Bytes.toBytes(prefix+startrow));
				//设置结束位置
				scan.setStopRow(Bytes.toBytes(prefix+endrow));
			}
			ResultScanner rs = table.getScanner(scan);
			List list = new ArrayList();
			for (Result result : rs) {
				Object objs  = showCell(result, clazz);
				list.add(objs);
			}
			table.close();
			close();
			return list;
		} catch (IOException e) {
			return null;
		}		
	}
	
	
	public static String captureName(String name) {
		/*
		 * name = name.substring(0, 1).toUpperCase() + name.substring(1); return
		 * name;
		 */
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}
}