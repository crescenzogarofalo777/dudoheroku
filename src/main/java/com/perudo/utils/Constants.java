package com.perudo.utils;

public class Constants {

	public static final String USER_INSERT = "insert into users (username,password,score) values ";
	public static final String USER_X_DEVICE_INSERT = "insert into users_x_devices (userid,deviceid) values ";
	public static final String USER_UPDATE_SCORE = "update users set score = ? where username = ? ";
	public static final String USER_UPDATE_PASSWORD = "update users set password = ? where username = ? ";
	public static final String USER_UPDATE_ONLINE = "update users set online = ? where username = ? ";
	public static final String SCORE_INSERT = "insert into scores (idusername,score) values ";
	public static final String SCORE_UPDATE = "update scores set score = ? where idusername = ? ";

}
