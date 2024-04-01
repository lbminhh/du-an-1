/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import request.CustomerHistorySearchRequest;
import request.CustomerRequest;
import request.CustomerSearchRequest;
import response.CustomerHistoryReponse;
import response.CustomerReponse;

/**
 *
 * @author admin
 */
public interface CustomerService{
    
    List<CustomerReponse> getAllCustomer();
    
    List<CustomerHistoryReponse> getAllCustomerHistory();
    
    boolean addCustomer(CustomerRequest request);
    
    boolean updateCustomer(CustomerRequest request);
    
    boolean deleteCustomer(String id);
    
    List<CustomerReponse> searchListCustomer(CustomerSearchRequest item);
    
    List<CustomerHistoryReponse> searchListCustomerHistory(CustomerHistorySearchRequest item);
    
    
    
}
