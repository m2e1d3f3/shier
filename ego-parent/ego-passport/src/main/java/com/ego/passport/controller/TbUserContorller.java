package com.ego.passport.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;

@Controller
public class TbUserContorller {
	
	@Resource
	private TbUserService tbUserServiceImpl;
/**
 * 显示登录页面
 * @param url
 * @param model
 * @return
 */
	@RequestMapping("user/showLogin")
	public String showLogin(@RequestHeader(value="Referer",defaultValue="") String url,Model model,String interurl)
	{
		if (interurl!=null&&!interurl.equals("")) {
			model.addAttribute("redirect", interurl);
		}else if(url!=null) {
			model.addAttribute("redirect", url);
		}
		
		return "login";
	}
	/**
	 * 登录页面
	 * @param url
	 * @param model
	 * @return
	 */
	@RequestMapping("user/login")
	@ResponseBody
	public EgoResult login(TbUser user,HttpServletRequest request,HttpServletResponse response)
	{
	return	tbUserServiceImpl.login(user,request,response);
	}
	
	/**
	 * 通过 token 获取用户信息
	 * @param url
	 * @param model
	 * @return
	 */
	@RequestMapping("user/token/{token}")
	@ResponseBody
	public Object getUserInfo(@PathVariable String token,String callback)
	{
		EgoResult er = tbUserServiceImpl.getUserInfoByToken(token);
		if (callback!=null&&!callback.equals("")) {
			MappingJacksonValue mjv=new MappingJacksonValue(er);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
	return	er;
	}
	
	/**
	 * 退出
	 * @param url
	 * @param model
	 * @return
	 */
	@RequestMapping("user/logout/{token}")
	@ResponseBody
	public Object logout(@PathVariable String token,String callback,HttpServletRequest request,HttpServletResponse response)
	{
		EgoResult er = tbUserServiceImpl.logout(token, request, response);
		if (callback!=null&&!callback.equals("")) {
			MappingJacksonValue mjv=new MappingJacksonValue(er);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
	return	er;
	}
	
	
	
	
	
	
	
	
	
	
	
}
