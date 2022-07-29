package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Customer;
import com.example.demo.model.CustomerTransaction;
import com.example.demo.service.RewardsService;
import com.example.demo.service.RewardsServiceMock;

@RestController
public class RewardsController {

	@Autowired
	private RewardsServiceMock rewardsServiceMock;

	@Autowired
	private RewardsService rewardsService;

	@GetMapping("/{id}/rewards")
	public List<CustomerTransaction> getAll() {
		return rewardsServiceMock.getAll();
	}

	@GetMapping("/customers")
	public List<Customer> findCustomerAll() {
		return rewardsService.getCustomerAll();
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Integer id) {
		Customer customer = rewardsService.getCustomerById(id);
		if (customer == null)
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

}
