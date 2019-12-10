package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.pojo.TbItemParam;

public interface TbItemParamDubboService {
/**
 * 分页查询数据
 * @param page
 * @param rows
 * @return
 */
	EasyUiDataGrid showPage(int page,int rows);
	
	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	int delByIds(String ids) throws Exception;
	
	/**
	 * 根据类目ID选择参数模板
	 * @param id
	 * @return
	 */
	TbItemParam selByCatId(long catId);
	
	/**
	 * 新增
	 * @param param
	 * @return
	 */
	int insParam(TbItemParam param);
}
