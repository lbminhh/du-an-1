/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package request;

import java.util.Date;
import response.VoucherResponse;
import service.impl.VoucherServiceImpl;

/**
 *
 * @author LE MINH
 */
public class Test {

    public static void main(String[] args) {
        VoucherServiceImpl voucherServiceImpl = new VoucherServiceImpl();
        System.out.println(voucherServiceImpl.setDisableVoucher("JDGFFED"));
//        VoucherResponse voucher = voucherServiceImpl.getVoucherById("JDGFFED");
//        Date today = new Date();
//        if (today.compareTo(voucher.getTimeStart()) < 0) {
//            System.out.println("chưa đến ngày áp dụng");
//        }
//        
//        if (today.compareTo(voucher.getTimeEnd()) > 0) {
//            System.out.println("đã hết hạn");
//        }
        
    }
}
