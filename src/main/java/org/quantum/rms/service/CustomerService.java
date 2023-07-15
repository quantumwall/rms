package org.quantum.rms.service;

import java.util.List;
import java.util.Objects;

import org.quantum.rms.model.Customer;
import org.quantum.rms.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
	this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
	return customerRepository.findAll();
    }

    public Customer findById(long id) {
	return customerRepository.findById(id).orElse(null);
    }

    @Transactional
    public Customer save(Customer customer) {
	if (Objects.nonNull(customer)) {
	    customerRepository.save(customer);
	}
	return customer;
    }

}
