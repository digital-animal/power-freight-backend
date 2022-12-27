package com.dsi.commodity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsi.commodity.model.Commodity;

public interface CommodityRepository extends JpaRepository<Commodity, Long> {
	List<Commodity> findByNameContaining(String name);
}
