package com.ego.manage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import com.ego.redis.dao.JedisDao;
@Service
public class TbContentServiceImpl implements TbContentService{
@Reference
private TbContentDubboService tbContentDubboServiceImpl;

@Resource
private JedisDao jedisDaoImpl;
@Value("${redis.bigpic.key}")
private String key;
	@Override
	public EasyUiDataGrid showContent(long categoryId, int page, int rows) {
		// TODO Auto-generated method stub
		return tbContentDubboServiceImpl.selContentByPage(categoryId, page, rows);
	}
	@Override
	public int save(TbContent content) {
		// TODO Auto-generated method stub
		Date date=new Date();
		content.setCreated(date);
		content.setUpdated(date);
		int index = tbContentDubboServiceImpl.insContent(content);
		
		if (jedisDaoImpl.exists(key)) {
			String value = jedisDaoImpl.get(key);
			if (value!=null&&!value.equals("")) {
				List<HashMap> list = JsonUtils.jsonToList(value, HashMap.class);
				
				
				HashMap<String, Object> map=new HashMap<String, Object>();
				
				map.put("srcB", content.getPic2());
				map.put("height", 240);
				map.put("alt", "错误");
				map.put("width", 670);
				map.put("src",content.getPic() );
				map.put("widthB", 550);
				map.put("href", content.getUrl());
				map.put("heightB", 240);
				if (list.size()==5) {
					list.remove(4);
				}
				list.add(0,map);
				
				jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
			}
			
		}
		
		//判断是否有缓存数据
		
		return index;
	}

}
