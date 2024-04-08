/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import request.CustomerRequest;
import request.CustomerUpdateRequest;
import response.CustomerResponse;

/**
 *
 * @author LE MINH
 */
public interface CustomerService {
    
    String getIdCustomer();
    
    boolean addCustomer(CustomerRequest customerRequest);
    
    boolean updateCustomer(CustomerRequest customerRequest);
    
    List<CustomerResponse> getListCustomer();
    
    CustomerResponse getCustomerById(String id);
    
    int getNumberOfPurchase(String id);
    
    boolean deleteCustomer();
    
    boolean resetNumberOfPurchase(String id);
    
    boolean updateCustomer2(CustomerUpdateRequest item);
    
    boolean addCustomer2(CustomerUpdateRequest item);
    
    List<CustomerResponse> getListCustomerSearch(String value);
    
    List<CustomerResponse> getListCustomerVip();
}
