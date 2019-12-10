package com.ego.manage.service;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

public interface TbItemParamService {
/**
 * 分页显现商品规格参数
 * @param page
 * @param rows
 * @return
 */
	EasyUiDataGrid showPage(int page,int rows);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	int delete(String ids) throws Exception;
	
	/**
	 * 根据id查询模板
	 * @param id
	 * @return
	 */
	EgoResult showParam(long catId);
	
	/**
	 * 新增模板
	 * @param param
	 * @return
	 */
	EgoResult save(TbItemParam param);
}
