/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import request.ProductDetailRequest;
import request.ProductDetailSearchRequest;
import response.ProductDetailResponse;

/**
 *
 * @author LE MINH
 */
public interface ProductDetailService {

    int getQuantityByProductsId(String id);
    
    int getQuantityByProductDetail(Long id);

    List<ProductDetailResponse> getAllProductDetail(String id);

    boolean addProductDetail(ProductDetailRequest productDetailRequest);

    boolean updateProductDetail(ProductDetailRequest productDetailRequest);

    boolean deleteProductDetail(ProductDetailRequest productDetailRequest);
    
    List<ProductDetailResponse> searchListProductDetail(ProductDetailSearchRequest item);
    
    ProductDetailResponse getProductDetailById(Long id);
    
    boolean setMinusQuantityProductDetail(Long id, int quantity);
    
    boolean setPlusQuantityProductDetail(Long id, int quantity);
    
    List<ProductDetailResponse> getAllProductDetailShop();
    
    List<ProductDetailResponse> searchListProductDetailShop(ProductDetailSearchRequest item);
}
