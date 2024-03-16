/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.SizeRepository;
import request.SizeRequest;
import response.SizeResponse;
import service.SizeService;

/**
 *
 * @author LE MINH
 */
public class SizeServiceImpl implements SizeService{
    
    private SizeRepository sizeRepository = new SizeRepository();

    @Override
    public List<SizeResponse> getAllSize() {
        return sizeRepository.getAllSize();
    }

    @Override
    public boolean addSize(SizeRequest sizeRequest) {
        return sizeRepository.addSize(sizeRequest);
    }

    @Override
    public boolean updateSize(SizeRequest sizeRequest) {
        return sizeRepository.updateSize(sizeRequest);
    }

    @Override
    public boolean deleteSize(SizeRequest sizeRequest) {
        return sizeRepository.deleteSize(sizeRequest);
    }
    
}
