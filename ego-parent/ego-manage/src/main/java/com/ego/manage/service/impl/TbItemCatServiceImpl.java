package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasuUiTree;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
@Service
public class TbItemCatServiceImpl implements TbItemCatService{
@Reference
private TbItemCatDubboService tbItemCatDubboServiceImpl;
	@Override
	public List<EasuUiTree> show(long pid) {
		// TODO Auto-generated method stub
		List<TbItemCat> list = tbItemCatDubboServiceImpl.show(pid);
		List<EasuUiTree> listTree=new ArrayList<>();
		for (TbItemCat cat : list) {
			EasuUiTree tree=new EasuUiTree();
			tree.setId(cat.getId());
			tree.setText(cat.getName());
			tree.setState(cat.getIsParent()?"closed":"open");
			listTree.add(tree);
		}
		return listTree;
	}

}
