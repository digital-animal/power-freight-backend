package com.dsi.commodity.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dsi.commodity.model.Commodity;
import com.dsi.commodity.repository.CommodityRepository;

// @CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CommodityController {

	@Autowired
	CommodityRepository commodityRepository;

	@GetMapping("/commodities")
	public ResponseEntity<List<Commodity>> getAllCommoditys(@RequestParam(required = false) String name) {
		try {
			List<Commodity> commodities = new ArrayList<Commodity>();

			if (name == null)
				commodityRepository.findAll().forEach(commodities::add);
			else
				commodityRepository.findByNameContaining(name).forEach(commodities::add);

			if (commodities.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(commodities, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/commodities/{id}")
	public ResponseEntity<Commodity> getCommodityById(@PathVariable("id") long id) {
		Optional<Commodity> commodityData = commodityRepository.findById(id);

		if (commodityData.isPresent()) {
			return new ResponseEntity<>(commodityData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/commodities")
	public ResponseEntity<Commodity> createCommodity(@RequestBody Commodity commodity) {
		try {
			Commodity _commodity = commodityRepository
					.save(new Commodity(commodity.getName()));
			return new ResponseEntity<>(_commodity, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PutMapping("/commodities/{id}")
	public ResponseEntity<Commodity> updateCommodity(@PathVariable("id") long id, @RequestBody Commodity commodity) {
		Optional<Commodity> commodityData = commodityRepository.findById(id);

		if (commodityData.isPresent()) {
			Commodity _commodity = commodityData.get();
			_commodity.setName(commodity.getName());
			return new ResponseEntity<>(commodityRepository.save(_commodity), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/commodities/{id}")
	public ResponseEntity<HttpStatus> deleteCommodity(@PathVariable("id") long id) {
		try {
			commodityRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/commodities")
	public ResponseEntity<HttpStatus> deleteAllCommoditys() {
		try {
			commodityRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

}
