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
import request.MaterialRequest;
import request.ProductDetailRequest;
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
                        ORDER BY dbo.product_detail.time_create DESC
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
    
    public boolean addProductDetail(ProductDetailRequest productDetailRequest) {
        if (productDetailRequest == null) {
            return false;
        }
        String query = """
                        DECLARE @now DATETIME2;
                        SET @now = CAST(GETDATE() AS DATETIME2)
                        INSERT INTO dbo.product_detail
                        (
                            product_id,
                            price,
                            quantity,
                            color_id,
                            size_id,
                            material_id,
                            sole_id,
                            status,
                            time_create
                        		)
                        VALUES
                        (   
                            ?,   -- product_id - varchar(10)
                            ?, -- price - money
                            ?,    -- quantity - int
                            ?,    -- color_id - int
                            ?,    -- size_id - int
                            ?,    -- material_id - int
                            ?,    -- sole_id - int
                            ?, -- status - bit
                            @now  -- time_create - datetime2(7)
                        )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, productDetailRequest.getIdProduct());
            stm.setBigDecimal(2, productDetailRequest.getPrice());
            stm.setInt(3, productDetailRequest.getQuantity());
            stm.setLong(4, productDetailRequest.getIdColor());
            stm.setLong(5, productDetailRequest.getIdSize());
            stm.setLong(6, productDetailRequest.getIdMaterial());
            stm.setLong(7, productDetailRequest.getIdSole());
            stm.setBoolean(8, productDetailRequest.getStatus());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }
    
    public boolean updateProductDetail(ProductDetailRequest productDetailRequest) {
        if (productDetailRequest == null) {
            return false;
        }
        String query = """
                        UPDATE dbo.product_detail
                        SET price = ?, quantity = ?, color_id = ?, size_id = ?, material_id = ?, sole_id = ?, status = ?
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setBigDecimal(1, productDetailRequest.getPrice());
            stm.setInt(2, productDetailRequest.getQuantity());
            stm.setLong(3, productDetailRequest.getIdColor());
            stm.setLong(4, productDetailRequest.getIdSize());
            stm.setLong(5, productDetailRequest.getIdMaterial());
            stm.setLong(6, productDetailRequest.getIdSole());
            stm.setBoolean(7, productDetailRequest.getStatus());
            stm.setLong(8, productDetailRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }
    
    public boolean deleteProductDetail(ProductDetailRequest productDetailRequest) {
        if (productDetailRequest == null) {
            return false;
        }
        String query = """
                        UPDATE dbo.product_detail
                        SET status = 0
                        WHERE product_id = ?
                        )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, productDetailRequest.getIdProduct());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }
    
    
    public static void main(String[] args) {
        ProductDetailRepository productDetailRepository = new ProductDetailRepository();
        System.out.println(productDetailRepository.getAllProductDetail("SP01").size());
    }
}
