package com.ego.cart.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;

@Service
public class CartServiceImpl implements CartService{

	

	@Resource
	private	JedisDao jedisDaoImpl;
	
	@Value("${passport.url}")
	private String passportUrl;
	
	@Value("${cart.key}")
	private String cartKey;
	
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	
	
	@Override
	public void addCart(long id, int num, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		List<TbItemChild> list=new ArrayList<>();
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = HttpClientUtil.doPost(passportUrl+token);
		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
	
		//String key=cartKey+((LinkedHashMap)er.getData()).get("username");
		String key=cartKey+((LinkedHashMap)er.getData()).get("username");
		if (jedisDaoImpl.exists(key)) {
			String json = jedisDaoImpl.get(key);
			if (json!=null&&!json.equals("")) {
				list = JsonUtils.jsonToList(json, TbItemChild.class);
				for (TbItemChild tbItemChild : list) {
					if ((long)tbItemChild.getId()==id) {
						
						tbItemChild.setNum(tbItemChild.getNum()+num);
						
						jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
						return ;
					}
					
				}
				
			}
			
		}
		
	//没有key
		TbItem item = tbItemDubboServiceImpl.selById(id);
		TbItemChild child = new TbItemChild();
		child.setId(item.getId());
		child.setTitle(item.getTitle());
		child.setPrice(item.getPrice());
		child.setNum(num);
		child.setImages(item.getImage()==null||item.getImage().equals("")?new String[1]:item.getImage().split(","));
		
		list.add(child);
		jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
		
		
	}


	@Override
	public List<TbItemChild> showCart(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = HttpClientUtil.doPost(passportUrl+token);
	  EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
	String key=cartKey+((LinkedHashMap)er.getData()).get("username");
	
	String json = jedisDaoImpl.get(key);
	
		
		return JsonUtils.jsonToList(json, TbItemChild.class);
	}

	@Override
	public EgoResult update(long id, int num, HttpServletRequest request) {
		// TODO Auto-generated method stub
		String token = CookieUtils.getCookieValue(request,"TT_TOKEN" );
		String jsonUser = HttpClientUtil.doPost(passportUrl+token);
		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
		String key=cartKey+((LinkedHashMap)er.getData()).get("username");
		String json = jedisDaoImpl.get(key);
		List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);
		for (TbItemChild tbItemChild : list) {
			if ((long)tbItemChild.getId()==id) {
				tbItemChild.setNum(num);
				
			}
			
		}
		String ok = jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
		EgoResult eResult=new EgoResult();
		if (ok.equals("OK")) {
			 eResult.setStatus(200);
			
		}
		return  eResult;
	}


	@Override
	public EgoResult delete(long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		String token = CookieUtils.getCookieValue(request,"TT_TOKEN" );
		String jsonUser = HttpClientUtil.doPost(passportUrl+token);
		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
		String key=cartKey+((LinkedHashMap)er.getData()).get("username");
		String json = jedisDaoImpl.get(key);
		List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);
		TbItemChild child=null;
		for (TbItemChild tbItemChild : list) {
			if ((long)tbItemChild.getId()==id) {
				child=tbItemChild;
				
			}
			
		}
		list.remove(child);
		String ok = jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
		EgoResult eResult=new EgoResult();
		if (ok.equals("OK")) {
			 eResult.setStatus(200);
			
		}
		return  eResult;
	}

}























