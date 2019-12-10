package com.ego.passport.service.impl;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;

@Service
public class TbUserServiceImpl implements TbUserService{
	
	@Reference
	private TbUserDubboService tbUserDubboServiceImpl;
	
	@Resource
	private JedisDao jedisDaoImpl;

	@Override
	public EgoResult login(TbUser user,HttpServletRequest request,HttpServletResponse response) {
		// TODO Auto-generated method stub
		EgoResult result=new EgoResult();
		TbUser userSelect = tbUserDubboServiceImpl.selBuUser(user);
		if (userSelect!=null&&!userSelect.equals("")) {
			result.setStatus(200);
			//放入Redis
			String key=UUID.randomUUID().toString();
			jedisDaoImpl.set(key, JsonUtils.objectToJson(userSelect));
			jedisDaoImpl.expaie(key, 60*60*24*7);
			//产生cookie
			CookieUtils.setCookie(request, response,"TT_TOKEN" , key, 60*60*24*7);
		}else {
			result.setMsg("用户名或密码错误");
		}
	
	
		return result;
	}

	@Override
	public EgoResult getUserInfoByToken(String token) {
		// TODO Auto-generated method stub
		EgoResult er=new EgoResult();
		String json = jedisDaoImpl.get(token);
		
		if (json!=null&&!json.equals("")) {
			TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
			user.setPassword(null);
			er.setStatus(200);
			er.setMsg("Ok");
			er.setData(user);
		}else {
			er.setMsg("失败");
		}
		
		return er;
	}

	@Override
	public EgoResult logout(String token, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		jedisDaoImpl.del(token);
		CookieUtils.deleteCookie(request, response, "TT_TOKEN");
		EgoResult er=new EgoResult();
		er.setMsg("OK");
		er.setStatus(200);
		return er;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
