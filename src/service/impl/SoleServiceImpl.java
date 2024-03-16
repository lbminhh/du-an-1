/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.SoleShoesRepository;
import request.SoleShoesRequest;
import response.SoleShoesResponse;
import service.SoleService;

/**
 *
 * @author LE MINH
 */
public class SoleServiceImpl implements SoleService{
    
    private SoleShoesRepository soleShoesRepository = new SoleShoesRepository();

    @Override
    public List<SoleShoesResponse> getAllSole() {
        return soleShoesRepository.getAllSole();
    }

    @Override
    public boolean addSole(SoleShoesRequest soleShoesRequest) {
        return soleShoesRepository.addSole(soleShoesRequest);
    }

    @Override
    public boolean updateSole(SoleShoesRequest soleShoesRequest) {
        return soleShoesRepository.updateSole(soleShoesRequest);
    }

    @Override
    public boolean deleteSole(SoleShoesRequest soleShoesRequest) {
        return soleShoesRepository.deleteSole(soleShoesRequest);
    }
    
}
