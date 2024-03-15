/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import request.BrandRequest;
import response.BrandResponse;

/**
 *
 * @author LE MINH
 */
public interface BrandService {

    List<BrandResponse> getAllBrand();

    boolean addBrand(BrandRequest brandRequest);

    boolean updateBrand(BrandRequest brandRequest);

    boolean deleteBrand(BrandRequest brandRequest);
}
