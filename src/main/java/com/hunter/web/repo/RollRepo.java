package com.hunter.web.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.hunter.web.model.Roll;

public interface RollRepo extends JpaRepository<Roll, Long>{
	
	List<Roll> findAllByOrderByIdDesc();
	
	@Query("FROM Roll sir where stockOutIndicator = FALSE")
	List<Roll> getAvailableStockInRolls();
	
	@Query("FROM Roll roll where roll.stockIn = (FROM StockIn si where si.id = :stockInId)")
	List<Roll> findByStockInId(Long stockInId);
	
	@Query("FROM Roll roll where roll.stockOut = (FROM StockOut so where so.id = :stockOutId)")
	List<Roll> findByStockOutId(Long stockOutId);
	
	@Modifying
	@Transactional
	@Query("delete FROM Roll roll where roll.stockIn = (FROM StockIn si where si.id = :stockInId) AND stockOutIndicator = FALSE AND roll.id NOT IN :currentChildIds")
	void deleteStockInAndStockOutOrphanChilds(Long stockInId, List<Long> currentChildIds);
	
	@Modifying
	@Transactional
	@Query("UPDATE Roll roll SET roll.stockOutIndicator = FALSE, roll.stockOutPrice = 0, roll.stockOut = null where roll.stockOut = (FROM StockOut so where so.id = :stockOutId)")
	void removeStockOutReference(long stockOutId);

}
