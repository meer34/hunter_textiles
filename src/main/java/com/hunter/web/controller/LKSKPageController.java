package com.hunter.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hunter.web.service.StockInService;

@Controller
public class LKSKPageController {

	@Autowired StockInService stockInService;

	@RequestMapping("/login")
	public String login() {
		return "index";
	}
	
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		return "access-denied";
	}

	@GetMapping({"/", "/home"})
	public String showLandingPage(Model model) {
		return "home";
	}
	
	@GetMapping("/stock")
	public String showTotalStockPage(Model model) {
		model.addAttribute("stockList", stockInService.getAllStockIns());
		return "stock";
	}
	
	@GetMapping("/total-sell")
	public String showStockIn(Model model) {
		return "total-sell";
	}

	@RequestMapping(value = "/searchStockDashboard",
			method = RequestMethod.GET)
	public String searchTotalStock(Model model,
			@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
			@RequestParam(value="item", required = false) String itemParam) throws Exception{

		return "home";

	}

	@RequestMapping(value = "/searchTotalStock",
			method = RequestMethod.GET)
	public String searchTotalStock(Model model, @RequestParam("item") String item ) throws Exception{

		return "stock";

	}

}
