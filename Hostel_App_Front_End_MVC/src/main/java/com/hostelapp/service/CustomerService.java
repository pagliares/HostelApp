package com.hostelapp.service;

import com.hostelapp.domain.Customer;
import com.hostelapp.domain.CustomerModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Rodrigo Martins Pagliares.
 */
@Service
public class CustomerService {
    private static final String GUESTS = "/customers";
    private static final String SLASH = "/";

    @Value("${hostelApp.customer.service.url}")
    private String customerServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Customer> getAllCustomers(){
        String url = customerServiceUrl + GUESTS;
        HttpEntity<String> request = new HttpEntity<>(null, null);
        return this.restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<Customer>>() { }).getBody();
    }

    public Customer addCustomer(CustomerModel customerModel){
        String url = customerServiceUrl + GUESTS;
        HttpEntity<CustomerModel> request = new HttpEntity<>(customerModel, null);
        return this.restTemplate.exchange(url, HttpMethod.POST, request, Customer.class).getBody();
    }

    public Customer getCustomer(long id) {
        String url = customerServiceUrl + GUESTS + SLASH + id;
        HttpEntity<String> request = new HttpEntity<>(null, null);
        return this.restTemplate.exchange(url, HttpMethod.GET, request, Customer.class).getBody();
    }

    public Customer updateCustomer(long id, CustomerModel customerModel) {
        System.out.println(customerModel);
        String url = customerServiceUrl + GUESTS + SLASH + id;
        HttpEntity<CustomerModel> request = new HttpEntity<>(customerModel, null);
        return this.restTemplate.exchange(url, HttpMethod.PUT, request, Customer.class).getBody();
    }
}
