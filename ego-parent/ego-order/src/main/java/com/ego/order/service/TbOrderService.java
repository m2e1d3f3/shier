package com.ego.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.order.pojo.MyOrderParam;

public interface TbOrderService {
/**
 * 把购车车的商品传给确认订单
 * @param ids
 * @param request
 * @return
 */
	List<TbItemChild> showOrderCart(List<Long> ids,HttpServletRequest request);
	
	
	EgoResult create(MyOrderParam param,HttpServletRequest request);
}
