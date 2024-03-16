/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.MaterialRepository;
import request.MaterialRequest;
import response.MaterialResponse;
import service.MaterialService;

/**
 *
 * @author LE MINH
 */
public class MaterialServiceImpl implements MaterialService{
    
    private MaterialRepository materialRepository = new MaterialRepository();

    @Override
    public List<MaterialResponse> getAllMaterial() {
        return materialRepository.getAllMaterial();
    }

    @Override
    public boolean addMaterial(MaterialRequest materialRequest) {
        return materialRepository.addMaterial(materialRequest);
    }

    @Override
    public boolean updateMaterial(MaterialRequest materialRequest) {
        return materialRepository.updateMaterial(materialRequest);
    }

    @Override
    public boolean deleteMaterial(MaterialRequest materialRequest) {
        return materialRepository.deleteMaterial(materialRequest);
    }
    
}
