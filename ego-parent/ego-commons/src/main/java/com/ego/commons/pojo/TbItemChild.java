package com.ego.commons.pojo;

import com.ego.pojo.TbItem;

public class TbItemChild extends TbItem{
private Boolean enough;
	

	public Boolean getEnough() {
	return enough;
}

public void setEnough(Boolean enough) {
	this.enough = enough;
}

	private String[] images;

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}
}
