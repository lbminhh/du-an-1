/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.VoucherRepository;
import request.VoucherRequest;
import response.VoucherResponse;
import service.VoucherService;

/**
 *
 * @author LE MINH
 */
public class VoucherServiceImpl implements VoucherService {

    private VoucherRepository voucherRepository = new VoucherRepository();

    @Override
    public List<VoucherResponse> getAllVoucherNoCustomer() {
        return voucherRepository.getAllVoucherNoCustomer();
    }

    @Override
    public boolean getCustomerToVoucher(String idCustomer, String idVoucher) {
        return voucherRepository.getCustomerToVoucher(idCustomer, idVoucher);
    }

    @Override
    public VoucherResponse getVoucherById(String id) {
        return voucherRepository.getVoucherById(id);
    }

    @Override
    public boolean setDisableVoucher(String idVoucher) {
        return voucherRepository.setDisableVoucher(idVoucher);
    }

    @Override
    public boolean setEnableVoucher(String idVoucher) {
        return voucherRepository.setEnableVoucher(idVoucher);
    }

    @Override
    public List<VoucherResponse> getAllVoucher() {
        return voucherRepository.getAllVoucher();
    }

    @Override
    public boolean addVoucher(VoucherRequest voucherRequest) {
        return voucherRepository.addVoucher(voucherRequest);
    }

    @Override
    public boolean updateVoucher(VoucherRequest voucherRequest) {
        return voucherRepository.updateVoucher(voucherRequest);
    }

}
