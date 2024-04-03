/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.math.BigDecimal;
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
public class BillsServiceImpl implements BillsService {

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
    public BillsRequest getBillsRequestById(String id) {
        return billsRepository.getBillsRequestById(id);
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

    @Override
    public BillsResponse getBillsResponseById(String id) {
        return billsRepository.getBillsResponseById(id);
    }

    @Override
    public double getRevenueByMonth(int month, int year) {
        return billsRepository.getRevenueByMonth(month, year);
    }

    @Override
    public BigDecimal getTotalMoneyByToday() {
        return billsRepository.getTotalMoneyByToday();
    }

    @Override
    public BigDecimal getTotalMoneyBy7Days() {
        return billsRepository.getTotalMoneyBy7Days();
    }

    @Override
    public BigDecimal getTotalMoneyByMonth() {
        return billsRepository.getTotalMoneyByMonth();
    }

    @Override
    public BigDecimal getTotalMoneyByYear() {
        return billsRepository.getTotalMoneyByYear();
    }

    @Override
    public BigDecimal getTotalMoneyByDate(String timeStart, String timeEnd) {
        return billsRepository.getTotalMoneyByDate(timeStart, timeEnd);
    }

    @Override
    public boolean cancelBills(String idBills) {
        return billsRepository.cancelBills(idBills);
    }

}
