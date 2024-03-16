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
import request.MaterialRequest;
import response.MaterialResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class MaterialRepository {

    public List<MaterialResponse> getAllMaterial() {
        String query = """
                       SELECT * FROM dbo.material
                       """;
        List<MaterialResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new MaterialResponse(rs.getLong(1), rs.getString(2), rs.getBoolean(3)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public boolean addMaterial(MaterialRequest materialRequest) {
        if (materialRequest == null) {
            return false;
        }
        String query = """
                        INSERT INTO dbo.material
                        (
                            material_name,
                            status
                        )
                        VALUES
                        (   ?, -- material_name - nvarchar(255)
                            ? -- status - bit
                            )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, materialRequest.getMaterialName());
            stm.setBoolean(2, materialRequest.getStatus());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }

    public boolean updateMaterial(MaterialRequest materialRequest) {
        if (materialRequest == null) {
            return false;
        }
        String query = """
                        UPDATE dbo.material
                        SET material_name = ?, status = ?
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, materialRequest.getMaterialName());
            stm.setBoolean(2, materialRequest.getStatus());
            stm.setLong(3, materialRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }

    public boolean deleteMaterial(MaterialRequest materialRequest) {
        String query = """
                        UPDATE dbo.material
                        SET status = 0
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, materialRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return check > 0;
    }

    public static void main(String[] args) {
        MaterialRepository materialRepository = new MaterialRepository();
        System.out.println(materialRepository.getAllMaterial().size());
    }
}
