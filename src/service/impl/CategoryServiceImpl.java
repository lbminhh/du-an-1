/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.CategoryRepository;
import request.CategoryRequest;
import response.CategoryResponse;
import service.CategoryService;

/**
 *
 * @author LE MINH
 */
public class CategoryServiceImpl implements CategoryService{
    
    private CategoryRepository categoryRepository = new CategoryRepository();

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.getAllCategory();
    }

    @Override
    public boolean addCategory(CategoryRequest categoryRequest) {
        return categoryRepository.addCategory(categoryRequest);
    }

    @Override
    public boolean updateCategory(CategoryRequest categoryRequest) {
        return categoryRepository.updateCategory(categoryRequest);
    }

    @Override
    public boolean deleteCategory(CategoryRequest categoryRequest) {
        return categoryRepository.deleteCategory(categoryRequest);
    }
    
}
