package com.hunter.web.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hunter.web.model.StockIn;

public interface StockInRepo extends JpaRepository<StockIn, Long>, JpaSpecificationExecutor<StockIn>{
	
	Page<StockIn> findAllByOrderByIdDesc(Pageable pageable);
	
}
