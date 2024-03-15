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
import request.SoleShoesRequest;
import response.SoleShoesResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class SoleShoesRepository {

    public List<SoleShoesResponse> getAllSole() {
        String query = """
                       SELECT * FROM dbo.sole_shoes
                       """;
        List<SoleShoesResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new SoleShoesResponse(rs.getLong(1), rs.getString(2), rs.getBoolean(3)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public boolean addSole(SoleShoesRequest soleShoesRequest) {
        if (soleShoesRequest == null) {
            return false;
        }
        String query = """
                        INSERT INTO dbo.sole_shoes
                        (
                            sole_name,
                            status
                        )
                        VALUES
                        (   ?, -- sole_name - nvarchar(255)
                            ? -- status - bit
                            )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, soleShoesRequest.getSoleName());
            stm.setBoolean(2, soleShoesRequest.getStatus());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }

    public boolean updateSole(SoleShoesRequest soleShoesRequest) {
        if (soleShoesRequest == null) {
            return false;
        }
        String query = """
                        UPDATE dbo.sole_shoes
                        SET sole_name = ?, status = ?
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, soleShoesRequest.getSoleName());
            stm.setBoolean(2, soleShoesRequest.getStatus());
            stm.setLong(3, soleShoesRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }

    public boolean deleteSole(SoleShoesRequest soleShoesRequest) {
        String query = """
                        UPDATE dbo.sole_shoes
                        SET status = 0
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, soleShoesRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return check > 0;
    }

    public static void main(String[] args) {
        SoleShoesRepository soleShoesRepository = new SoleShoesRepository();
        System.out.println(soleShoesRepository.getAllSole().size());
    }
}
