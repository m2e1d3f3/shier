package com.ego.item.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.pojo.PortalMenuNode;
import com.ego.item.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
@Service
public class TbItemCatServiceImpl implements TbItemCatService{
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;

	@Override
	public PortalMenu showCatMenu() {
		// TODO Auto-generated method stub
		//查询所有一件菜单
		List<TbItemCat> list = tbItemCatDubboServiceImpl.show(0);
		PortalMenu pm=new PortalMenu();
		pm.setData(selAllMenu(list));
		 System.out.println("service京东方");
		return pm;
	}
	//最终返回所有查询的结果
	public List<Object> selAllMenu(List<TbItemCat> list) {
		List<Object> listNode=new ArrayList<>();
		
		for (TbItemCat tbItemCat : list) {
			PortalMenuNode pmn=new PortalMenuNode();
			if (tbItemCat.getIsParent()) {
				
				pmn.setU("/products/"+tbItemCat.getId()+".html");
				pmn.setN("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				pmn.setI(selAllMenu(tbItemCatDubboServiceImpl.show(tbItemCat.getId())));
				listNode.add(pmn);
			}else {
				listNode.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
			}
		}
		
		return listNode;
		
	}

}










