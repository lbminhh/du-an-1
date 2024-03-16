/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import request.SizeRequest;
import response.SizeResponse;

/**
 *
 * @author LE MINH
 */
public interface SizeService {
    
    List<SizeResponse> getAllSize();

    boolean addSize(SizeRequest sizeRequest);

    boolean updateSize(SizeRequest sizeRequest);

    boolean deleteSize(SizeRequest sizeRequest);
}
