package com.ego.item.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemServiceImpl implements TbItemService{
	
	@Resource
	private JedisDao JedisDaoImpl;
	@Value("${redis.item.key}")
	private String itemKey;
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;

	@Override
	public TbItemChild show(long id) {
		// TODO Auto-generated method stub
		String key=itemKey+id;
		if (JedisDaoImpl.exists(key)) {
			String json = JedisDaoImpl.get(key);
			if (json!=null&&!json.equals("")) {
				return JsonUtils.jsonToPojo(json, TbItemChild.class);
			}
		}
		
		
		TbItemChild child=new TbItemChild();
		TbItem item = tbItemDubboServiceImpl.selById(id);
		child.setId(item.getId());
		child.setSellPoint(item.getSellPoint());
		child.setTitle(item.getTitle());
		child.setPrice(item.getPrice());
		
		child.setImages(item.getImage()!=null&&!item.getImage().equals("")?item.getImage().split(","):new String[1]);
		
		JedisDaoImpl.set(key, JsonUtils.objectToJson(child));
		return child;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
