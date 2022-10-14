package com.hunter.web.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		return "access-denied";
	}

}
