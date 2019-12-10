package com.ego.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.pojo.TbItemChild;
import com.ego.search.service.TbItemService;

@Service
public class TbItemServiceImpl implements TbItemService{

	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	@Resource
	private CloudSolrClient solrClient;
	@Override
	public void init() throws SolrServerException, IOException {
		// TODO Auto-generated method stub
		solrClient.deleteByQuery("*:*");
		solrClient.commit();
		List<TbItem> itemList = tbItemDubboServiceImpl.selAllByStatus((byte)1);
		
		for (TbItem item : itemList) {
			/*<field name="item_title" type="text_ik" indexed="true" stored="true"/>
			<field name="item_sell_point" type="text_ik" indexed="true" stored="true"/>
			<field name="item_price"  type="long" indexed="true" stored="true"/>
			<field name="item_image" type="string" indexed="false" stored="true" />
			<field name="item_category_name" type="string" indexed="true" stored="true" />
			<field name="item_desc" type="text_ik" indexed="true" stored="false" />*/
			
			
			SolrInputDocument doc=new SolrInputDocument();
			
			TbItemDesc desc = tbItemDubboServiceImpl.selByItemId(item.getId());
			TbItemCat cat = tbItemCatDubboServiceImpl.selById(item.getCid());
			doc.setField("id", item.getId());
			doc.setField("item_title", item.getTitle());
			doc.setField("item_sell_point", item.getSellPoint());
			doc.setField("item_price", item.getPrice());
			doc.setField("item_image", item.getImage());
			doc.setField("item_category_name", cat.getName());
			doc.setField("item_desc", desc.getItemDesc());
			doc.setField("item_updated", item.getUpdated());
			solrClient.add(doc);
		}
		solrClient.commit();
	}

	@Override
	public Map<String, Object> selByQuery(String query, int page, int rows)
			throws SolrServerException, IOException {
		// TODO Auto-generated method stub
		SolrQuery params = new SolrQuery();
		
		
		params.setQuery("item_keywords:"+query);
		params.setStart(rows*(page-1));
		params.setRows(rows);
		
		params.setHighlight(true);
		params.addHighlightField("item-title");
		params.setHighlightSimplePre("<span style='color:red'>");
		params.setHighlightSimplePost("</span>");
		//添加排序
		params.setSort("item_updated",ORDER.desc);
		QueryResponse response = solrClient.query(params);
		SolrDocumentList listSolr = response.getResults();
		
		
		
		List<TbItemChild> listChild=new ArrayList<>();
		
		Map<String, Map<String, List<String>>> hh = response.getHighlighting();
		
for (SolrDocument docs : listSolr) {
	Map<String, List<String>> map = hh.get(docs.getFieldValue("id"));
			TbItemChild child=new TbItemChild();
			child.setId(Long.parseLong(docs.getFieldValue("id").toString()));
			child.setPrice((long)docs.getFieldValue("item_price".toString()));
			child.setImages(docs.getFieldValue("item_image").toString().split(","));
			
			child.setSellPoint(docs.getFieldValue("item_sell_point").toString());
			
			List<String> list = map.get("item_title");
			if (list!=null&&list.size()>0) {
				child.setTitle(list.get(0));
			}else {
				child.setTitle(docs.getFieldValue("item_title").toString());
			}
			
			listChild.add(child);
			
			
		}

Map<String, Object> resultMap=new HashMap<String, Object>();
resultMap.put("itemList", listChild);
long total = listSolr.getNumFound();
int pages;
if (total%rows==0) {
	pages=(int) (total/rows);
}else {
	pages=(int) (total/rows)+1;
}

resultMap.put("totalPages",pages );
		
		return resultMap;
	}

	@Override
	public int add(Map<String, Object> map,String desc) throws SolrServerException, IOException {
		// TODO Auto-generated method stub
		SolrInputDocument doc=new SolrInputDocument();
		doc.addField("id",map.get("id"));
		doc.addField("item_title",map.get("title"));
		doc.addField("item_sell_point",map.get("sellPoint"));
		doc.addField("item_price",map.get("price"));
		doc.addField("item_image",map.get("image"));
		doc.addField("item_category_name",tbItemCatDubboServiceImpl.selById((Integer)map.get("cid")).getName());
		doc.addField("item_desc", desc);
		
	  UpdateResponse response = solrClient.add(doc);
	  solrClient.commit();
	  if (response.getStatus()==0) {
		return 1;
	}
	  
		return 0;
	}

}



















