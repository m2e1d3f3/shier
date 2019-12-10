package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasuUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService{

	@Reference
	private TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;
	@Override
	public List<EasuUiTree> showCategory(long id) {
		// TODO Auto-generated method stub
		List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selByPid(id);
		List<EasuUiTree> listTree=new ArrayList<>();
		for (TbContentCategory cate : list) {
			EasuUiTree tree=new EasuUiTree();
			tree.setId(cate.getId());
			tree.setState(cate.getIsParent()?"closed":"open");
			tree.setText(cate.getName());
			listTree.add(tree);
		}
		return listTree;
	}
	@Override
	public EgoResult create(TbContentCategory cate) {
		// TODO Auto-generated method stub
		EgoResult er=new EgoResult();
		//判断名字是否重复
		List<TbContentCategory> children = tbContentCategoryDubboServiceImpl.selByPid(cate.getParentId());
		for (TbContentCategory child : children) {
			if (cate.getName().equals(child.getName())) {
				er.setData("名字重复");
				return er;
			}
		}
		
		Date date=new Date();
		long id = IDUtils.genItemId();
		cate.setCreated(date);
				cate.setId(id);
				cate.setIsParent(false);
				cate.setSortOrder(1);
				cate.setStatus(1);
				cate.setUpdated(date);
				
		
		int index = tbContentCategoryDubboServiceImpl.insTbContentCategory(cate);
		if (index>0) {
			TbContentCategory parent=new TbContentCategory();
			parent.setId(cate.getParentId());
			parent.setIsParent(true);
			tbContentCategoryDubboServiceImpl.updIsParentById(parent);
			
			
		}
		Map<String, Long> map=new HashMap<String, Long>();
		map.put("id", id);
		er.setData(map);
		er.setStatus(200);
		return er;
	}
	@Override
	public EgoResult update(TbContentCategory cate) {
		// TODO Auto-generated method stub
		
		EgoResult er=new EgoResult();
		//判断名字是否重复
		TbContentCategory cateSelect = tbContentCategoryDubboServiceImpl.selById(cate.getId());
		List<TbContentCategory> children = tbContentCategoryDubboServiceImpl.selByPid(cateSelect.getParentId());
		for (TbContentCategory child : children) {
			if (cate.getName().equals(child.getName())) {
				er.setData("名字重复");
				return er;
			}
		}
		int index = tbContentCategoryDubboServiceImpl.updIsParentById(cate);
		if (index>0) {
			er.setStatus(200);
		}
		return er;
	}
	@Override
	public EgoResult delete(TbContentCategory cate) {
		// TODO Auto-generated method stub
		EgoResult er=new EgoResult();
		cate.setStatus(0);
		int index = tbContentCategoryDubboServiceImpl.updIsParentById(cate);
		
		
		if (index>0) {
			TbContentCategory my = tbContentCategoryDubboServiceImpl.selById(cate.getId());
			List<TbContentCategory> allList = tbContentCategoryDubboServiceImpl.selByPid(my.getParentId());
			if (allList==null||allList.size()==0) {
				TbContentCategory parent=new TbContentCategory();
				parent.setId(my.getParentId());
				parent.setIsParent(false);
				tbContentCategoryDubboServiceImpl.updIsParentById(parent);
			}
			er.setStatus(200);
		}
		return er;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
