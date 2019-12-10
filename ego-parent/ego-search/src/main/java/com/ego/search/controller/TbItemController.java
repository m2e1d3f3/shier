package com.ego.search.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.search.service.TbItemService;

@Controller
public class TbItemController {

	@Resource
	private TbItemService tbItemServiceImpl;
	
	@RequestMapping(value="solr/init",produces="text/html;charset=utf-8")
	@ResponseBody
	public String init()
	{
		long start = System.currentTimeMillis();
		try {
			tbItemServiceImpl.init();
			long end = System.currentTimeMillis();
			return "时间"+(end-start)/1000+"秒";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "失败";
		} 
	}
	
	/**
	 * 搜索功能
	 * @param model
	 * @param q
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("search.html")
		public String search(Model model,String q,@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="10")int rows) {
		try {
			q=new String(q.getBytes("iso-8859-1"),"utf-8");
			Map<String, Object> map = tbItemServiceImpl.selByQuery(q, page, rows);
			model.addAttribute("itemList", map.get("itemList"));
			model.addAttribute("totalPages", map.get("totalPages"));
			model.addAttribute("page", page);
			model.addAttribute("query", q);
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "search";
		
	}
	
	@RequestMapping("solr/add")
	@ResponseBody
	public int insSolr(@RequestBody Map<String, Object> map)
	{
		try {
			return	tbItemServiceImpl.add((LinkedHashMap)map.get("item"), map.get("desc").toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return 0;
		
	}
	
}
