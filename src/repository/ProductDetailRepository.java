/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import response.ProductDetailResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class ProductDetailRepository {

    public int getQuantityByProductsId(String id) {
        String query = """
                        SELECT SUM(quantity) 
                        FROM dbo.product_detail JOIN dbo.products ON products.id = product_detail.product_id
                        WHERE product_id = ?
                       """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return 0;
    }
    
    public List<ProductDetailResponse> getAllProductDetail(String id) {
        String query = """
                        DECLARE @id_product VARCHAR(10)
                        SET @id_product = ?
                        SELECT product_detail.id, product_name, brand_name, price, color_name, size_name, sole_name, material_name, quantity, dbo.product_detail.status
                        FROM dbo.product_detail JOIN dbo.products ON products.id = product_detail.product_id
                        						JOIN dbo.brand ON brand.id = products.brand_id
                        						JOIN dbo.color ON color.id = product_detail.color_id
                        						JOIN dbo.size ON size.id = product_detail.size_id
                        						JOIN dbo.sole_shoes ON sole_shoes.id = product_detail.sole_id
                        						JOIN dbo.material ON material.id = product_detail.material_id
                        WHERE products.id = @id_product OR @id_product IS NULL
                       """;
        List<ProductDetailResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {                
                list.add(new ProductDetailResponse(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getBigDecimal(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getBoolean(10)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return list;
    }
    
    public static void main(String[] args) {
        ProductDetailRepository productDetailRepository = new ProductDetailRepository();
        System.out.println(productDetailRepository.getAllProductDetail("SP01").size());
    }
}
