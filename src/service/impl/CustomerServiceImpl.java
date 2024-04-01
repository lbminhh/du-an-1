/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.CustomerRepository;
import request.CustomerHistorySearchRequest;
import request.CustomerRequest;
import request.CustomerSearchRequest;
import response.CustomerHistoryReponse;
import response.CustomerReponse;

/**
 *
 * @author admin
 */
public class CustomerServiceImpl implements service.CustomerService{
    
    private CustomerRepository customerrepository = new CustomerRepository();

    @Override
    public List<CustomerReponse> getAllCustomer() {
        return customerrepository.getAllCustomer();
    }

    @Override
    public boolean addCustomer(CustomerRequest request) {
        return customerrepository.addCustomer(request);
    }

    @Override
    public boolean updateCustomer(CustomerRequest request) {
        return customerrepository.updateCustomer(request);
    }

    @Override
    public boolean deleteCustomer(String id) {
        return  customerrepository.deleteCustomer(id);
    }

    @Override
    public List<CustomerReponse> searchListCustomer(CustomerSearchRequest item) {
        return  customerrepository.searchListCustomer(item);
    }

    @Override
    public List<CustomerHistoryReponse> getAllCustomerHistory() {
        return  customerrepository.getAllCustomerHistory();
     }

    @Override
    public List<CustomerHistoryReponse> searchListCustomerHistory(CustomerHistorySearchRequest item) {
        return  customerrepository.searchListCustomerHistory(item);
    }

   
   
}
