/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.ProductDetailRepository;
import response.ProductDetailResponse;
import service.ProductDetailService;

/**
 *
 * @author LE MINH
 */
public class ProductDetailServiceImpl implements ProductDetailService{
    
    private ProductDetailRepository productDetailRepository = new ProductDetailRepository();

    @Override
    public int getQuantityByProductsId(String id) {
        return productDetailRepository.getQuantityByProductsId(id);
    }

    @Override
    public List<ProductDetailResponse> getAllProductDetail(String id) {
        return productDetailRepository.getAllProductDetail(id);
    }
    
}
