package com.hunter.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hunter.web.model.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long>{
	
}
