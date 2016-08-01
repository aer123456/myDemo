package com.cintel.test;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.sql.ResultSet;
// import java.sql.PreparedStatement;
// import java.text.SimpleDateFormat;
// import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cintel.test.ConnectionPool.PooledConnection;

public class JDBC implements Runnable
{

	public static String JDBC_DRIVER = "" ;//= ServiceConfig.getJdbcDriver().toString();
	public static String JDBC_URL = "";//ServiceConfig.getJdbcUrl().toString();
	public static String JDBC_USER ="";// ServiceConfig.getJdbcUser().toString();
	public static String JDBC_PWD ="";// ServiceConfig.getJdbcPwd().toString();
	private static Integer sum = 0;
	private static Integer suc = 0;
	private static boolean bStop = false;
	private static int x = 0;
	SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String type = "";
	private static String proId = "1";

	public void setType(String type)
	{
		this.type = type;
	}

	private static long delayTotal = 0;

	public void run()
	{
		Date stdate = new Date();
		String date = simpleFormate.format(stdate);

		Long starttime = stdate.getTime() / 1000;
		// String time01 = simpleFormate.format(new Date());
		// System.out.println("------------time01 == " + time01);

		Long time = stdate.getTime() / 1000;
		long time2 = 0;
		long count = 0;
		try
		{
			while (!bStop)
			{
				
				Long time01 = new Date().getTime();
				if ("select".equals(type))
				{
					select();
				} else if ("insert".equals(type))
				{
					insert();
				} else if ("update".equals(type))
				{
					update();
				} else if ("delete".equals(type))
				{
					delete();
				}
				// select();
				time2 = new Date().getTime() / 1000;

				Long time02 = new Date().getTime();
				long delay = time02 - time01;
				delayTotal += delay;
				// String time02 = simpleFormate.format(new Date());
				// System.out.println("------------time01 == " + time01);
				// System.out.println("------------time02 == " + time02);
				// System.out.println("----------------rn " + (time2 - time));
				if (count == 1000)
				{
					System.out.println("�������ͣ�" + type + ",��ʼʱ�䣺[" + date
							+ "],����ʱ�䣺[" + simpleFormate.format(new Date())
							+ "]," + "sum_num:" + sum + ", suc_num:" + suc
							+ ", fai_num:" + (sum - suc) + ", tps:" + sum
							/ (time2 - starttime) + ",ʱ�ӣ�" + delayTotal / sum);
					time = time2;
					// time01 = time02;
					count =0;
				}
				count ++;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException
	{

		int threadNum = 40;
		if (args != null && args.length > 0)
		{
			threadNum = Integer.parseInt(args[0]);
		}
		String type = "select";
		if (args != null && args.length > 1)
		{
			type = args[1];
		}

		if (args != null && args.length > 2)
		{
			proId = args[2];
			if(proId.length()<2){
				proId = "0" + proId;
			}
		}
		ExecutorService exec = Executors.newFixedThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++)
		{
			JDBC thread = new JDBC();
			thread.setType(type);
			exec.execute(thread);
		}
		exec.shutdown();
		// System.out.println("----2---- exec.isTerminated() = " +
		// exec.isTerminated());
		while (!exec.isTerminated())
		{
			// �ȴ��������߳̽��������˳����߳�

		}

	}

	public static synchronized void insert()
	{
		// -- ����tagId��uniqueId�Զ�����'BOT000000000xxxxxxxx'��ĩβ8λ˳�����00000000 ~
		// 99999999

		String tagId = "BOT0000000" + proId;
		int t = x++;
		// System.out.println(t);
		if ((t + "").length() < 8)
		{
			for (int i = 0; i < 8 - (t + "").length(); i++)
			{
				tagId += "0";
			}
		}
		tagId += t + "";
		// System.out.println("-----------tagId == " + tagId);

		String sql = "insert into S_PH_BotTag_2(merchant_id,merchant_password.merchant_phone,merchant_description) "
				+ "values('"
				+ tagId
				+ "','"
				+ tagId
				+ "','PRO0000001','BOX0000001')";

		int count = 0;
		Connection conn = getConnection();
		PreparedStatement pstm = null;
		try
		{
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			close(null, pstm, conn);
		}
		if (count == 0)
		{
			suc++;
		}
		sum++;
		if (x > 299999)
		{//
			bStop = true;
		}
	}

	public void update()
	{
		// -- ����tagId='BOT0000000000xxxxxx'��ĩβ7λ�����Χ000000 ~ 5000000

		String tagId = "BOT0000000000";
		int rd = (int) (1 + Math.random() * 5000000);

		if ((rd + "").length() != 7)
		{
			for (int i = 0; i < 7 - (rd + "").length(); i++)
			{
				tagId += "0";
			}
		}
		tagId += rd + "";

		String sql = "update S_PH_BotTag_2 set billId='PRO0000002',usingFlag=2 where tagId='"
				+ tagId + "'";
		int count = 0;
		Connection conn = getConnection();
		PreparedStatement pstm = null;
		try
		{
			pstm = conn.prepareStatement(sql);
			count = pstm.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			close(null, pstm, conn);
		}
		if (count == 1)
		{
			suc++;
		}
		sum++;
	}

	public void select()
	{

		// -- ����tagId='BOT0000000000xxxxxx'��ĩβ7λ�����Χ000000 ~ 5000000
		String tagId = "BOT0000000000";
		int rd = (int) (1 + Math.random() * 5000000);

		if ((rd + "").length() != 7)
		{
			for (int i = 0; i < 7 - (rd + "").length(); i++)
			{
				tagId += "0";
			}
		}
		tagId += rd + "";
		// tagId += "050000";

		String sql = "select tagId,uniqueId,billId,boxUniqueId,boxTagId,usingFlag,logisticsFlag,authKey,"
				+ "authNum,custCode,custType "
				+ "from S_PH_BotTag_2 "
				+ "where tagId= '" + tagId + "'";

		// PooledConnection conn = null;
		// PreparedStatement statement = null;
		// try {
		// conn = DBManager.getConnection();
		// // ��ѯĳҳ����
		// statement = conn.getStatement(sql);
		//            
		// result = statement.executeUpdate();
		//
		// }

		Connection conn = getConnection();
		ResultSet rs = null;
		Statement statement = null;
		try
		{

			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			if (rs != null)
			{
				suc++;
			}
			sum++;
			// System.out.println("-------------t == " + t);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			close(rs, statement, conn);
			// closeQuietly(statement);
			// conn.close();
		}
	}

	public void delete()
	{
		// -- ����tagId='BOT0000000 + proId + xxxxxxxx'��ĩβ8λ˳�����00000000 ~ 99999999
		String tagId = "BOT0000000" + proId;
		int t = x++;
		// System.out.println(t);
		if ((t + "").length() < 8)
		{
			for (int i = 0; i < 8 - (t + "").length(); i++)
			{
				tagId += "0";
			}
		}
		tagId += t + "";

		String sql = "delete from S_PH_BotTag_2 where tagId='" + tagId + "'";
		int count = 0;
		Connection conn = getConnection();
		PreparedStatement pstm = null;
		try
		{
			pstm = conn.prepareStatement(sql);
			count = pstm.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			close(null, pstm, conn);
		}
		if (count == 1)
		{
			suc++;
		}
		sum++;

		if (x > 99999999)
		{// ��10���̣߳�����ʵ�ʻ���29�Ž���
			bStop = true;
		}
	}

	public int execute(String sql)
	{
		int result = 0;
		PooledConnection conn = null;
		PreparedStatement statement = null;
		try
		{
			conn = DBManager.getConnection();
			// ��ѯĳҳ����
			statement = conn.getStatement(sql);

			result = statement.executeUpdate();

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			// closeQuietly(statement);
			conn.close();
		}
		return result;
	}

	public static void closeQuietly(ResultSet rs)
	{
		try
		{
			close(rs);
		} catch (Exception e)
		{
			// quiet
		}
	}

	public static void close(ResultSet rs) throws SQLException
	{
		if (rs != null)
		{
			rs.close();
		}
	}

	public static void closeQuietly(Statement stmt)
	{
		try
		{
			close(stmt);
		} catch (Exception e)
		{
			// quiet
		}
	}

	public static void close(Statement stmt) throws SQLException
	{
		if (stmt != null)
		{
			stmt.close();
		}
	}

	// public void run() {
	//
	// int type = 0;
	// int count = 0;
	// int i = 0;
	// int j = 0;
	// Connection conn = null;
	// PreparedStatement pstm = null;
	// Long time = new Date().getTime() / 1000;
	// long time2 = 0;
	// try {
	// conn = getConnection();
	// // String sql = "delete from calllog where ID= ?";
	// String sql = "select
	// tagId,uniqueId,billId,boxUniqueId,boxTagId,usingFlag,logisticsFlag,authKey,authNum,custCode,custType
	// from S_PH_BotTag where tagId='BOT17059283100050000';";
	// type = 0;
	// while (true) {
	// // for (int k = 0; k <2000; k++) {
	//
	// try {
	// if (type == 1) {// DML
	// pstm = conn.prepareStatement(sql);
	// count = pstm.executeUpdate();
	// } else {// SELECT
	// Statement st = null;
	// st = conn.createStatement();
	// // sql = "select * from calllog";
	// ResultSet rs = null;
	// rs = st.executeQuery(sql);
	// if (rs != null) {
	// count = 1;
	// }
	// }
	// if (count == 1) {
	// j++;
	// }
	// i++;
	// time2 = new Date().getTime() / 1000;
	// SimpleDateFormat simpleFormate = new SimpleDateFormat(
	// "yyyy-MM-dd HH:mm:ss");
	// Date stdate = new Date();
	// String date = simpleFormate.format(stdate);
	// if (time2 - time == 10) {
	// System.out.println("[" + date + "]" + "sum_num:" + i
	// + ", suc_num:" + j + ", fai_num:" + (i - j)
	// + ", tps:" + i / 10);
	// time = time2;
	// }
	// } catch (Exception e) {
	// // e.printStackTrace();
	// }
	// }
	// // }
	// } catch (Exception e) {
	// // e.printStackTrace();
	// } finally {
	//
	// close(null, pstm, conn);
	// }
	//
	// }
	//
	// @SuppressWarnings("finally")
	// public static Connection getConnection() {
	// Connection conn = null;
	// try {
	// Class.forName("org.postgresql.Driver");
	// String url = "jdbc:postgresql://192.168.2.239:26661/fdsf";
	// String uname = "cintel";
	// String pwd = "123456";
	// conn = DriverManager.getConnection(url, uname, pwd);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new RuntimeException("���ݿ�����ʧ�ܣ�", e);
	// } finally {
	// return conn;
	// }
	//
	// }
	//
	// public static void close(ResultSet rs, Statement pstm, Connection conn) {
	// if (rs != null) {
	// try {
	// rs.close();
	// } catch (SQLException se) {
	// se.printStackTrace();
	// }
	// }
	// if (pstm != null) {
	// try {
	// pstm.close();
	// } catch (SQLException se) {
	// se.printStackTrace();
	// }
	// }
	// if (conn != null) {
	// try {
	// conn.close();
	// } catch (SQLException se) {
	// se.printStackTrace();
	// }
	// }
	// }

	@SuppressWarnings("finally")
	public static Connection getConnection()
	{
		// Connection conn = null;
		// try{
		// Class.forName(JDBC_DRIVER);
		// conn = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PWD);
		//			
		// }catch(Exception e){
		// e.printStackTrace();
		// throw new RuntimeException("���ݿ�����ʧ�ܣ�",e);
		// }finally{
		// return conn;
		// }
		DBConnectionManager connectionMan = DBConnectionManager.getInstance();
		
		String name = "";
		Connection conn = connectionMan.getConnection(name);
		return conn;

	}

	public static void close(ResultSet rs, Statement pstm, Connection conn)
	{
		if (rs != null)
		{
			try
			{
				rs.close();
			} catch (SQLException se)
			{
				se.printStackTrace();
			}
		}
		if (pstm != null)
		{
			try
			{
				pstm.close();
			} catch (SQLException se)
			{
				se.printStackTrace();
			}
		}
		if (conn != null)
		{

			String name = "";
			DBConnectionManager connectionMan = DBConnectionManager
					.getInstance();
			connectionMan.freeConnection(name, conn);
			// conn.close();

		}
	}

}