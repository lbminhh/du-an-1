/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import request.ProductsRequest;
import request.ProductsSearchRequest;
import response.ProductsResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class ProductsRepository {

    public List<ProductsResponse> getAllProducts() {
        String query = """
                       SELECT products.id, product_name, 1, category_name, brand_name, products.status, description
                       FROM dbo.products JOIN dbo.category ON category.id = products.category_id
                       			 JOIN dbo.brand ON brand.id = products.brand_id
                       ORDER BY time_create DESC
                       """;
        List<ProductsResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ProductsResponse(rs.getString(1), rs.getString(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public boolean addProducts(ProductsRequest productsRequest) {
        if (productsRequest == null) {
            return false;
        }
        String query = """
                        DECLARE @now DATETIME2;
                        SET @now = CAST(GETDATE() AS DATETIME2)
                        INSERT INTO dbo.products
                        (
                            id,
                            product_name,
                            description,
                            category_id,
                            brand_id,
                            status,
                            time_create
                        )
                        VALUES
                        (   ?,   -- id - varchar(10)
                            ?,  -- product_name - nvarchar(255)
                            ?, -- description - nvarchar(max)
                            ?,    -- category_id - int
                            ?,    -- brand_id - int
                            ?, -- status - bit
                            @now
                        )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, productsRequest.getId());
            stm.setString(2, productsRequest.getProductName());
            stm.setString(3, productsRequest.getDescription());
            stm.setLong(4, productsRequest.getIdCategory());
            stm.setLong(5, productsRequest.getIdBrand());
            stm.setBoolean(6, productsRequest.getStatus());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }

    public boolean updateProducts(ProductsRequest productsRequest) {
        if (productsRequest == null) {
            return false;
        }
        String query = """
                        UPDATE dbo.products
                        SET product_name = ?, description = ?, category_id = ?, brand_id = ?, status = ?
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, productsRequest.getProductName());
            stm.setString(2, productsRequest.getDescription());
            stm.setLong(3, productsRequest.getIdCategory());
            stm.setLong(4, productsRequest.getIdBrand());
            stm.setBoolean(5, productsRequest.getStatus());
            stm.setString(6, productsRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("3");
        }
        return check > 0;
    }

    public boolean deleteProducts(String id) {
        String query = """
                        UPDATE dbo.products
                        SET status = ?
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setBoolean(1, false);
            stm.setString(2, id);
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return check > 0;
    }

    public int getTotalPageProducts() {
        int totalPage = 1;
        String query = """
                     SELECT COUNT(*)
                     FROM dbo.products
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                totalPage = (int) Math.ceil((rs.getDouble(1) / 6) + 0.5);
                System.out.println("okk");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("5");
        }
        return totalPage;
    }

    public String getIdProducts() {
        String query = """
                        SELECT MAX(SUBSTRING(id, 3, LEN(id))) FROM dbo.products
                       """;
        String result = "";
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1) + 1;
                if (id < 10) {
                    result = "0" + id;
                } else {
                    result = String.valueOf(id);
                }

            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return result;
    }

    public List<ProductsResponse> searchListProducts(ProductsSearchRequest item) {
        String query = """
                       DECLARE @product_name NVARCHAR(255), @brand_name NVARCHAR(255), @category_name NVARCHAR(255), @status BIT
                       SET @product_name = ?
                       SET @brand_name = ?
                       SET @category_name = ?
                       SET @status = ?
                       SELECT products.id, product_name, 1, category_name, brand_name, products.status, description
                       FROM dbo.products JOIN dbo.category ON category.id = products.category_id
                                         JOIN dbo.brand ON brand.id = products.brand_id
                       WHERE (product_name LIKE '%' + @product_name + '%' OR @product_name LIKE '')
                       	AND (brand_name LIKE @brand_name OR @brand_name LIKE '')
                       	AND (category_name LIKE @category_name OR @category_name LIKE '')
                       	AND (products.status = @status OR @status IS NULL)
                       ORDER BY time_create DESC
                       """;
        List<ProductsResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, item.getProductName());
            stm.setString(2, item.getBrandName());
            stm.setString(3, item.getCategoryName());
            if (item.getStatus() != null) {
                stm.setBoolean(4, item.getStatus());
            } else {
                stm.setNull(4, 0);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ProductsResponse(rs.getString(1), rs.getString(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public static void main(String[] args) {
        ProductsRepository productsRepository = new ProductsRepository();
        System.out.println(productsRepository.searchListProducts(new ProductsSearchRequest("f", "", "", null)));
    }

}
