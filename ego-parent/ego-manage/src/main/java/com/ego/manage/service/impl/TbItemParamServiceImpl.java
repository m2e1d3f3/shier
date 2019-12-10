package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemParam;


@Service
public class TbItemParamServiceImpl implements TbItemParamService{
@Reference
private TbItemCatDubboService tbItemCatDubboServiceImpl;
@Reference
private TbItemParamDubboService tbItemParamDubboServiceImpl;
	@Override
	public EasyUiDataGrid showPage(int page, int rows) {
		// TODO Auto-generated method stub
		EasyUiDataGrid dataGrid = tbItemParamDubboServiceImpl.showPage(page, rows);
		List<TbItemParam>  list= (List<TbItemParam>) dataGrid.getRows();
		List<TbItemParamChild> listChild=new ArrayList<>();
		for (TbItemParam tbItemParam : list) {
			TbItemParamChild tipc=new TbItemParamChild();
			tipc.setCreated(tbItemParam.getCreated());
			tipc.setId(tbItemParam.getId());
			tipc.setItemCatId(tbItemParam.getItemCatId());
			tipc.setItemCatName(tbItemCatDubboServiceImpl.selById(tbItemParam.getItemCatId()).getName());
			tipc.setParamData(tbItemParam.getParamData());
			tipc.setUpdated(tbItemParam.getUpdated());
			listChild.add(tipc);
			
		}
		dataGrid.setRows(listChild);
		return dataGrid;
	}
	@Override
	public int delete(String ids) throws Exception {
		// TODO Auto-generated method stub
		
		return tbItemParamDubboServiceImpl.delByIds(ids);
	}
	@Override
	public EgoResult showParam(long catId) {
		// TODO Auto-generated method stub
		EgoResult er=new EgoResult();
		TbItemParam param = tbItemParamDubboServiceImpl.selByCatId(catId);
		if (param!=null) {
			
			er.setStatus(200);
			er.setData(param);
		}
		return er;
	}
	@Override
	public EgoResult save(TbItemParam param) {
		// TODO Auto-generated method stub
		EgoResult er=new EgoResult();
		Date date=new Date();
		param.setCreated(date);
		param.setUpdated(date);
		
		int index = tbItemParamDubboServiceImpl.insParam(param);
		if (index>0) {
			er.setStatus(200);
		}
		return er;
	}

}

























