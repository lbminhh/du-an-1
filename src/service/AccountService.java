/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import request.AccountRequest;
import response.AccountResponse;

/**
 *
 * @author LE MINH
 */
public interface AccountService {
    
    boolean addAccount(AccountRequest accountRequest);
    
    boolean updateAccount(AccountRequest accountRequest);
    
    boolean checkUsername(String username);
    
    AccountResponse getAccountById(String idEmployee);
    
    boolean checkLogin(String username, String password);
    
    AccountResponse getAccountByUsername(String username);
}
