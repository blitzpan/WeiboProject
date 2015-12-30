package com.myweibo.entity;

import org.springframework.stereotype.Component;

@Component
public class WeiboEntity {
	private String wbId;
	private String userId;
	
	public String getWbId() {
		return wbId;
	}
	public void setWbId(String wbId) {
		this.wbId = wbId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
