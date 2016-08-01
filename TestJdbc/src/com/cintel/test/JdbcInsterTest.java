package com.cintel.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 MySql ����(insert)���ܲ���
 Oracle ����(insert)���ܲ���

 MySql������䣺
 CREATE  TABLE `dev`.`test_insert` (
 `id` INT NOT NULL ,
 `uname` VARCHAR(10) NULL ,
 PRIMARY KEY (`id`) )
 ENGINE = InnoDB;
 */
public class JdbcInsterTest {

	static int count = 100000;//�ܴ���

	//һ��ҪдrewriteBatchedStatements������Mysql������������ܲ�����
	static String mySqlUrl = "jdbc:postgresql://127.0.0.1:3306/WebDevelopment";
	static String mySqlUserName = "root";
	static String mySqlPassword = "111111";

	static String oracleurl = "jdbc:oracle:thin:@192.168.10.139:1521:orcl";
	static String oracleuserName = "scott";
	static String oraclepassword = "tiger";

	static String sql = "insert into test_insert(id,uname) values(?,?)";

	//ÿִ�м����ύһ��
//	static int[] commitPoint = { count, 10000, 1000, 100, 10, 1 };

	public static void main(String[] args) {
//		for (int point : commitPoint) {
//			test_mysql(point);
//		}
//		for (int point : commitPoint) {
//			test_mysql_batch(point);
//		}
//		//    	for(int point:commitPoint){
//		//            test_oracle(point);  
//		//    	}
//		//    	for(int point:commitPoint){
//		//            test_oracle_batch(point);  
//		//    	}
		
		test_oracle_batch(1000);
	}

	/**
	 * ��������
	 * @return
	 */
	public static Connection getConn(String flag) {
		long a = System.currentTimeMillis();
		try {
			if ("mysql".equals(flag)) {
				Class.forName("org.postgresql.Driver");
				Connection conn = DriverManager.getConnection(mySqlUrl,
						mySqlUserName, mySqlPassword);
				conn.setAutoCommit(false);
				return conn;
			} else if ("oracle".equals(flag)) {
				Class.forName("oracle.jdbc.OracleDriver");
				Connection conn = DriverManager.getConnection(oracleurl,
						oracleuserName, oraclepassword);
				conn.setAutoCommit(false);
				return conn;
			} else {
				System.out.println();
				throw new RuntimeException("flag��������ȷ,flag=" + flag);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			long b = System.currentTimeMillis();
			System.out.println("����������ʱ" + (b - a) + " ms");
		}
		return null;
	}

	/**
	 * �ر�����
	 * @return
	 */
	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ɾ��������
	 * @return
	 */
	public static void clear(Connection conn) {
		try {
			Statement st = conn.createStatement();
			boolean bl = st.execute("delete FROM test_insert");
			conn.commit();
			st.close();
			System.out.println("ִ�����������" + (bl == false ? "�ɹ�" : "ʧ��"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ӡ��Ϣ
	 * @return
	 */
	public static void print(String key, long startTime, long endTime, int point) {
		System.out.println("ÿִ��" + point + "��sql�ύһ������");
		System.out.println(key + "����ʱ" + (endTime - startTime) + " ms,ƽ��ÿ��ִ��"
				+ (count * 1000 / (endTime - startTime)) + "��");
		System.out.println("----------------------------------");
	}

//	/** 
//	 * mysql����������10������¼ 
//	 */
//	public static void test_mysql(int point) {
//		Connection conn = getConn("mysql");
//		clear(conn);
//		try {
//			PreparedStatement prest = conn.prepareStatement(sql);
//			long a = System.currentTimeMillis();
//			for (int x = 1; x <= count; x++) {
//				prest.setInt(1, x);
//				prest.setString(2, "����");
//				prest.execute();
//				if (x % point == 0) {
//					conn.commit();
//				}
//			}
//			long b = System.currentTimeMillis();
//			print("MySql����������10������¼", a, b, point);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			close(conn);
//		}
//	}
//
//	/** 
//	 * mysql��������10������¼ 
//	 */
//	public static void test_mysql_batch(int point) {
//		Connection conn = getConn("mysql");
//		clear(conn);
//		try {
//			PreparedStatement prest = conn.prepareStatement(sql);
//			long a = System.currentTimeMillis();
//			for (int x = 1; x <= count; x++) {
//				prest.setInt(1, x);
//				prest.setString(2, "����");
//				prest.addBatch();
//				if (x % point == 0) {
//					prest.executeBatch();
//					conn.commit();
//				}
//			}
//			long b = System.currentTimeMillis();
//			print("MySql��������10������¼", a, b, point);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			close(conn);
//		}
//	}
//
//	/** 
//	 * oracle����������10������¼ 
//	 */
//	public static void test_oracle(int point) {
//		Connection conn = getConn("oracle");
//		clear(conn);
//		try {
//			PreparedStatement prest = conn.prepareStatement(sql);
//			long a = System.currentTimeMillis();
//			for (int x = 1; x <= count; x++) {
//				prest.setInt(1, x);
//				prest.setString(2, "����");
//				prest.execute();
//				if (x % point == 0) {
//					conn.commit();
//				}
//			}
//			long b = System.currentTimeMillis();
//			print("Oracle����������10���¼", a, b, point);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			close(conn);
//		}
//	}
//
	/** 
	 * oracle��������10������¼ 
	 */
	public static void test_oracle_batch(int point) {
		Connection conn = getConn("oracle");
		clear(conn);
		try {
			PreparedStatement prest = conn.prepareStatement(sql);
			long a = System.currentTimeMillis();
			for (int x = 1; x <= count; x++) {
				prest.setInt(1, x);
				prest.setString(2, "����");
				prest.addBatch();
				if (x % point == 0) {
					prest.executeBatch();
					conn.commit();
				}
			}
			long b = System.currentTimeMillis();
			print("Oracle��������10���¼", a, b, point);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(conn);
		}
	}
}
