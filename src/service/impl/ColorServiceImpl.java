/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.ColorRepository;
import request.ColorRequest;
import response.ColorResponse;
import service.ColorService;

/**
 *
 * @author LE MINH
 */
public class ColorServiceImpl implements ColorService{
    
    private ColorRepository colorRepository = new ColorRepository();

    @Override
    public List<ColorResponse> getAllColor() {
        return colorRepository.getAllColor();
    }
    

    @Override
    public boolean addColor(ColorRequest colorRequest) {
        return colorRepository.addColor(colorRequest);
    }

    @Override
    public boolean updateCategory(ColorRequest colorRequest) {
        return colorRepository.updateColor(colorRequest);
    }

    @Override
    public boolean deleteCategory(ColorRequest colorRequest) {
        return colorRepository.deleteColor(colorRequest);
    }
    
}
