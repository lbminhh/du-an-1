/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import repository.AccountRepository;
import request.AccountRequest;
import response.AccountResponse;
import service.AccountService;

/**
 *
 * @author LE MINH
 */
public class AccountServiceImpl implements AccountService{
    
    private AccountRepository accountRepository = new AccountRepository();

    @Override
    public boolean addAccount(AccountRequest accountRequest) {
        return accountRepository.addAccount(accountRequest);
    }

    @Override
    public boolean updateAccount(AccountRequest accountRequest) {
        return accountRepository.updateAccount(accountRequest);
    }

    @Override
    public boolean checkUsername(String username) {
        return accountRepository.checkUsername(username);
    }

    @Override
    public AccountResponse getAccountById(String idEmployee) {
        return accountRepository.getAccountById(idEmployee);
    }

    @Override
    public boolean checkLogin(String username, String password) {
        return accountRepository.checkLogin(username, password);
    }

    @Override
    public AccountResponse getAccountByUsername(String username) {
        return accountRepository.getAccountByUsername(username);
    }
    
    
}
