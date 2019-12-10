package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

public interface TbOrderDubboService {
/**
 * 创建订单
 * @param tbOrder
 * @param list
 * @param tbOrderShipping
 * @return
 */
	int insOrder(TbOrder tbOrder,List<TbOrderItem> list,TbOrderShipping shipping) throws Exception;
	
}
