/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import request.ColorRequest;
import response.ColorResponse;

/**
 *
 * @author LE MINH
 */
public interface ColorService {
    
    List<ColorResponse> getAllColor();

    boolean addColor(ColorRequest colorRequest);

    boolean updateCategory(ColorRequest colorRequest);

    boolean deleteCategory(ColorRequest colorRequest);
}
