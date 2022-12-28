package com.dsi.country.controller;

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

import com.dsi.country.model.Country;
import com.dsi.country.repository.CountryRepository;

// @CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CountryController {

	@Autowired
	CountryRepository countryRepository;

	@GetMapping("/countries")
	public ResponseEntity<List<Country>> getAllCountrys(@RequestParam(required = false) String name) {
		try {
			List<Country> countries = new ArrayList<Country>();

			if (name == null)
				countryRepository.findAll().forEach(countries::add);
			else
				countryRepository.findByNameContaining(name).forEach(countries::add);

			if (countries.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(countries, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/countries/{id}")
	public ResponseEntity<Country> getCountryById(@PathVariable("id") long id) {
		Optional<Country> countryData = countryRepository.findById(id);

		if (countryData.isPresent()) {
			return new ResponseEntity<>(countryData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/countries")
	public ResponseEntity<Country> createCountry(@RequestBody Country country) {
		try {
			Country _country = countryRepository
					.save(new Country(country.getName()));
			return new ResponseEntity<>(_country, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PutMapping("/countries/{id}")
	public ResponseEntity<Country> updateCountry(@PathVariable("id") long id, @RequestBody Country country) {
		Optional<Country> countryData = countryRepository.findById(id);

		if (countryData.isPresent()) {
			Country _country = countryData.get();
			_country.setName(country.getName());
			return new ResponseEntity<>(countryRepository.save(_country), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/countries/{id}")
	public ResponseEntity<HttpStatus> deleteCountry(@PathVariable("id") long id) {
		try {
			countryRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/countries")
	public ResponseEntity<HttpStatus> deleteAllCountries() {
		try {
			countryRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

}
