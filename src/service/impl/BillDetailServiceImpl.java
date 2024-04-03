/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.BillDetailRepository;
import request.BillDetailRequest;
import request.BillDetailUpdateRequest;
import response.BillDetailResponse;
import response.ProductCardResponse;
import service.BillDetailService;

/**
 *
 * @author LE MINH
 */
public class BillDetailServiceImpl implements BillDetailService{
    
    private BillDetailRepository billDetailRepository = new BillDetailRepository();

    @Override
    public boolean addBillDetail(BillDetailRequest billDetailRequest) {
        return billDetailRepository.addBillDetail(billDetailRequest);
    }

    @Override
    public boolean checkCardItemInDetailBill(Long idProductDetail, String idBill) {
        return billDetailRepository.checkCardItemInDetailBill(idProductDetail, idBill);
    }

    @Override
    public List<ProductCardResponse> getListProductCard(String idBill) {
        return billDetailRepository.getListProductCard(idBill);
    }

    @Override
    public boolean updateBillDetail(BillDetailUpdateRequest billDetailUpdateRequest) {
        return billDetailRepository.updateBillDetail(billDetailUpdateRequest);
    }

    @Override
    public boolean deleteBillDetail(Long idProductDetail, String idBill) {
        return billDetailRepository.deleteBillDetail(idProductDetail, idBill);
    }

    @Override
    public List<BillDetailResponse> getBillDetailByBills(String idBill) {
        return billDetailRepository.getBillDetailByBills(idBill);
    }

    @Override
    public boolean cancelBillDetail(String idBills) {
        return billDetailRepository.cancelBillDetail(idBills);
    }
    
}
