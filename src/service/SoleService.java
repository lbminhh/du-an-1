/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import request.SoleShoesRequest;
import response.SoleShoesResponse;

/**
 *
 * @author LE MINH
 */
public interface SoleService {
    
    List<SoleShoesResponse> getAllSole();

    boolean addSole(SoleShoesRequest soleShoesRequest);

    boolean updateSole(SoleShoesRequest soleShoesRequest);

    boolean deleteSole(SoleShoesRequest soleShoesRequest);
}
