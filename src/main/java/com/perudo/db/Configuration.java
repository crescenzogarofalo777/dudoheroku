package com.perudo.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

	public String DB_USER_NAME;

	public String DB_PASSWORD;

	public String DB_URL;
	
	public String DB_HOST;
	public String DB_PORT;

	public String DB_DRIVER;

	public Integer DB_MAX_CONNECTIONS;

	public Configuration() {
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Configuration configuration = new Configuration();

	public static Configuration getInstance() {
		return configuration;
	}

	private void init() throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("conf/database.properties");
		// ...
		Properties properties = new Properties();
		properties.load(input);
		String dbUserName = properties.getProperty("DB_USER_NAME");
		System.out.println("DB_USER_NAME :"+dbUserName);
		String dbPassword = properties.getProperty("DB_PASSWORD");
		System.out.println("DB_PASSWORD :"+dbPassword);
		String dbUrlPrefix = properties.getProperty("DB_URL_PREFIX");
		System.out.println("DB_URL_PREFIX :"+dbUrlPrefix);
		String dbUrl = properties.getProperty("DB_URL");
		System.out.println("DB_URL :"+dbUrl);
		String dbName = properties.getProperty("DB_NAME");
		String dbHost = properties.getProperty("DB_HOST");
		String dbPort = properties.getProperty("DB_PORT");
		System.out.println("DB_NAME :"+dbName);
		DB_USER_NAME = System.getenv(dbUserName);
		System.out.println("DB_USER_NAME :"+DB_USER_NAME);
		DB_PASSWORD = System.getenv(dbPassword);
		System.out.println("DB_PASSWORD :"+DB_PASSWORD);
		DB_HOST = System.getenv(dbHost);
		System.out.println("DB_HOST :"+DB_HOST);
		DB_PORT = System.getenv(dbPort);
		System.out.println("DB_PORT :"+DB_PORT);
		DB_URL = dbUrlPrefix + DB_HOST + ":" + DB_PORT+"/"+dbName;
		System.out.println("DB_URL complete :"+DB_URL);
		DB_DRIVER = properties.getProperty("DB_DRIVER");
		DB_MAX_CONNECTIONS = Integer.valueOf(properties.getProperty("DB_MAX_CONNECTIONS"));
	}
}