/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.ProductsRepository;
import request.ProductsRequest;
import request.ProductsSearchRequest;
import response.ProductsResponse;
import service.ProductsService;

/**
 *
 * @author LE MINH
 */
public class ProductsServiceImpl implements ProductsService{
    
    private ProductsRepository productsRepository = new ProductsRepository();

    @Override
    public List<ProductsResponse> getAllProducts() {
        return productsRepository.getAllProducts();
    }

    @Override
    public boolean addProducts(ProductsRequest request) {
        return productsRepository.addProducts(request);
    }
    

    @Override
    public boolean updateProducts(ProductsRequest request) {
        return productsRepository.updateProducts(request);
    }

    @Override
    public boolean deleteProducts(String id) {
        return productsRepository.deleteProducts(id);
    }

    @Override
    public int getTotalPageProducts() {
        return productsRepository.getTotalPageProducts();
    }

    @Override
    public String getIdProducts() {
        return productsRepository.getIdProducts();
    }

    @Override
    public List<ProductsResponse> searchListProducts(ProductsSearchRequest item) {
        return productsRepository.searchListProducts(item);
    }
    
}
