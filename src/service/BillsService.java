/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.math.BigDecimal;
import java.util.List;
import request.BillsRequest;
import request.BillsSearchRequest;
import response.BillsResponse;

/**
 *
 * @author LE MINH
 */
public interface BillsService {
    
    BillsResponse getBills();
    
    boolean addBill(BillsRequest billsRequest);
    
    boolean updateBill(BillsRequest billsRequest);
    
    List<BillsResponse> getAllBillsToday();
    
    boolean updateBillCustomer(String idBill, String idCustomer, String phone);
    
    BillsRequest getBillsRequestById(String id);
    
    boolean isExistVoucherInBills(String idVoucher);
    
    boolean setNullVoucherInBills(String id);
    
    List<BillsResponse> getCustomerBill(String idCustomer);
    
    List<BillsResponse> getAllBills();
    
    List<BillsResponse> getAllBillsSearch(BillsSearchRequest billsSearchRequest);
    
    BillsResponse getBillsResponseById(String id);
    
    double getRevenueByMonth(int month, int year);
    
    BigDecimal getTotalMoneyByToday();
    
    BigDecimal getTotalMoneyBy7Days();
    
    BigDecimal getTotalMoneyByMonth();
    
    BigDecimal getTotalMoneyByYear();
    
    BigDecimal getTotalMoneyByDate(String timeStart, String timeEnd);
    
    boolean cancelBills(String idBills);
}
