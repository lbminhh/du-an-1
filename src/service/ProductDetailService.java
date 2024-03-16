/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import request.ProductDetailRequest;
import response.ProductDetailResponse;

/**
 *
 * @author LE MINH
 */
public interface ProductDetailService {

    int getQuantityByProductsId(String id);

    List<ProductDetailResponse> getAllProductDetail(String id);

    boolean addProductDetail(ProductDetailRequest productDetailRequest);

    boolean updateProductDetail(ProductDetailRequest productDetailRequest);

    boolean deleteProductDetail(ProductDetailRequest productDetailRequest);
}
