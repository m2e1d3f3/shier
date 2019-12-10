package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemDubboServiceImpl implements TbItemDubboService{
	@Resource
	private TbItemMapper tbItemMapper;
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	@Resource 
	private TbItemParamItemMapper tbItemParamItemMapper;
	@Override
	public EasyUiDataGrid show(int page, int rows) {
		// TODO Auto-generated method stub
		
		PageHelper.startPage(page, rows);
		
		List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
		
		PageInfo<TbItem> pi=new PageInfo<>(list);
		EasyUiDataGrid easyUiDataGrid=new EasyUiDataGrid();
		easyUiDataGrid.setRows(pi.getList());
		easyUiDataGrid.setTotal(pi.getTotal());
		return easyUiDataGrid;
	}
	@Override
	public int updItemStatus(TbItem tbItem) {
		// TODO Auto-generated method stub
		
		return tbItemMapper.updateByPrimaryKeySelective(tbItem);
	}
	@Override
	public int insTbItemDesc(TbItem tbItem, TbItemDesc tbItemDesc,TbItemParamItem paramItem)
			throws Exception {
		// TODO Auto-generated method stub
		int index=0;
		try {
			index=tbItemMapper.insertSelective(tbItem);
			index+=tbItemDescMapper.insertSelective(tbItemDesc);
			index+=tbItemParamItemMapper.insert(paramItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (index==3) {
			return 1;
		}else {
			throw new Exception("新增失败，数据还原");
		}
		
		
	}
	@Override
	public List<TbItem> selAllByStatus(Byte status) {
		// TODO Auto-generated method stub
		TbItemExample example=new TbItemExample();
		example.createCriteria().andStatusEqualTo(status);
		
		return tbItemMapper.selectByExample(example);
	}
	@Override
	public TbItemDesc selByItemId(long itemId) {
		// TODO Auto-generated method stub
		return tbItemDescMapper.selectByPrimaryKey(itemId);
	}
	@Override
	public TbItem selById(long id) {
		// TODO Auto-generated method stub
		return tbItemMapper.selectByPrimaryKey(id);
	}

}
