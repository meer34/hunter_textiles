package com.hunter.web.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hunter.web.model.StockIn;
import com.hunter.web.service.CustomerService;
import com.hunter.web.service.ModeratorService;
import com.hunter.web.service.PartyService;
import com.hunter.web.service.StockInService;

@Controller
@PropertySource("classpath:hunter_textiles.properties")
public class StockInController {

	@Autowired StockInService stockInService;
	@Autowired PartyService mahajanService;
	@Autowired CustomerService customerService;
	@Autowired ModeratorService moderatorService;
	@Value("${INITIAL_PAGE_SIZE}") private Integer initialPageSize;

	@GetMapping("/stock-in")
	public String showStockIn(Model model,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size,
			@RequestParam(value="fromDate", required = false) String fromDate,
			@RequestParam(value="toDate", required = false) String toDate,
			@RequestParam(value="keyword", required = false) String keyword) throws ParseException {
		
		Page<StockIn> listPage = null;
		
		if(keyword == null && fromDate == null && toDate == null) {
			System.out.println("StockIn home page");
			listPage = stockInService.getAllStockIns(page.orElse(1) - 1, size.orElse(initialPageSize));
			
		} else {
			System.out.println("Searching StockIn for fromDate:" + fromDate + " and toDate:" +toDate +" and keyword:" + keyword);
			listPage = stockInService.searchStockInByDateAndKeyword(keyword, fromDate, toDate, page.orElse(1) - 1, size.orElse(initialPageSize));
			
			model.addAttribute("fromDate", fromDate);
			model.addAttribute("toDate", toDate);
			model.addAttribute("keyword", keyword);
			
		}
		
		model.addAttribute("listPage", listPage);
		int totalPages = listPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		return "stock-in";

	}

	@GetMapping("/addStockInPage")
	public String showAddStockInPage(Model model) {

		model.addAttribute("mahajans", mahajanService.getAllUsers());
		model.addAttribute("header", "Add Stock In");

		return "stock-in-create";

	}

	@RequestMapping(value = "/editStockInPage",
			method = RequestMethod.GET)
	public String editStockOutPage(Model model, @RequestParam("id") String id) throws Exception{

		System.out.println("Got edit request for stock-out id " + id);

		model.addAttribute("mahajans", mahajanService.getAllUsers());
		model.addAttribute("header", "Edit Stock In");

		model.addAttribute("stockIn", stockInService.findStockInById(Long.parseLong(id)));

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

	/*
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
	 */
	
	
	/*
	 @GetMapping("/stock-in")
	public String showStockIn(Model model,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size,
			@RequestParam(value="fromDate", required = false) String fromDate,
			@RequestParam(value="toDate", required = false) String toDate,
			@RequestParam(value="keyword", required = false) String keyword) throws ParseException {
		
		Page<StockIn> listPage = null;
		
		if(keyword == null) {
			System.out.println("StockIn home page");
			listPage = stockInService.getAllStockIns(page.orElse(0), size.orElse(4));
			
		} else {
			System.out.println("Searching StockIn for fromDate:" + fromDate + " and toDate:" +toDate +" and keyword:" + keyword);
			for (StockIn StockIn : stockInService.searchStockInByDate(fromDate, toDate)) {
				if(StockIn.toString().toLowerCase().contains(keyword.toLowerCase())) {
					listPage.getContent().add(StockIn);
				}
			}
		}
		
		model.addAttribute("listPage", listPage);
		int totalPages = listPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		return "stock-in";

	}
	 */
	
	

}
