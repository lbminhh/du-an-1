/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.BillsRepository;
import request.BillsRequest;
import request.BillsSearchRequest;
import response.BillsResponse;
import service.BillsService;

/**
 *
 * @author LE MINH
 */
public class BillsServiceImpl implements BillsService{
    
    private BillsRepository billsRepository = new BillsRepository();

    @Override
    public BillsResponse getBills() {
        return billsRepository.getBills();
    }

    @Override
    public boolean addBill(BillsRequest billsRequest) {
        return billsRepository.addBill(billsRequest);
    }

    @Override
    public List<BillsResponse> getAllBillsToday() {
        return billsRepository.getAllBillsToday();
    }

    @Override
    public boolean updateBill(BillsRequest billsRequest) {
        return billsRepository.updateBill(billsRequest);
    }

    @Override
    public boolean updateBillCustomer(String idBill, String idCustomer, String phone) {
        return billsRepository.updateBillCustomer(idBill, idCustomer, phone);
    }

    @Override
    public BillsRequest getBillsById(String id) {
        return billsRepository.getBillsById(id);
    }

    @Override
    public boolean isExistVoucherInBills(String idVoucher) {
        return billsRepository.isExistVoucherInBills(idVoucher);
    }

    @Override
    public boolean setNullVoucherInBills(String id) {
        return billsRepository.setNullVoucherInBills(id);
    }

    @Override
    public List<BillsResponse> getCustomerBill(String idCustomer) {
        return billsRepository.getCustomerBill(idCustomer);
    }

    @Override
    public List<BillsResponse> getAllBills() {
        return billsRepository.getAllBills();
    }

    @Override
    public List<BillsResponse> getAllBillsSearch(BillsSearchRequest billsSearchRequest) {
        return billsRepository.getAllBillsSearch(billsSearchRequest);
    }
    
    
}