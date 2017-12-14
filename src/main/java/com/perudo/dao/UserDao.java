package com.perudo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.perudo.db.DataSource;
import com.perudo.dto.UserDto;
import com.perudo.utils.Constants;


public class UserDao extends GenericDao<UserDto> {

	private static final String USERS_LIST_ALL_QUERY = "select * from users;";
	
	private static final String USERS_SINGLE_QUERY = "select * from users where id = ? ;";
	
	public List<UserDto> readAll() throws Exception {
		Connection conn = DataSource.getConnection();
		PreparedStatement pstm = conn.prepareStatement(USERS_LIST_ALL_QUERY);
		
		ResultSet rs = pstm.executeQuery();
		List<UserDto> resultList = new ArrayList<UserDto>();
		
		if(rs != null) {
			while(rs.next())
			{
				UserDto bean = new UserDto();
				bean.setId(rs.getInt(1));
				bean.setUserName(rs.getString(2));				
				bean.setPassword(rs.getString(3));
				bean.setScore(rs.getDouble(4));
				resultList.add(bean);
			}
		}
		DataSource.returnConnection(conn);
		return resultList;
	}

	public UserDto findByKey(Object id) throws Exception {
		
		Connection conn = DataSource.getConnection();
		PreparedStatement pstm = conn.prepareStatement(USERS_SINGLE_QUERY);
		
		pstm.setString(1, id.toString());
		ResultSet rs = pstm.executeQuery();
		
		UserDto bean = null;
		if(rs != null && rs.next()) {
			bean = new UserDto();
			bean.setId(rs.getInt(1));
			bean.setUserName(rs.getString(2));				
			bean.setPassword(rs.getString(3));
			bean.setScore(rs.getDouble(4));
		}
		DataSource.returnConnection(conn);
		return bean;
	}

	public String getTableName() {
		
		return "users";
		
	}

	@Override
	public List<UserDto> findByCriteria(Map<String, Object> params) throws Exception {
		ResultSet rs = this.findByCriteriaInternal(params);
		List<UserDto> resultList = new ArrayList<UserDto>();
		
		if(rs != null) {
			while(rs.next()) {
				UserDto bean = new UserDto();
				bean.setId(rs.getInt(1));
				bean.setUserName(rs.getString(2));				
				bean.setPassword(rs.getString(3));
				bean.setScore(rs.getDouble(4));
				resultList.add(bean);
			}
		}
		return resultList;
	}

	@Override
	public UserDto insert(UserDto userDto) throws Exception {
		
		Connection conn = DataSource.getConnection();
		String generatedPK[] = {"id"};
		StringBuilder userInsertQuery = new StringBuilder(Constants.USER_INSERT);
		userInsertQuery.append(" (?,?,?)");
		if(conn == null) {
			System.out.println("BE CAREFUL  CONNECTION OBJECT IS NULL!!!!!!!!!!!!!!!1");
		}
		PreparedStatement pstm = conn.prepareStatement(userInsertQuery.toString(),generatedPK);
		pstm.setString(1, userDto.getUserName());
		pstm.setString(2, userDto.getPassword());
		pstm.setDouble(3, userDto.getScore());

		int numUserIns = pstm.executeUpdate();
		ResultSet rsUser = pstm.getGeneratedKeys();
		if(numUserIns > 0 && rsUser != null) {
			if(rsUser.next()) {
				int id = rsUser.getInt(1);
				userDto.setId(id);
			}
		}
		DataSource.returnConnection(conn);
		return userDto;
	}

	@Override
	public boolean update(UserDto dto) throws Exception {
		Connection conn = DataSource.getConnection();
		StringBuilder userUpdateDml = new StringBuilder();
		PreparedStatement pstm = null;
		if(dto.getScore() > 0) {
			userUpdateDml.append(Constants.USER_UPDATE_SCORE);
			pstm = conn.prepareStatement(userUpdateDml.toString());
			pstm.setDouble(1, dto.getScore());
		} else if (dto.getPassword() != null) {
			userUpdateDml.append(Constants.USER_UPDATE_PASSWORD);
			pstm = conn.prepareStatement(userUpdateDml.toString());
			pstm.setString(1, dto.getPassword());
		} else {
			userUpdateDml.append(Constants.USER_UPDATE_ONLINE);
			pstm = conn.prepareStatement(userUpdateDml.toString());
			pstm.setBoolean(1, dto.isOnline());
		}
		pstm.setString(2, dto.getUserName());
		
		int numUpd = pstm.executeUpdate();
		DataSource.returnConnection(conn);
		if(numUpd > 0 ) {
			return true;
		}
		return false;

	}
	
	
}
