package com.ego.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
@RequestMapping("/")
public String show(){
	return "index";
	
}

@RequestMapping("{page}")
public String page(@PathVariable String page)
{
	return page;
	
}
}
