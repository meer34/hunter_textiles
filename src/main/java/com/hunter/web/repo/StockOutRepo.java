package com.hunter.web.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hunter.web.model.StockOut;

public interface StockOutRepo extends JpaRepository<StockOut, Long>, JpaSpecificationExecutor<StockOut>{
	
	Page<StockOut> findAllByOrderByIdDesc(PageRequest of);
	
	@Query("FROM StockOut so where so.customer = (FROM Customer cust where cust.id = :custId)")
	List<StockOut> findByCustomer(Long custId);

}
