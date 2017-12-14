package com.perudo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.perudo.db.DataSource;
import com.perudo.dto.GenericDto;
import com.perudo.dto.UserDeviceDto;
import com.perudo.dto.UserDto;
import com.perudo.utils.Constants;


public class UserDeviceDao extends GenericDao<UserDeviceDto> {

	
	private static final String USERS_X_DEVICES_QUERY = "select * from users_x_devices where userid = ? ;";
	
	public String getTableName() {
		
		return "users_x_devices";
		
	}

	@Override
	public List<UserDeviceDto> findByCriteria(Map<String, Object> params) throws Exception {
		ResultSet rs = this.findByCriteriaInternal(params);
		List<UserDeviceDto> resultList = new ArrayList<UserDeviceDto>();
		
		if(rs != null && rs.next()) {
			UserDeviceDto bean = new UserDeviceDto();
			bean.setUserId(rs.getInt(1));
			bean.getDeviceIds().add(rs.getString(2));				
			while(rs.next()) {
				bean.getDeviceIds().add(rs.getString(2));
			}
			resultList.add(bean);
		}
		return resultList;
	}

	@Override
	public UserDeviceDto insert(UserDeviceDto userDeviceDto) throws Exception {
		
		Connection conn = DataSource.getConnection();
		StringBuilder userDeviceInsertQuery = new StringBuilder(Constants.USER_X_DEVICE_INSERT);
		userDeviceInsertQuery.append(" (?,?)");
		if(conn == null) {
			System.out.println("BE CAREFUL CONNECTION OBJECT IS NULL!!!!!!!!!!!!!!!1");
		}
		PreparedStatement pstm = conn.prepareStatement(userDeviceInsertQuery.toString());
		pstm.setInt(1, userDeviceDto.getUserId());
		pstm.setString(2, userDeviceDto.getDeviceIds().get(0));

		int numUserIns = pstm.executeUpdate();
		DataSource.returnConnection(conn);
		if(numUserIns <= 0) {
			userDeviceDto = null;
		}
		return userDeviceDto;
	}

	@Override
	public List<? extends GenericDto> readAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericDto findByKey(Object id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
