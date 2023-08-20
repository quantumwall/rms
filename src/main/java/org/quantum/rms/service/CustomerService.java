package org.quantum.rms.service;

import java.util.Collections;
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
    private final SecurityService securityService;

    public CustomerService(CustomerRepository customerRepository, SecurityService securityService) {
	this.customerRepository = customerRepository;
	this.securityService = securityService;
    }

    public List<Customer> findAll() {
	return securityService.getAuthenticatedUser().map(u -> customerRepository.findAllByUserId(u.getId()))
		.orElse(Collections.emptyList());
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
