package com.hostelapp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Rodrigo Martins Pagliares.
 */
@RestController
@RequestMapping("/customers")
public class CustomerServicesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServicesController.class);

    private final CustomerRepository repository;

    public CustomerServicesController(CustomerRepository repository){
        super();
        this.repository = repository;
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return new ArrayList<>(this.repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody CustomerModel model){
        Customer customer = this.repository.save(model.translateModelToCustomer());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.created(location).body(customer);
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id){
        Optional<Customer> customer = this.repository.findById(id);
        if(customer.isPresent()){
            return customer.get();
        }
        throw new CustomerNotFoundException("Customer not found with id: " + id);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody CustomerModel model){
        Optional<Customer> existing = this.repository.findById(id);
        if(!existing.isPresent()){
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        Customer customer = model.translateModelToCustomer();
        customer.setId(id);
        return this.repository.save(customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteCustomer(@PathVariable Long id){
        Optional<Customer> existing = this.repository.findById(id);
        if(!existing.isPresent()){
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        this.repository.deleteById(id);
    }
}
