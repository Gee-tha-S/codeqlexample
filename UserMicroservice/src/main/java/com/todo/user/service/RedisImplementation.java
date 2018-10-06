package com.todo.user.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.todo.user.model.User;
import com.todo.user.security.JwtTokens;

/*************************************************************************************************************
 *
 * purpose:To store the information in redis database
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/
@Repository
public class RedisImplementation implements RedisRepository {
	@Autowired
	JwtTokens tokens;
	@Autowired
	private RedisTemplate redisTemplate;
	private static HashOperations<String, String, String> hashOperations;
	private static String KEY = "Token";

	@Autowired
	public RedisImplementation(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private static Logger logger = LoggerFactory.getLogger(RedisRepository.class);

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void setToken(String jwtToken) {
		String userId = tokens.parseJwt(jwtToken).getSubject();
		hashOperations.put(KEY, userId, jwtToken);
		logger.info("Token set in redis");
	}

	@Override
	public String getToken(String userId) {
		logger.info("Get token from redis");
		return hashOperations.get(KEY, userId);
	}

	@Override
	public String setUserId(String userId) {
		hashOperations.put(KEY, "userId", userId);
		return null;
	}

	@Override
	public String getUserId(String userId) {

		return hashOperations.get(KEY, userId);
	}

}
