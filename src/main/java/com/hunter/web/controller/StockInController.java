package com.hunter.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hunter.web.model.StockIn;
import com.hunter.web.service.CustomerService;
import com.hunter.web.service.ModeratorService;
import com.hunter.web.service.PartyService;
import com.hunter.web.service.StockInService;

@Controller
public class StockInController {

	@Autowired StockInService stockInService;
	@Autowired PartyService mahajanService;
	@Autowired CustomerService customerService;
	@Autowired ModeratorService moderatorService;

	@GetMapping("/stock-in")
	public String showStockIn(Model model) {
		System.out.println("Inside stock-in");
		model.addAttribute("stockInList", stockInService.getAllStockIns());
		return "stock-in";
	}

	@GetMapping("/addStockInPage")
	public String showAddStockInPage(Model model) {
		model.addAttribute("mahajans", mahajanService.getAllUsers());
		return "stock-in-create";
	}

	@RequestMapping(value = "/addStockIn",
			method = RequestMethod.POST)
	public String addStock(Model model, StockIn stockIn, RedirectAttributes redirectAttributes) throws Exception{
		
		stockInService.saveStockInToDB(stockIn);
		redirectAttributes.addFlashAttribute("successMessage", "Stock In added successfully!");
		return "redirect:/stock-in";

	}

	@RequestMapping(value = "/viewStockIn",
			method = RequestMethod.GET)
	public String viewStockIn(Model model, @RequestParam("id") String id) throws Exception{

		System.out.println("Got view request for stock_in id " + id);

		model.addAttribute("stockIn", stockInService.findStockInById(Long.parseLong(id)));
		model.addAttribute("header", "Stock In");
		model.addAttribute("submitValue", "Print");

		model.addAttribute("parties", mahajanService.getAllUsers());
		model.addAttribute("moderators", moderatorService.getAllUsers());

		return "stock-in-view";

	}

	@RequestMapping(value = "/editStockIn",
			method = RequestMethod.GET)
	public String editStockIn(Model model, @RequestParam("id") String id) throws Exception{

		System.out.println("Got edit request for stock-in id " + id);

		model.addAttribute("stockIn", stockInService.findStockInById(Long.parseLong(id)));
		model.addAttribute("header", "Edit Stock In");
		model.addAttribute("submitValue", "Save");

		model.addAttribute("parties", mahajanService.getAllUsers());
		model.addAttribute("moderators", moderatorService.getAllUsers());

		return "stock-in-view";

	}

	@RequestMapping(value = "/deleteStockIn",
			method = RequestMethod.GET)
	public String deleteStockIn(RedirectAttributes redirectAttributes, @RequestParam("id") Long id) throws Exception{

		System.out.println("Got delete request for stock-in id " + id);
		stockInService.deleteStockInById(id);
		
		redirectAttributes.addFlashAttribute("successMessage", "Stock In deleted successfully!");
		return "redirect:/stock-in";

	}

	@RequestMapping(value = "/saveStockInEdit",
			method = RequestMethod.POST)
	public String saveStockInEdit(RedirectAttributes redirectAttributes, StockIn stockIn,
			@RequestParam Long itemId,
			@RequestParam("id") String id) throws Exception{

		System.out.println("Got save edit request for stock_in id " + id);

		stockInService.saveStockInToDB(stockIn);

		redirectAttributes.addFlashAttribute("successMessage", "Stock In edited successfully!");
		return "redirect:/stock-in";

	}
	
	@RequestMapping(value = "/searchStockIn",
			method = RequestMethod.GET)
	public String searchStockIn(Model model, 
			@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
			@RequestParam("keyword") String keyword ) throws Exception{

		List<StockIn> stockInList = new ArrayList<StockIn>();

		for (StockIn StockIn : stockInService.searchStockInByDate(fromDate, toDate)) {
			if(StockIn.toString().toLowerCase().contains(keyword.toLowerCase())) {
				stockInList.add(StockIn);
			}
		}

		System.out.println("Search size for fromDate:" + fromDate + " and toDate:" +toDate +" and keyword:" + keyword + " is - " + stockInList.size());

		model.addAttribute("stockInList", stockInList);
		return "stock-in";

	}

	@RequestMapping(value = "/addStockInForScanCode",
			method = RequestMethod.GET)
	public String addStockInForScanCode(Model model, @RequestParam("action") String action, 
			@RequestParam("scanCode") String scanCode) throws Exception{

		System.out.println("Got prefetch request for id " + scanCode);

		StockIn stockIn = stockInService.findStockInByScanCode(scanCode);
		stockIn.setId(0);

		model.addAttribute("stockIn", stockIn);
		model.addAttribute("header", "New Stock In");
		model.addAttribute("submitValue", "Save");

		model.addAttribute("parties", mahajanService.getAllUsers());
		model.addAttribute("moderators", moderatorService.getAllUsers());

		return "stock-in-view";

	}

	@RequestMapping(value = "/checkIfScanCodeExistsForStockIn",
			method = RequestMethod.GET)
	@ResponseBody
	public String checkIfScanCodeExistsForStockIn(@RequestParam String scanCode) {
		System.out.println("Searching StockIn for scan code - " + scanCode);
		if(stockInService.findStockInByScanCode(scanCode) != null) {
			return "Exist";
		} else {
			return "Not Exist";
		}
	}
	
	@RequestMapping(value = "/checkIfScanCodeExistsForStockOut",
			method = RequestMethod.GET)
	@ResponseBody
	public String checkIfScanCodeExists(@RequestParam String scanCode) {
		System.out.println("Searching StockOut for scan code - " + scanCode);
		if(stockInService.findStockInByScanCode(scanCode) != null) {
			return "Exist";
		} else {
			return "Not Exist";
		}
	}

}
