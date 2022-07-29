package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Customer;
import com.example.demo.model.CustomerTransaction;
import com.example.demo.service.CustomerRepository;
import com.example.demo.service.RewardsService;

@RunWith(SpringRunner.class)
public class RewardsServiceIntegrationTest {
	
	@TestConfiguration
    static class RewardServiceImplTestContextConfiguration {
        @Bean
        public RewardsService rewardService() {
            return new RewardsService();
        }
    }
	
	@Autowired
    private RewardsService rewardService;
    
    @MockBean
	private CustomerRepository customerRepository;

	@Before
    public void setUp() {
		Customer alex = new Customer("alex");
        Set<CustomerTransaction> alexTxns = new HashSet<CustomerTransaction>();
        Date date = new Date(); 
        CustomerTransaction alexTxn1 = new CustomerTransaction(alex, 320.3, "Purchase", date);
        CustomerTransaction alexTxn2 = new CustomerTransaction(alex, 634.3, "Purchase", date);
        alexTxns.add(alexTxn1);
        alexTxns.add(alexTxn2);
        
        
        Customer ron = new Customer("ron");
        Set<CustomerTransaction> ronTxns = new HashSet<CustomerTransaction>();
        
        CustomerTransaction ronTxn1 = new CustomerTransaction(ron, 244.3, "Purchase", date);
        CustomerTransaction ronTxn2 = new CustomerTransaction(ron, 544.3, "Purchase", date);
        alexTxns.add(ronTxn1);
        alexTxns.add(ronTxn2);
        ron.setTransactions(ronTxns);
        
        List<Customer> customers = Arrays.asList(alex,ron);
        
        Mockito.when(customerRepository.findById(alex.getId())).thenReturn(Optional.of(alex));
        Mockito.when(customerRepository.findAll()).thenReturn(customers);
        Mockito.when(customerRepository.findById(-99)).thenReturn(Optional.empty());
    }
	
    @Test
    public void whenValidId_thenCustomerShouldBeFound() {
        Customer fromDb = rewardService.getCustomerById(0);
        assertThat(fromDb.getName()).isEqualTo("alex");
        verifyFindByIdIsCalledOnce();
    }
    
    @Test
    public void givenCustomers_whengetAll_thenReturnRecords() {
    	
    	Customer alex = new Customer("alex");
        Set<CustomerTransaction> alexTxns = new HashSet<CustomerTransaction>();
        Date date = new Date(); 
        CustomerTransaction alexTxn1 = new CustomerTransaction( alex, 320.3, "Purchase", date);
        CustomerTransaction alexTxn2 = new CustomerTransaction(alex, 634.3, "Purchase", date);
        alexTxns.add(alexTxn1);
        alexTxns.add(alexTxn2);
        
        
        Customer ron = new Customer("ron");
        Set<CustomerTransaction> ronTxns = new HashSet<CustomerTransaction>();
        
        CustomerTransaction ronTxn1 = new CustomerTransaction(ron, 244.3, "Purchase", date);
        CustomerTransaction ronTxn2 = new CustomerTransaction(ron, 544.3, "Purchase", date);
        alexTxns.add(ronTxn1);
        alexTxns.add(ronTxn2);
        ron.setTransactions(ronTxns);
        

        List<Customer> customers = rewardService.getCustomerAll();
        verifyFindAllCustomersIsCalledOnce();
        assertThat(customers).extracting(Customer::getName).contains(alex.getName(), ron.getName());
    }
    
    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(customerRepository, VerificationModeFactory.times(1)).findById(Mockito.anyInt());
        Mockito.reset(customerRepository);
    }

    private void verifyFindAllCustomersIsCalledOnce() {
        Mockito.verify(customerRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(customerRepository);
    }
}
