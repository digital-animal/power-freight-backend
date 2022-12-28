package com.dsi.currency.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dsi.currency.model.Currency;
import com.dsi.currency.repository.CurrencyRepository;

// @CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CurrencyController {

	@Autowired
	CurrencyRepository currencyRepository;

	@GetMapping("/currency")
	public ResponseEntity<List<Currency>> getAllCurrencies(@RequestParam(required = false) String name) {
		try {
			List<Currency> currencys = new ArrayList<Currency>();

			if (name == null)
				currencyRepository.findAll().forEach(currencys::add);
			else
				currencyRepository.findByNameContaining(name).forEach(currencys::add);

			if (currencys.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(currencys, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/currency/{id}")
	public ResponseEntity<Currency> getCurrencyById(@PathVariable("id") long id) {
		Optional<Currency> currencyData = currencyRepository.findById(id);

		if (currencyData.isPresent()) {
			return new ResponseEntity<>(currencyData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/currency")
	public ResponseEntity<Currency> createCurrency(@RequestBody Currency currency) {
		try {
			Currency _currency = currencyRepository
					.save(new Currency(currency.getName()));
			return new ResponseEntity<>(_currency, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PutMapping("/currency/{id}")
	public ResponseEntity<Currency> updateCurrency(@PathVariable("id") long id, @RequestBody Currency currency) {
		Optional<Currency> currencyData = currencyRepository.findById(id);

		if (currencyData.isPresent()) {
			Currency _currency = currencyData.get();
			_currency.setName(currency.getName());
			return new ResponseEntity<>(currencyRepository.save(_currency), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/currency/{id}")
	public ResponseEntity<HttpStatus> deleteCurrency(@PathVariable("id") long id) {
		try {
			currencyRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/currency")
	public ResponseEntity<HttpStatus> deleteAllCurrencys() {
		try {
			currencyRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

}
