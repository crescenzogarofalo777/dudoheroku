package com.perudo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.perudo.dao.DaoFactory;
import com.perudo.dao.UserDao;
import com.perudo.dao.UserDeviceDao;
import com.perudo.dto.UserDeviceDto;
import com.perudo.dto.UserDto;

public class PerudoUserDeviceService {

	
	public UserDeviceDto verifyDeviceUserAssociated(String userName, String deviceId) throws Exception {
		UserDeviceDto result = new UserDeviceDto();
		if(userName == null || "".equals(userName.trim())) {
			throw new RuntimeException("Username null!!!");
		}

		UserDao dao = DaoFactory.getUserDao();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", userName);
		List<UserDto> list = (List<UserDto>) dao.findByCriteria(params);
		
		if (list != null && list.size() > 0) {
			UserDto userDto = list.get(0);
			result.setUserId(userDto.getId());
			UserDeviceDao userDeviceDao = DaoFactory.getUserDeviceDao();
			params.clear();
			params.put("userid", userDto.getId());
			params.put("deviceid", deviceId);
			List<UserDeviceDto> userDevices = userDeviceDao.findByCriteria(params);
			if(userDevices != null && !userDevices.isEmpty()) {
				result = userDevices.get(0);
			} 
		}

		return result;
	}
	
	public UserDeviceDto insertUserDevice(UserDeviceDto userDeviceDto) {
		UserDeviceDao dao = DaoFactory.getUserDeviceDao();
		UserDeviceDto res = null;
		try {
			res = dao.insert(userDeviceDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
}
