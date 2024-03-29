/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import response.VoucherResponse;

/**
 *
 * @author LE MINH
 */
public interface VoucherService {
    
    List<VoucherResponse> getAllVoucher();
    
    boolean getCustomerToVoucher(String idCustomer, String idVoucher);
    
    VoucherResponse getVoucherById(String id);
    
    boolean setDisableVoucher(String idVoucher);
    
    boolean setEnableVoucher(String idVoucher);
}
