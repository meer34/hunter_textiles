package com.hunter.web.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hunter.web.model.StockOut;

public interface StockOutRepo extends JpaRepository<StockOut, Long>{
	
	List<StockOut> findByDateBetween(Date  fromDate, Date  toDate);
	List<StockOut> findByDateGreaterThanEqual(Date  fromDate);
	List<StockOut> findByDateLessThanEqual(Date  toDate);

	List<StockOut> findAllByOrderByIdDesc();
	
}
