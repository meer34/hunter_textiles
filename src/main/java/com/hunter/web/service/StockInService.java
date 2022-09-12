package com.hunter.web.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hunter.web.model.StockIn;
import com.hunter.web.model.StockInRoll;
import com.hunter.web.repo.StockInRepo;
import com.hunter.web.repo.StockInRollRepo;

@Service
public class StockInService {

	@Autowired private StockInRepo stockInRepo;
	@Autowired private StockInRollRepo stockInRollRepo;

	public StockIn saveStockInToDB(StockIn stockIn) {
		stockIn.processParts();
		return stockInRepo.save(stockIn);
	}

	public List<StockIn> getAllStockIns() {
		return stockInRepo.findAllByOrderByIdDesc();
	}
	
	public List<StockInRoll> getAllStockInRolls() {
		return stockInRollRepo.findAllByOrderByIdDesc();
	}

	public StockIn findStockInById(long id) {
		return stockInRepo.findById(id).get();
	}

	public List<StockIn> searchStockInByDate(String fromDate, String toDate) throws ParseException {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		if(fromDate != null && !fromDate.equalsIgnoreCase("") && toDate != null && !toDate.equalsIgnoreCase("")) {
			return stockInRepo.findByDateBetween(formatter.parse(fromDate), formatter.parse(toDate));
			
		} else if((fromDate == null || fromDate.equalsIgnoreCase("")) && toDate != null && !toDate.equalsIgnoreCase("")) {
			return stockInRepo.findByDateLessThanEqual(formatter.parse(toDate));
			
		} else if((toDate == null || toDate.equalsIgnoreCase("")) && fromDate != null && !fromDate.equalsIgnoreCase("")) {
			return stockInRepo.findByDateGreaterThanEqual(formatter.parse(fromDate));
			
		} else {
			return stockInRepo.findAll();
		}

	}

	public void deleteStockInById(Long id) {
		stockInRepo.deleteById(id);
	}

	public StockIn findStockInByScanCode(String scanCode) {
//		return stockInRepo.findFirstByScanCodeOrderByIdDesc(scanCode);
		return null;
	}

}
