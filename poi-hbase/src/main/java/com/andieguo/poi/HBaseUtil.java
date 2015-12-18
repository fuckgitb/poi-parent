package com.andieguo.poi;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;


public class HBaseUtil {
	private Configuration conf ;
	public HBaseUtil(){
		conf = ZHBaseConfiguration.getConfiguration();
	}

	public void create(String tableName, String... families) {
		HBaseAdmin admin = null;
		try {
			admin = new HBaseAdmin(conf);
			HTableDescriptor tableDescriptor = new HTableDescriptor(tableName.getBytes());
			if (admin.tableExists(tableName)) {// 表存在
				System.out.println(tableName + "已经存在！");
			} else {
				for (String family : families) {
					tableDescriptor.addFamily(new HColumnDescriptor(family));
				}
				admin.createTable(tableDescriptor);
			}
			admin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void put(String tabelName,String rowKey,String family,String qualifier,String value) {
		HTable table = null;
		try {
			table = new HTable(conf, tabelName);
			Put putRow1 = new Put(rowKey.getBytes());
			putRow1.add(family.getBytes(), qualifier.getBytes(),value.getBytes());
			table.put(putRow1);
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void put(String tableName,byte[] rowkey,byte[] family,byte[] qualifier,byte[] value){
		HTable table = null;
		try {
			table = new HTable(conf, tableName);
			Put putRow1 = new Put(rowkey);
			putRow1.add(family, qualifier,value);
			table.put(putRow1);
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		HBaseUtil hbaseUtil = new HBaseUtil();
		hbaseUtil.create("tb_poi","info");
	}

}
