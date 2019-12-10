package com.ego.order.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.redis.dao.JedisDao;

@Service
public class TbOrderServiceImpl implements TbOrderService{

	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${cart.key}")
	private String cartKey;
	@Value("${passport.url}")
	private String passportUrl;
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Reference
	private TbOrderDubboService tbOrderDubboServiceImpl;
	
	
	@Override
	public List<TbItemChild> showOrderCart(List<Long> ids,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		
		String result = HttpClientUtil.doPost(passportUrl+token);
		EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
		String key=cartKey+((LinkedHashMap)er.getData()).get("username");
		String json = jedisDaoImpl.get(key);
		List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);
		List<TbItemChild> list2 = new ArrayList<TbItemChild>();
		for (TbItemChild tbItemChild : list) {
			for (Long id : ids) {
				if ((long)id==(long)tbItemChild.getId()) {
									
					//判断库存
					TbItem tbItem = tbItemDubboServiceImpl.selById(id);
					if (tbItem.getNum()>=tbItemChild.getNum()) {
						tbItemChild.setEnough(true);
						
					}else {
						
						tbItemChild.setEnough(false);
					}
					list2.add(tbItemChild);
				}
			}
		}
		return list2;
	}
	@Override
	public EgoResult create(MyOrderParam param, HttpServletRequest request) {
		// TODO Auto-generated method stub
		TbOrder tbOrder = new TbOrder();
		tbOrder.setPayment(param.getPayment());
		tbOrder.setPaymentType(param.getPaymentType());
		//订单id, 类型是String
		long id = IDUtils.genItemId();
		tbOrder.setOrderId(id+"");
		
		Date date=new Date();
		tbOrder.setUpdateTime(date);
		tbOrder.setCreateTime(date);
		
		//用户ID
	  String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
	  String result = HttpClientUtil.doPost(passportUrl+token);
	  EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
	  	  	 Map user=(LinkedHashMap)er.getData();
	  	 	long parseLong = Long.parseLong(user.get("id").toString());
		tbOrder.setUserId(parseLong);
		
		tbOrder.setBuyerRate(0);
		
		
		//订单-商品表
		List<TbOrderItem> list = param.getOrderItems();
		for (TbOrderItem tbOrderItem : list) {
			tbOrderItem.setId(IDUtils.genItemId()+"");
			tbOrderItem.setOrderId(id+"");
		}
		//收货人信息
		TbOrderShipping shipping = param.getOrderShipping();
		shipping.setOrderId(id+"");
		shipping.setCreated(date);
		shipping.setUpdated(date);
		
		EgoResult eResult=new EgoResult();
	try {
		int index = tbOrderDubboServiceImpl.insOrder(tbOrder, list, shipping);
		if (index>0) {
			eResult.setStatus(200);
			//删除购买的商品
			String json = jedisDaoImpl.get(cartKey+user.get("username"));
			List<TbItemChild> listChild = JsonUtils.jsonToList(json, TbItemChild.class);
			List<TbItemChild> listNew = new ArrayList<TbItemChild>();
			for (TbItemChild tbItemChild : listChild) {
				for (TbOrderItem tbOrderItem : list) {
					
					if (tbItemChild.getId().longValue()==Long.parseLong(tbOrderItem.getItemId())) {
						listNew.add(tbItemChild);
					}
					
				}
			}
			for(TbItemChild tbItemChild : listNew){
				listChild.remove(tbItemChild);
			}
			jedisDaoImpl.set(cartKey+user.get("username"), JsonUtils.objectToJson(listChild));
			
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return eResult;
	}

}








































