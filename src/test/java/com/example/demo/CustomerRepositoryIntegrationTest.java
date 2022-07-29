package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Customer;
import com.example.demo.model.CustomerTransaction;
import com.example.demo.service.CustomerRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryIntegrationTest{

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;
    
    
    @Test
    public void whenFindById_thenReturnEmployee() {
    	Customer alex = new Customer("alex");
        Set<CustomerTransaction> alexTxns = new HashSet<CustomerTransaction>();
        Date date = new Date(); 
        CustomerTransaction alexTxn1 = new CustomerTransaction(alex, 320.3, "Purchase", date);
        CustomerTransaction alexTxn2 = new CustomerTransaction(alex, 634.3, "Purchase", date);
        alexTxns.add(alexTxn1);
        alexTxns.add(alexTxn2);
        entityManager.persistAndFlush(alex);

        Customer fromDb = customerRepository.findById(alex.getId()).orElse(null);
        assertThat(fromDb.getName()).isEqualTo(alex.getName());
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
    	Customer fromDb = customerRepository.findById(-11).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfEmployees_whenFindAll_thenReturnAllEmployees() {
        Customer alex = new Customer("alex");
        Set<CustomerTransaction> alexTxns = new HashSet<CustomerTransaction>();
        Date date = new Date(); 
        CustomerTransaction alexTxn1 = new CustomerTransaction( alex, 320.3, "Purchase", date);
        CustomerTransaction alexTxn2 = new CustomerTransaction( alex, 634.3, "Purchase", date);
        alexTxns.add(alexTxn1);
        alexTxns.add(alexTxn2);
        
        
        Customer ron = new Customer("ron");
        Set<CustomerTransaction> ronTxns = new HashSet<CustomerTransaction>();
        
        CustomerTransaction ronTxn1 = new CustomerTransaction( ron, 244.3, "Purchase", date);
        CustomerTransaction ronTxn2 = new CustomerTransaction( ron, 544.3, "Purchase", date);
        alexTxns.add(ronTxn1);
        alexTxns.add(ronTxn2);
        ron.setTransactions(ronTxns);
        
        entityManager.persist(alex);
        entityManager.persist(ron);
        entityManager.flush();

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers).extracting(Customer::getName).contains(alex.getName(), ron.getName());
    }
}


