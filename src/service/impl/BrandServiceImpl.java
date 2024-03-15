/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.BrandRepository;
import request.BrandRequest;
import response.BrandResponse;
import service.BrandService;

/**
 *
 * @author LE MINH
 */
public class BrandServiceImpl implements BrandService{
    
    private BrandRepository brandRepository = new BrandRepository();

    @Override
    public List<BrandResponse> getAllBrand() {
        return brandRepository.getAllBrand();
    }

    @Override
    public boolean addBrand(BrandRequest brandRequest) {
        return brandRepository.addBrand(brandRequest);
    }

    @Override
    public boolean updateBrand(BrandRequest brandRequest) {
        return brandRepository.updateBrand(brandRequest);
    }

    @Override
    public boolean deleteBrand(BrandRequest brandRequest) {
        return brandRepository.deleteBrand(brandRequest);
    }
    
}
