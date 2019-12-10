package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService{
@Resource
private TbItemParamMapper tbItemParamMapper;
	@Override
	public EasyUiDataGrid showPage(int page, int rows) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
		PageInfo<TbItemParam> pi=new PageInfo<>(list);
		
		EasyUiDataGrid dataGrid=new EasyUiDataGrid();
		dataGrid.setRows(pi.getList());
		dataGrid.setTotal(pi.getTotal());
		return dataGrid;
	}
	@Override
	public int delByIds(String ids) throws Exception {
		// TODO Auto-generated method stub
		String[] idStr = ids.split(",");
		int index=0;
		for (String id : idStr) {
			index+=tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(id));
		}
		if (index==idStr.length) {
			return 1;
		}else {
			throw new Exception("错误");
			
		}
		
	}
	@Override
	public TbItemParam selByCatId(long catId) {
		// TODO Auto-generated method stub
		TbItemParamExample example=new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(catId);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public int insParam(TbItemParam param) {
		// TODO Auto-generated method stub
		
		return tbItemParamMapper.insertSelective(param);
	}

}






