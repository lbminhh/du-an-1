/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import request.CategoryRequest;
import response.CategoryResponse;

/**
 *
 * @author LE MINH
 */
public interface CategoryService {
    
    List<CategoryResponse> getAllCategory();

    boolean addCategory(CategoryRequest categoryRequest);

    boolean updateCategory(CategoryRequest categoryRequest);

    boolean deleteCategory(CategoryRequest categoryRequest);
}
