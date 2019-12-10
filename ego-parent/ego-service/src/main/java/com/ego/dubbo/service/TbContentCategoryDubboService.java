package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbContentCategory;

public interface TbContentCategoryDubboService {
	/**
	 * 根据父ID查询所有子类目
	 * @param id
	 * @return
	 */
     List<TbContentCategory> selByPid(long id);
     
     /**
      * 新增类目
      * @param cate
      * @return
      */
     int insTbContentCategory(TbContentCategory cate);
     
     /**
      * 根据ID修改
      * @param cate
      * @return
      */
     int updIsParentById(TbContentCategory cate);
     
     /**
      * 根据ID查询自己
      * @param id
      * @return
      */
     TbContentCategory selById(long id);
}
