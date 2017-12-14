package com.perudo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.perudo.dao.DaoFactory;
import com.perudo.dao.UserDao;
import com.perudo.dto.UserDto;

public class PerudoUserService {

	public UserDto login(String userName,String passWord) throws Exception {
		UserDto result = null;
		if(userName == null || "".equals(userName.trim()) || passWord == null || "".equals(passWord.trim())) {
			throw new RuntimeException("Username or passworf null!!!");
		}

		UserDao dao = DaoFactory.getUserDao();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", userName);
		List<UserDto> list = (List<UserDto>) dao.findByCriteria(params);
		if (list != null && list.size() > 1) {
			throw new RuntimeException("More than one user found with same username");
		}
		if (list != null && list.size() > 0) {
			result = list.get(0);
			if(!passWord.equals(result.getPassword())) {
				result = null;
			} else {
				result.setPassword(null);
			}
		}

		return result;
		
	}
	
	public UserDto retrieveUser(String userName) throws Exception {
		UserDto result = null;
		if(userName == null || "".equals(userName.trim())) {
			throw new RuntimeException("Username null!!!");
		}

		UserDao dao = DaoFactory.getUserDao();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", userName);
		List<UserDto> list = (List<UserDto>) dao.findByCriteria(params);
		
		if (list != null && list.size() > 0) {
			result = list.get(0);
			result.setPassword(null);
		}

		return result;
	}

	
	public boolean verifyUsernameAvailability(String userName) throws Exception {
		boolean result = true;
		if(userName == null || "".equals(userName.trim())) {
			throw new RuntimeException("Username or passworf null!!!");
		}

		UserDao dao = DaoFactory.getUserDao();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", userName);
		List<UserDto> list = (List<UserDto>) dao.findByCriteria(params);
		
		if (list != null && list.size() > 0) {
			result = false;
		}

		return result;
	}
	
	public UserDto insertUser(UserDto userDto) {
		UserDao dao = DaoFactory.getUserDao();
		UserDto res = null;
		try {
			res = dao.insert(userDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public UserDto updateUser(UserDto userDto) {
		UserDao userDao = DaoFactory.getUserDao();
		UserDto res = null;
		try {
			
			boolean updated = userDao.update(userDto);
			if(updated) {
				res = userDto;
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public List<UserDto> scores() throws Exception {

		UserDao dao = DaoFactory.getUserDao();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderBy", "score desc");
		List<UserDto> list = (List<UserDto>) dao.findByCriteria(params);

		return list;
		
	}
	
	public List<UserDto> retrieveOnlineUsers(int numOfPlayers) throws Exception {

		UserDao dao = DaoFactory.getUserDao();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("online", Boolean.TRUE);
		params.put("orderBy", "random()");
		params.put("limit", new Integer(numOfPlayers));
		List<UserDto> list = (List<UserDto>) dao.findByCriteria(params);

		return list;
		
	}
	
	

}
