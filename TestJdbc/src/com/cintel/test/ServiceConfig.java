package com.cintel.test;

import java.io.File;

public class ServiceConfig extends BaseConfig {
	
//	private static String PATH =  BaseConfig.class.getClassLoader().getResource("").getPath();
	private static String PATH = System.getProperty("user.home");	//服务器
//	private static String PATH = System.getProperty("user.dir"); 	//本地

	private static String SERVICE_CONFIG_FILE_PATH = PATH + File.separator + "config.properties";
	
	private static String JDBC_DRIVER = "jdbc_driver";
	
	private static String JDBC_URL = "jdbc_url";
	
	private static String JDBC_USER = "jdbc_user";
	
	private static String JDBC_PWD = "jdbc_pwd";
	
	private static String INIT_CONN = "init_conn";
	
	private static String INCRE_CONN = "incre_conn";
	
	private static String MAX_CONN = "max_conn";
	
	
	
	public static String getJdbcDriver() {
//		System.out.println("SERVICE_CONFIG_FILE_PATH == " + SERVICE_CONFIG_FILE_PATH);
		return ServiceConfig.readValue(ServiceConfig.SERVICE_CONFIG_FILE_PATH,
				ServiceConfig.JDBC_DRIVER);
	}
	
	public static String getJdbcUrl() {
		return ServiceConfig.readValue(ServiceConfig.SERVICE_CONFIG_FILE_PATH,
				ServiceConfig.JDBC_URL);
	}
	
	public static String getJdbcUser() {
		return ServiceConfig.readValue(ServiceConfig.SERVICE_CONFIG_FILE_PATH,
				ServiceConfig.JDBC_USER);
	}
	
	public static String getJdbcPwd() {
		return ServiceConfig.readValue(ServiceConfig.SERVICE_CONFIG_FILE_PATH,
				ServiceConfig.JDBC_PWD);
	}
	
	public static String getInitConn() {
		return ServiceConfig.readValue(ServiceConfig.SERVICE_CONFIG_FILE_PATH,
				ServiceConfig.INIT_CONN);
	}
	
	public static String getIncreConn() {
		return ServiceConfig.readValue(ServiceConfig.SERVICE_CONFIG_FILE_PATH,
				ServiceConfig.INCRE_CONN);
	}
	
	public static String getMaxConn() {
		return ServiceConfig.readValue(ServiceConfig.SERVICE_CONFIG_FILE_PATH,
				ServiceConfig.MAX_CONN);
	}

}
