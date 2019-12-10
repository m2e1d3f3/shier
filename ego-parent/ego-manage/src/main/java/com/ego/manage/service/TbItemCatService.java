package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasuUiTree;

public interface TbItemCatService {
/**
 * 根据父菜单ID查询所有子菜单
 * @param id
 * @return
 */
	List<EasuUiTree> show(long pid);
}
