package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.mapper.TbItemCatMapper;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;
import com.ego.pojo.TbItemParam;

public class TbItemCatDubboServiceImpl implements TbItemCatDubboService{
@Resource
private TbItemCatMapper tbItemCatMapper;

	@Override
	public List<TbItemCat> show(long pid) {
		// TODO Auto-generated method stub
		TbItemCatExample example=new TbItemCatExample();
		example.createCriteria().andParentIdEqualTo(pid);
		
		
		return tbItemCatMapper.selectByExample(example);
	}
	@Override
	public TbItemCat selById(long id) {
		// TODO Auto-generated method stub
		return tbItemCatMapper.selectByPrimaryKey(id);
	}

}







