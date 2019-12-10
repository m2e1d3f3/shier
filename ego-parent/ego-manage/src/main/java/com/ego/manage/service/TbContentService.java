package com.ego.manage.service;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.pojo.TbContent;

public interface TbContentService {
/**
 * 分页
 * @param categoryId
 * @param page
 * @param rows
 * @return
 */
	EasyUiDataGrid showContent(long categoryId,int page,int rows);
	
	/**
	 * 新增
	 * @param content
	 * @return
	 */
	int save(TbContent content);
}
