package com.perudo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.perudo.db.DataSource;
import com.perudo.dto.GenericDto;


public abstract class GenericDao<Dto extends GenericDto> {

	protected final String CRITERIA_QUERY = "select * from ";
	
	public abstract List<? extends GenericDto> readAll() throws Exception;
	
	public abstract GenericDto  findByKey(Object id) throws Exception;
	
	public abstract List<? extends GenericDto> findByCriteria(Map<String,Object> params) throws Exception;
	
	public ArrayList<Dto> insert(List<Dto> dtoList) throws Exception {
		return null;
	};

	public Dto insert(Dto dto) throws Exception {
		return null;
	};

	public boolean update(List<Dto> dtoList) throws Exception {
		return false;
	};

	public boolean update(Dto dto) throws Exception {
		return false;
	};

	public List<? extends GenericDto> insert(Map<String, Object> params) throws Exception {
		return null;
	}
	
	public abstract String getTableName();
	
	protected ResultSet findByCriteriaInternal(Map<String,Object> params) throws Exception {
		
		StringBuilder query = new StringBuilder(CRITERIA_QUERY+" "+getTableName());
		boolean someNoOrderByParam = false;
		ResultSet rs = null;
		if(params != null && !params.isEmpty()) {
			
			Connection conn = DataSource.getConnection();
			int nParams = 0;
			List<Object> paramValues = new ArrayList<Object>();
			Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
			String orderBy = null;
			String limit = null;
			while(iterator.hasNext()) {	
				Entry<String, Object> entry = iterator.next();
				if(!"orderBy".equals(entry.getKey()) && !"limit".equals(entry.getKey())) {
					nParams++;
					if(!someNoOrderByParam) {
						query.append(" where ");
						someNoOrderByParam = true;
					}
					query.append(entry.getKey()+" = ? ");
					paramValues.add(entry.getValue());
					query.append("and ");
				} else if("orderBy".equals(entry.getKey())){
					orderBy = entry.getValue().toString();
				} else if("limit".equals(entry.getKey())){
					limit = entry.getValue().toString();
				}
			}
			if(someNoOrderByParam) {
				query.delete(query.length()-4, query.length());
			}
			if(orderBy != null) {
				query.append(" order by ").append(orderBy);
			}
			if(limit != null) {
				query.append(" limit ").append(limit);
			}
			PreparedStatement pstm = conn.prepareStatement(query.toString());
			
			for(int i=0; i<nParams; i++) {
				pstm.setObject(i+1, paramValues.get(i));
			}
			
			rs = pstm.executeQuery();
			
			DataSource.returnConnection(conn);
		}
		return rs;
	}
}
