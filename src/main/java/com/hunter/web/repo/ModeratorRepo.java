package com.hunter.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hunter.web.model.Moderator;

public interface ModeratorRepo extends JpaRepository<Moderator, Long>{
	
}
