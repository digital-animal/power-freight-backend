package com.dsi.country.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsi.country.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
	List<Country> findByNameContaining(String name);
}
