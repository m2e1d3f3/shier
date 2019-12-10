package com.ego.redis.dao;

public interface JedisDao {
   /**
    * 判断是否存在
    * @return
    */
	boolean exists(String key);
	 /**
	    * 删除
	    * @return
	    */
	Long del(String key);
	
	/**
	    * 添加
	    * @return
	    */
	String set(String key,String value);
	
	/**
	    * 取值
	    * @return
	    */
	String get(String key);
	
	long expaie(String key,int seconds);
}
