package com.ego.dubbo.service;

import java.io.IOException;
import java.util.List;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

public interface TbItemDubboService {
/**
 * 商品分页查询
 * @param page第几页
 * @param rows每页几条数据
 * @return
 */
EasyUiDataGrid show(int page,int rows);
	/**
	 * 修改状态
	 * @param id
	 * @param status
	 * @return
	 */
int updItemStatus(TbItem tbItem);

/**
 * 商品新增
 * @param tbItem
 * @param tbItemDesc
 * @return
 * @throws Exception
 */
int insTbItemDesc(TbItem tbItem,TbItemDesc tbItemDesc,TbItemParamItem paramItem) throws Exception;
	
/**
 *  根据状态查询全部
 * @param status
 * @return
 */
List<TbItem> selAllByStatus(Byte status);

/**
 * 根据主键查询商品描述对象
 * @param itemId
 * @return
 */
TbItemDesc selByItemId(long itemId);
/**
 * 根据主键ID查询全部
 * @param id
 * @return
 */
TbItem selById(long id);
}
