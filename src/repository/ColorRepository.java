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
import request.ColorRequest;
import response.ColorResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class ColorRepository {
    
    public List<ColorResponse> getAllColor() {
        String query = """
                       SELECT * FROM dbo.color
                       """;
        List<ColorResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ColorResponse(rs.getLong(1), rs.getString(2), rs.getBoolean(3)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public boolean addColor(ColorRequest colorRequest) {
        if (colorRequest == null) {
            return false;
        }
        String query = """
                        INSERT INTO dbo.color
                        (
                            color_name,
                            status
                        )
                        VALUES
                        (   ?, -- color_name - nvarchar(255)
                            ? -- status - bit
                            )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, colorRequest.getColorName());
            stm.setBoolean(2, colorRequest.getStatus());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }
    
    public boolean updateColor(ColorRequest colorRequest) {
        if (colorRequest == null) {
            return false;
        }
        String query = """
                        UPDATE dbo.color
                        SET color_name = ?, status = ?
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, colorRequest.getColorName());
            stm.setBoolean(2, colorRequest.getStatus());
            stm.setLong(3, colorRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;
    }

    public boolean deleteColor(ColorRequest colorRequest) {
        String query = """
                        UPDATE dbo.color
                        SET status = 0
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, colorRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return check > 0;
    }
    
    public static void main(String[] args) {
        ColorRepository colorRepository = new ColorRepository();
        System.out.println(colorRepository.getAllColor());
    }
}
