package com.ego.manage.service;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

public interface TbItemService {
	/**
	 * 显示商品
	 * @param page
	 * @param rows
	 * @return
	 */
     EasyUiDataGrid show(int page,int rows);
     /**
      * 批量修改状态
      * @param ins
      * @param status
      * @return
      */
     int update(String ids,byte status);
     /**
      * 数据新增
      * @param tbItem
      * @param desc
      * @return
      */
     int save(TbItem tbItem,String desc,String itemparam) throws Exception;
}
