package com.ego.manage.controller;

import javax.annotation.Resource;
import javax.xml.crypto.Data;

import net.sf.jsqlparser.statement.update.Update;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemParamItem;

@Controller
public class TbItemController {
    @Resource
   private TbItemService tbItemServiceImpl;
    @RequestMapping("item/list")
    @ResponseBody
    public EasyUiDataGrid show(int page,int rows) {
		return tbItemServiceImpl.show(page, rows);
		
	}
    /**
     * 商品删除
     * @param ids
     * @return
     */
    @RequestMapping("rest/item/delete")
    @ResponseBody
    public EgoResult delete(String ids)
    {
    	EgoResult er=new EgoResult();
    	int index = tbItemServiceImpl.update(ids, (byte)3);
    	if (index==1) {
			er.setStatus(200);
		}
		return er;
    	
    }
    
    /**
     * 商品下架
     * @param ids
     * @return
     */
    @RequestMapping("rest/item/instock")
    @ResponseBody
    public EgoResult instock(String ids)
    {
    	EgoResult er=new EgoResult();
    	int index = tbItemServiceImpl.update(ids, (byte)2);
    	if (index==1) {
			er.setStatus(200);
		}
		return er;
    	
    }
    
    /**
     * 商品上架
     * @param ids
     * @return
     */
    @RequestMapping("rest/item/reshelf")
    @ResponseBody
    public EgoResult reshelf(String ids)
    {
    	EgoResult er=new EgoResult();
    	int index = tbItemServiceImpl.update(ids, (byte)1);
    	if (index==1) {
			er.setStatus(200);
		}
		return er;
    	
    }
    /**
     * 商品新增
     * @param ids
     * @return
     */
    @RequestMapping("item/save")
    @ResponseBody
    public EgoResult save(TbItem tbItem,String desc,String itemParams) 
    {
    	EgoResult er=new EgoResult();
    	int index;
		try {
			System.out.println("con"+itemParams);
			index = tbItemServiceImpl.save(tbItem, desc,itemParams);
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
    
}
