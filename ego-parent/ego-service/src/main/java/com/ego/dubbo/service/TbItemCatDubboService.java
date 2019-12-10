package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemParam;

public interface TbItemCatDubboService {
	/**
	 * 根据ID查询子类目
	 * @param pid
	 * @return
	 */
     List<TbItemCat> show(long pid);
     
     /**
      * 根据ID查询商品类目ID
      * @param id
      * @return
      */
     TbItemCat selById(long id);

	
}
