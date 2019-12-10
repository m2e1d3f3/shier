package com.ego.redis.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.JedisCluster;

import com.ego.redis.dao.JedisDao;

@Repository
public class JedisDaoImpl implements JedisDao{

	@Resource
	private JedisCluster jedisClients;
	@Override
	public boolean exists(String key) {
		// TODO Auto-generated method stub
		return jedisClients.exists(key);
	}

	@Override
	public Long del(String key) {
		// TODO Auto-generated method stub
		return jedisClients.del(key);
	}

	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return jedisClients.set(key, value);
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return jedisClients.get(key);
	}

	@Override
	public long expaie(String key, int seconds) {
		// TODO Auto-generated method stub
		return jedisClients.expire(key, seconds);
	}

}
