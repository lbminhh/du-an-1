/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import request.CategoryRequest;
import response.CategoryResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class CategoryRepository {

    public List<CategoryResponse> getAllCategory() {
        String query = """
                       SELECT * FROM dbo.category
                       WHERE category_status = 1
                       """;
        List<CategoryResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new CategoryResponse(rs.getLong(1), rs.getString(2), rs.getBoolean(3)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public boolean addCategory(CategoryRequest categoryRequest) {
        if (categoryRequest == null) {
            return false;
        }
        String query = """
                        INSERT INTO dbo.category
                        (
                            category_name,
                            category_status
                        )
                        VALUES
                        (   ?, -- category_name - nvarchar(255)
                            ? -- category_status - bit
                            )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, categoryRequest.getCategoryName());
            stm.setBoolean(2, categoryRequest.getStatus());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }

    public boolean updateCategory(CategoryRequest categoryRequest) {
        if (categoryRequest == null) {
            return false;
        }
        String query = """
                        UPDATE dbo.category
                        SET category_name = ?, category_status = ?
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, categoryRequest.getCategoryName());
            stm.setBoolean(2, categoryRequest.getStatus());
            stm.setLong(3, categoryRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }

    public boolean deleteCategory(CategoryRequest categoryRequest) {
        String query = """
                        UPDATE dbo.category
                        SET category_status = 0
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, categoryRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return check > 0;
    }
    
    public static void main(String[] args) {
        CategoryRepository categoryRepository = new CategoryRepository();
        System.out.println(categoryRepository.getAllCategory());
    }
}
