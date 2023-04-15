package org.quantum.rms.services;

import java.util.List;
import org.quantum.rms.models.Customer;
import org.quantum.rms.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
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
}
