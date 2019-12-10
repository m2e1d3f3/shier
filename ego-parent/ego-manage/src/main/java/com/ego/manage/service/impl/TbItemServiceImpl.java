package com.ego.manage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;
@Service
public class TbItemServiceImpl implements TbItemService{
	@Value("${search.url}")
	private String url;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.item.key}")
	private String itemKey;
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	
	

	@Override
	public EasyUiDataGrid show(int page, int rows) {
		// TODO Auto-generated method stub
		return tbItemDubboServiceImpl.show(page, rows);
	}

	@Override
	public int update(String ids, byte status) {
		// TODO Auto-generated method stub
		int index=0;
		TbItem tbItem=new TbItem();
		String[] idsStr = ids.split(",");
		for (String id : idsStr) {
			
			tbItem.setId(Long.parseLong(id));
			tbItem.setStatus(status);
			index+=tbItemDubboServiceImpl.updItemStatus(tbItem);
			if (status==2||status==3) {
				jedisDaoImpl.del(itemKey+id);
				
			}
		}
		if (index==idsStr.length) {
			return 1;
		}
		return 0;
	}

	@Override
	public int save(TbItem tbItem, String desc,String itemParam) throws Exception {
		// TODO Auto-generated method stub
		long id = IDUtils.genItemId();
		tbItem.setId(id);
		Date date=new Date();
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		tbItem.setStatus((byte)1);
		
		TbItemDesc tbItemDesc=new TbItemDesc();
		tbItemDesc.setItemId(id);
		tbItemDesc.setCreated(date);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setUpdated(date);
		
		TbItemParamItem paramItem=new TbItemParamItem();
		paramItem.setCreated(date);
	    paramItem.setItemId(id);
		paramItem.setParamData(itemParam);
		System.out.println("impl"+itemParam);
		paramItem.setUpdated(date);
		
		
		int index=0;
	 index = tbItemDubboServiceImpl.insTbItemDesc(tbItem, tbItemDesc,paramItem);
	 
	 final TbItem itemFinal=tbItem;
	 final String descFinal=desc;
	 
	 new Thread(){
		 public void run(){
			 Map<String, Object> map=new HashMap<>();
			 map.put("item", itemFinal);
			 map.put("desc", descFinal);
			 
			 HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(map));
			 
		 }
	 }.start();
		return index;
		
	}

}
