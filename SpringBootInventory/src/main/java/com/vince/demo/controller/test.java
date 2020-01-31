package com.vince.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class test {
	
	@RequestMapping("/typeindex")
	public String index(){
		return "typeindex";
	}

}
