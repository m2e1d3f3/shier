package com.ego.item.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.service.TbItemDescService;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemDescServiceImpl implements TbItemDescService{

	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.desc.key}")
	private String descKey;
	@Override
	public String showDesc(long itemId) {
		// TODO Auto-generated method stub
		String key=itemId+descKey;
		if (jedisDaoImpl.exists(key)) {
			String json = jedisDaoImpl.get(key);
			if (json!=null&&!json.equals("")) {
				return json;
				
			}
		}
		String desc = tbItemDubboServiceImpl.selByItemId(itemId).getItemDesc();
		jedisDaoImpl.set(key, desc);
		return desc;
	}

}
