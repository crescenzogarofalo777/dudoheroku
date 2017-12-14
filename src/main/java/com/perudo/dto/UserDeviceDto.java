package com.perudo.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDeviceDto extends GenericDto {

	private int userId;
	private List<String> deviceIds;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<String> getDeviceIds() {
		if(this.deviceIds == null) {
			this.deviceIds = new ArrayList<String>();
		}
		return deviceIds;
	}
	public void setDeviceIds(List<String> deviceIds) {
		this.deviceIds = deviceIds;
	}
}
