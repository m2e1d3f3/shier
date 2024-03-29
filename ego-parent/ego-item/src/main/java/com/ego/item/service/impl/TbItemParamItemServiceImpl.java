package com.ego.item.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.item.pojo.ParamItem;
import com.ego.item.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;

@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService{

	@Reference
	private TbItemParamItemDubboService  tbItemParamItemDubboServiceImpl;
	@Override
	public String showParam(long itemId) {
		// TODO Auto-generated method stub
		TbItemParamItem item = tbItemParamItemDubboServiceImpl.selByItemId(itemId);
		List<ParamItem> list = JsonUtils.jsonToList(item.getParamData(), ParamItem.class);
		
		StringBuffer sf=new StringBuffer();
		for (ParamItem param : list) {
			sf.append("<table width='500' style='gray'>");
			for (int i = 0; i < param.getParams().size(); i++) {
				if (i==0) {
					sf.append("<tr >");
					sf.append("<td align='right' weight='30%'>"+param.getGroup()+"</td>");
					sf.append("<td align='right' weight='30%'>"+param.getParams().get(i).getK()+"</td>");
					sf.append("<td >"+param.getParams().get(i).getV()+"</td>");
					sf.append("</tr >");
				}
				sf.append("<tr >");
				sf.append("<td></td>");
				sf.append("<td align='right'>"+param.getParams().get(i).getK()+"</td>");
				sf.append("<td >"+param.getParams().get(i).getV()+"</td>");
				sf.append("</tr >");
			}
		
			sf.append("</table>");
			
			
			
			
		}
		
		return sf.toString();
	}

}
