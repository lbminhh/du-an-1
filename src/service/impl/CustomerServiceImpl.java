/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.CustomerRepository;
import request.CustomerRequest;
import request.CustomerUpdateRequest;
import response.CustomerResponse;
import service.CustomerService;

/**
 *
 * @author LE MINH
 */
public class CustomerServiceImpl implements CustomerService{
    
    private CustomerRepository customerRepository = new CustomerRepository();

    @Override
    public String getIdCustomer() {
        return customerRepository.getIdCustomer();
    }

    @Override
    public boolean addCustomer(CustomerRequest customerRequest) {
        return customerRepository.addCustomer(customerRequest);
    }

    @Override
    public boolean updateCustomer(CustomerRequest customerRequest) {
        return customerRepository.updateCustomer(customerRequest);
    }

    @Override
    public List<CustomerResponse> getListCustomer() {
        return customerRepository.getListCustomer();
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        return customerRepository.getCustomerById(id);
    }

    @Override
    public int getNumberOfPurchase(String id) {
        return customerRepository.getNumberOfPurchase(id);
    }

    @Override
    public boolean deleteCustomer() {
        return customerRepository.deleteCustomer();
    }

    @Override
    public boolean resetNumberOfPurchase(String id) {
        return customerRepository.resetNumberOfPurchase(id);
    }

    @Override
    public boolean updateCustomer2(CustomerUpdateRequest item) {
        return customerRepository.updateCustomer2(item);
    }

    @Override
    public boolean addCustomer2(CustomerUpdateRequest item) {
        return customerRepository.addCustomer2(item);
    }
    
}
