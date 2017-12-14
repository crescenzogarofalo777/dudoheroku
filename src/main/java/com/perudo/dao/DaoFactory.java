package com.perudo.dao;

public class DaoFactory {

	
	private static UserDao userDao;
	private static UserDeviceDao userDeviceDao;
	
	public static UserDao getUserDao() {
		if(userDao == null) {
			userDao = new UserDao();
		}
		return userDao;
	}

	public static UserDeviceDao getUserDeviceDao() {
		if(userDeviceDao == null) {
			userDeviceDao = new UserDeviceDao();
		}
		return userDeviceDao;
	}

}
