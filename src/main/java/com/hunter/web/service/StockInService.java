package com.hunter.web.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hunter.web.model.Roll;
import com.hunter.web.model.StockIn;
import com.hunter.web.repo.RollRepo;
import com.hunter.web.repo.StockInRepo;
import com.hunter.web.specification.StockInSearchSpecification;
import com.hunter.web.util.SearchSpecificationBuilder;

@Service
public class StockInService {

	@Autowired private StockInRepo stockInRepo;
	@Autowired private RollRepo rollRepo;

	public StockIn saveStockInToDB(StockIn stockIn) {
		stockIn.processParts(rollRepo);
		return stockInRepo.save(stockIn);
	}

	public Page<StockIn> getAllStockIns(Integer pageNo, Integer pageSize) {
		return stockInRepo.findAllByOrderByIdDesc(PageRequest.of(pageNo, pageSize));
	}

	public List<Roll> getAllStockInRolls() {
		return rollRepo.findAllByOrderByIdDesc();
	}

	public List<Roll> getAvailableStockInRolls() {
		return rollRepo.getAvailableStockInRolls();
	}

	public StockIn findStockInById(long id) {
		return (StockIn) stockInRepo.findById(id).get();
	}

	public Page<StockIn> searchStockInByDateAndKeyword(String keyword, 
			String fromDate, String toDate, int pageNo, Integer pageSize) throws ParseException {

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
		StockInSearchSpecification spec = (StockInSearchSpecification) SearchSpecificationBuilder.build(fromDate, toDate, keyword, StockIn.class);
		return stockInRepo.findAll(spec, pageRequest);

	}

	public void deleteStockInById(Long id) {
		stockInRepo.deleteById(id);
	}

	public StockIn findStockInByScanCode(String scanCode) {
		//		return stockInRepo.findFirstByScanCodeOrderByIdDesc(scanCode);
		return null;
	}

}
