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
import request.SizeRequest;
import response.SizeResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class SizeRepository {

    public List<SizeResponse> getAllSize() {
        String query = """
                       SELECT * FROM dbo.size
                       """;
        List<SizeResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new SizeResponse(rs.getLong(1), rs.getString(2), rs.getBoolean(3)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public boolean addSize(SizeRequest sizeRequest) {
        if (sizeRequest == null) {
            return false;
        }
        String query = """
                        INSERT INTO dbo.size
                        (
                            size_name,
                            status
                        )
                        VALUES
                        (   ?, -- size_name - nvarchar(255)
                            ? -- status - bit
                            )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, sizeRequest.getSizeName());
            stm.setBoolean(2, sizeRequest.getStatus());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }

    public boolean updateSize(SizeRequest sizeRequest) {
        if (sizeRequest == null) {
            return false;
        }
        String query = """
                        UPDATE dbo.size
                        SET size_name = ?, status = ?
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, sizeRequest.getSizeName());
            stm.setBoolean(2, sizeRequest.getStatus());
            stm.setLong(3, sizeRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }

    public boolean deleteSize(SizeRequest sizeRequest) {
        System.out.println(sizeRequest.getSizeName());
        String query = """
                        UPDATE dbo.size
                        SET status = 0
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, sizeRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return check > 0;
    }

    public static void main(String[] args) {
        SizeRepository sizeRepository = new SizeRepository();
        System.out.println(sizeRepository.getAllSize().size());
    }
}
