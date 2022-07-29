package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.RewardsController;
import com.example.demo.model.Customer;
import com.example.demo.model.CustomerTransaction;
import com.example.demo.service.RewardsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=RewardsController.class)
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties")
public class ReawardControllerIntegrationTest {
	
	@Autowired
    private MockMvc mvc;
	
    @MockBean
    private RewardsService service;

    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    public void givenCustomers_whenGetCustomers_thenReturnJsonArray() throws Exception {
    	
    	Customer alex = new Customer("alex");
        Set<CustomerTransaction> alexTxns = new HashSet<CustomerTransaction>();
        Date date = new Date(); 
        CustomerTransaction alexTxn1 = new CustomerTransaction( alex, 320.3, "Purchase", date);
        CustomerTransaction alexTxn2 = new CustomerTransaction(alex, 634.3, "Purchase", date);
        alexTxns.add(alexTxn1);
        alexTxns.add(alexTxn2);
        
        
        Customer ron = new Customer("ron");
        Set<CustomerTransaction> ronTxns = new HashSet<CustomerTransaction>();
        
        CustomerTransaction ronTxn1 = new CustomerTransaction( ron, 244.3, "Purchase", date);
        CustomerTransaction ronTxn2 = new CustomerTransaction( ron, 544.3, "Purchase", date);
        alexTxns.add(ronTxn1);
        alexTxns.add(ronTxn2);
        ron.setTransactions(ronTxns);
        

        List<Customer> customers = Arrays.asList(alex, ron);

        given(service.getCustomerAll()).willReturn(customers);

        mvc.perform(get("/customers").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        //.andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].name", is(alex.getName())))
        .andExpect(jsonPath("$[1].name", is(ron.getName())));
        verify(service, VerificationModeFactory.times(1)).getCustomerAll();
        reset(service);
    }
}
