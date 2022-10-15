package com.hunter.web.controller;
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

import com.hunter.web.model.TotalSale;
import com.hunter.web.model.TotalStock;
import com.hunter.web.service.SummaryService;

@Controller
@PropertySource("classpath:hunter_textiles.properties")
public class SummaryController {

	@Autowired SummaryService summaryService;
	@Value("${INITIAL_PAGE_SIZE}") private Integer initialPageSize;

	@GetMapping({"/"})
	public String showLandingPage(Model model) {
		return "index";
	}

	@GetMapping({"/home"})
	public String showHomePage(Model model) {
		model.addAttribute("dashboardList", summaryService.getAllDashBoardItems());
		return "home";
	}

	@RequestMapping(value = "/searchStockDashboard",
			method = RequestMethod.GET)
	public String searchStockDashboard(Model model,
			@RequestParam(value="fromDate", required = false) String fromDate,
			@RequestParam(value="toDate", required = false) String toDate,
			@RequestParam(value="mahajanName", required = false) String mahajanName) throws Exception{

		model.addAttribute("dashboardList", summaryService.getAllDashBoardItemsByDateAndMahajan(fromDate, toDate, mahajanName));
		return "home";

	}

	@GetMapping("/total-stock")
	public String showTotalStockPage(Model model,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size,
			@RequestParam(value="loadDataBy", required = false) String loadDataBy,
			@RequestParam(value="keyword", required = false) String keyword) {

		System.out.println("Showing Total Stock page");
		Page<TotalStock> listPage = null;

		if(keyword == null || "".equals(keyword)) {
			if(loadDataBy == null || "sortNo".equalsIgnoreCase(loadDataBy)) {
				listPage = summaryService.getTotalStocksBySortNo(page.orElse(1) - 1, size.orElse(initialPageSize));
				model.addAttribute("loadedBy", "sortNo");

			} else if("mahajanName".equalsIgnoreCase(loadDataBy)) {
				listPage = summaryService.getTotalStocksByMahajanName(page.orElse(1) - 1, size.orElse(initialPageSize));
				model.addAttribute("loadedBy", "mahajanName");
			}
		} else {
			if(loadDataBy == null || "sortNo".equalsIgnoreCase(loadDataBy)) {
				listPage = summaryService.getTotalStocksBySortNoAndKeyword(keyword, page.orElse(1) - 1, size.orElse(initialPageSize));
				model.addAttribute("loadedBy", "sortNo");

			} else if("mahajanName".equalsIgnoreCase(loadDataBy)) {
				listPage = summaryService.getTotalStocksByMahajanNameAndKeyword(keyword, page.orElse(1) - 1, size.orElse(initialPageSize));
				model.addAttribute("loadedBy", "mahajanName");
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
		
		model.addAttribute("keyword", keyword);
		return "total-stock";
	}

	@GetMapping("/total-sell")
	public String showTotalSale(Model model,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size,
			@RequestParam(value="keyword", required = false) String keyword) {

		System.out.println("Showing Total Sale page");

		Page<TotalSale> listPage = null;
		if(keyword == null || "".equals(keyword)) {
			listPage = summaryService.getAllTotalSales(page.orElse(1) - 1, size.orElse(initialPageSize));

		} else {
			listPage = summaryService.getTotalSalesBySortNoOrRollNo(keyword, page.orElse(1) - 1, size.orElse(initialPageSize)); //TODO
		}

		model.addAttribute("listPage", listPage);

		int totalPages = listPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("keyword", keyword);
		return "total-sell";
	}

	@GetMapping("/account-report")
	public String showAccountReportPage(Model model) {
		model.addAttribute("totalIncome", summaryService.getTotalIncome());
		model.addAttribute("totalExpense", summaryService.getTotalExpense());
		model.addAttribute("incomeReportList", summaryService.getIncomeReport());
		model.addAttribute("expenseReportList", summaryService.getExpenseReport());
		return "account-report";
	}

}
