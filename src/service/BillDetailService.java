/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import request.BillDetailRequest;
import request.BillDetailUpdateRequest;
import response.BillDetailResponse;
import response.ProductCardResponse;

/**
 *
 * @author LE MINH
 */
public interface BillDetailService {
    
    boolean addBillDetail(BillDetailRequest billDetailRequest);
    
    boolean updateBillDetail(BillDetailUpdateRequest billDetailUpdateRequest);
    
    boolean checkCardItemInDetailBill(Long idProductDetail, String idBill);
    
    List<ProductCardResponse> getListProductCard(String idBill);
    
    boolean deleteBillDetail(Long idProductDetail, String idBill);
    
    List<BillDetailResponse> getBillDetailByBills(String idBill);
    
    boolean cancelBillDetail(String idBills);
}
