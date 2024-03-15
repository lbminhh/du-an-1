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
import request.BrandRequest;
import response.BrandResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class BrandRepository {

    public List<BrandResponse> getAllBrand() {
        String query = """
                       SELECT * FROM dbo.brand
                       """;
        List<BrandResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new BrandResponse(rs.getLong(1), rs.getString(2), rs.getBoolean(3)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public boolean addBrand(BrandRequest brandRequest) {
        if (brandRequest == null) {
            return false;
        }
        String query = """
                        INSERT INTO dbo.brand
                        (
                            brand_name,
                            status
                        )
                        VALUES
                        (   ?, -- brand_name - nvarchar(255)
                            ? -- status - bit
                            )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, brandRequest.getBrandName());
            stm.setBoolean(2, brandRequest.getStatus());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }

    public boolean updateBrand(BrandRequest brandRequest) {
        if (brandRequest == null) {
            return false;
        }
        String query = """
                        UPDATE dbo.brand
                        SET brand_name = ?, status = ?
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, brandRequest.getBrandName());
            stm.setBoolean(2, brandRequest.getStatus());
            stm.setLong(3, brandRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }

    public boolean deleteBrand(BrandRequest brandRequest) {
        String query = """
                        UPDATE dbo.brand
                        SET status = 0
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, brandRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return check > 0;
    }
}
