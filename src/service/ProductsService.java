/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import request.ProductsRequest;
import request.ProductsSearchRequest;
import response.ProductsResponse;


/**
 *
 * @author LE MINH
 */
public interface ProductsService {
    
    List<ProductsResponse> getAllProducts();
    
    boolean addProducts(ProductsRequest request);
    
    boolean updateProducts(ProductsRequest request);
    
    boolean deleteProducts(String id);
    
    int getTotalPageProducts();
    
    String getIdProducts();
    
    List<ProductsResponse> searchListProducts(ProductsSearchRequest item);
    
}
