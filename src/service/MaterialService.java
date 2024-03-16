/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import request.MaterialRequest;
import response.MaterialResponse;

/**
 *
 * @author LE MINH
 */
public interface MaterialService {

    List<MaterialResponse> getAllMaterial();

    boolean addMaterial(MaterialRequest materialRequest);

    boolean updateMaterial(MaterialRequest materialRequest);

    boolean deleteMaterial(MaterialRequest materialRequest);
}
