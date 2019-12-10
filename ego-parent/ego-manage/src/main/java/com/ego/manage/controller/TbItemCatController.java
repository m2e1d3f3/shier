package com.ego.manage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasuUiTree;
import com.ego.manage.service.TbItemCatService;
@Controller
public class TbItemCatController {
	@Resource
	private TbItemCatService tbItemCatServiceImpl;
	/**
	 * 显示商品类目
	 * @param pid
	 * @return
	 */
    @RequestMapping("item/cat/list")
    @ResponseBody
    public List<EasuUiTree> show(@RequestParam(defaultValue="0") long id)
    {
		return  tbItemCatServiceImpl.show(id);
    	
    }
}
