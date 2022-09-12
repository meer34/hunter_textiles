package com.hunter.web.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hunter.web.model.StockInRoll;

public interface StockInRollRepo extends JpaRepository<StockInRoll, Long>{
	
	List<StockInRoll> findAllByOrderByIdDesc();
	
}
