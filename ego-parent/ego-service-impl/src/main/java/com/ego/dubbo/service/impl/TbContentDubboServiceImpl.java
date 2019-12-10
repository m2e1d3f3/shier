package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbContentDubboServiceImpl implements TbContentDubboService{
	@Resource
	private TbContentMapper tbContentMapper;

	@Override
	public EasyUiDataGrid selContentByPage(long categoryId, int page, int rows) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
	     TbContentExample example=new TbContentExample();
	     if (categoryId!=0) {
	    	 example.createCriteria().andCategoryIdEqualTo(categoryId);
		}
	    List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
	    PageInfo<TbContent> pInfo=new PageInfo<>(list);
	    
	    EasyUiDataGrid grid=new EasyUiDataGrid();
	    grid.setRows(pInfo.getList());
	    grid.setTotal(pInfo.getTotal());
	    
		return grid;
	}

	@Override
	public int insContent(TbContent content) {
		// TODO Auto-generated method stub
		
		
		return tbContentMapper.insertSelective(content);
	}

	@Override
	public List<TbContent> selByCount(int count, boolean isSort) {
		// TODO Auto-generated method stub
		TbContentExample example=new TbContentExample();
		if (isSort) {
			example.setOrderByClause("updated desc");
			
		}
		if (count!=0) {
			PageHelper.startPage(1, count);
			List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
			PageInfo<TbContent> pInfo=new PageInfo<>(list);
			return pInfo.getList();
			
			
		}else {
			return tbContentMapper.selectByExampleWithBLOBs(example);
		}
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
