package com.hunter.web.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hunter.web.model.StockIn;

public interface StockInRepo extends JpaRepository<StockIn, Long>{
	
	List<StockIn> findByDateBetween(Date  fromDate, Date  toDate);
	List<StockIn> findByDateGreaterThanEqual(Date  fromDate);
	List<StockIn> findByDateLessThanEqual(Date  toDate);
	
	List<StockIn> findAllByOrderByIdDesc();
	
}
