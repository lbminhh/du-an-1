/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.ProductDetailRepository;
import request.ProductDetailRequest;
import request.ProductDetailSearchRequest;
import response.ProductDetailResponse;
import service.ProductDetailService;

/**
 *
 * @author LE MINH
 */
public class ProductDetailServiceImpl implements ProductDetailService {

    private ProductDetailRepository productDetailRepository = new ProductDetailRepository();

    @Override
    public int getQuantityByProductsId(String id) {
        return productDetailRepository.getQuantityByProductsId(id);
    }

    @Override
    public List<ProductDetailResponse> getAllProductDetail(String id) {
        return productDetailRepository.getAllProductDetail(id);
    }

    @Override
    public boolean addProductDetail(ProductDetailRequest productDetailRequest) {
        return productDetailRepository.addProductDetail(productDetailRequest);
    }

    @Override
    public boolean updateProductDetail(ProductDetailRequest productDetailRequest) {
        return productDetailRepository.updateProductDetail(productDetailRequest);
    }

    @Override
    public boolean deleteProductDetail(ProductDetailRequest productDetailRequest) {
        return productDetailRepository.updateProductDetail(productDetailRequest);
    }

    @Override
    public List<ProductDetailResponse> searchListProductDetail(ProductDetailSearchRequest item) {
        return productDetailRepository.searchListProductDetail(item);
    }

    @Override
    public ProductDetailResponse getProductDetailById(Long id) {
        return productDetailRepository.getProductDetailById(id);
    }

    @Override
    public int getQuantityByProductDetail(Long id) {
        return productDetailRepository.getQuantityByProductDetail(id);
    }

    @Override
    public boolean setMinusQuantityProductDetail(Long id, int quantity) {
        return productDetailRepository.setMinusQuantityProductDetail(id, quantity);
    }

    @Override
    public boolean setPlusQuantityProductDetail(Long id, int quantity) {
        return productDetailRepository.setPlusQuantityProductDetail(id, quantity);
    }

}
