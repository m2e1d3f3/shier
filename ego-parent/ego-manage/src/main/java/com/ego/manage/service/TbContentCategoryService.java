package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasuUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

public interface TbContentCategoryService {
/**
 * 查询所有类目并转化为EasuUiTree属性要求
 * @param id
 * @return
 */
	List<EasuUiTree> showCategory(long id);
	/**
	 * 新增
	 * @param cate
	 * @return
	 */
	EgoResult create(TbContentCategory cate);
	
	/**
	 * 修改
	 * @param cate
	 * @return
	 */
	EgoResult update(TbContentCategory cate);
	
	
	/**
	 * 删除
	 * @param cate
	 * @return
	 */
	EgoResult delete(TbContentCategory cate);
	
}
