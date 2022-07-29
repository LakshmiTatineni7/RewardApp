package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Customer;
import com.example.demo.model.CustomerTransaction;

@Service
public class RewardsServiceMock {

	private static List<CustomerTransaction> transactions = new ArrayList<CustomerTransaction>();

	static {

		transactions.add(new CustomerTransaction(new Customer("Mohsen"), 170.0, "Purchase 1", new Date()));
		transactions.add(new CustomerTransaction(new Customer("Max"), 55.0, "Purchase 2", new Date()));
		transactions.add(new CustomerTransaction(new Customer("Lauren"), 200.0, "Purchase 3", new Date()));

	}

	public List<CustomerTransaction> getAll() {
		return transactions;
	}

}
