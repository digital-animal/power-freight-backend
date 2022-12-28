package com.dsi.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsi.currency.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Iterable<Currency> findByNameContaining(String name);
}