package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;

@Controller
public class TbItemParamController {

	@Resource
	private TbItemParamService tbItemParamServiceImpl;
	/**
	 * 规格参数，分页显示
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("item/param/list")
	@ResponseBody
	public EasyUiDataGrid showPage(int page,int rows)
	{
		
		return tbItemParamServiceImpl.showPage(page, rows);
		
	}
	
	@RequestMapping("item/param/delete")
	@ResponseBody
	public EgoResult delete(String ids) {
		EgoResult er=new EgoResult();
		
		try {
			
			int index = tbItemParamServiceImpl.delete(ids);
			if (index==1) {
				er.setStatus(200);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			er.setData(e.getMessage());
			
		}
	
		return er;
		
	}
	

	@RequestMapping("item/param/query/itemcatid/{catId}")
	@ResponseBody
	public EgoResult showParam(@PathVariable long catId){
		
		return tbItemParamServiceImpl.showParam(catId);
		
	}
	
	@RequestMapping("item/param/save/{catId}")
	@ResponseBody
	public EgoResult save(TbItemParam param,@PathVariable long catId)
	{
		param.setItemCatId(catId);
		return tbItemParamServiceImpl.save(param);
		
	}
}
