package com.todo.user.service;

/*************************************************************************************************************
 *
 * purpose:To store the information in redis database
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/
@SuppressWarnings("hiding")
public interface RedisRepository {

	public void setToken(String jwtToken);

	public String getToken(String userId);

	public String setUserId(String userId);

	String getUserId(String userId);

}
