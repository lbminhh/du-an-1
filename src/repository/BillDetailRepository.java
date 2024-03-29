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
import request.BillDetailRequest;
import request.BillDetailUpdateRequest;
import response.BillDetailResponse;
import response.ProductCardResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class BillDetailRepository {

    public boolean addBillDetail(BillDetailRequest billDetailRequest) {
        String query = """
                       DECLARE @now DATETIME2
                       SET @now = CAST(GETDATE() AS DATETIME2)
                       INSERT INTO dbo.bill_detail
                       (
                           bill_id,
                           quantity,
                           total_money,
                           status,
                           product_detail_id,
                           time_create
                       )
                       VALUES
                       (   ?, -- bill_id - varchar(10)
                           ?, -- quantity - int
                           ?, -- total_money - money
                           ?, -- status - bit
                           ?,  -- product_detail_id - int
                           @now
                       )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, billDetailRequest.getIdBill());
            stm.setInt(2, billDetailRequest.getQuantity());
            stm.setBigDecimal(3, billDetailRequest.getTotalMoney());
            stm.setBoolean(4, billDetailRequest.getStatus());
            stm.setLong(5, billDetailRequest.getIdProductDetail());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }
    
    public boolean updateBillDetail(BillDetailUpdateRequest billDetailUpdateRequest) {
        String query = """
                       UPDATE dbo.bill_detail
                       SET quantity = ?, total_money = ?
                       WHERE product_detail_id = ? AND bill_id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setInt(1, billDetailUpdateRequest.getQuantity());
            stm.setBigDecimal(2, billDetailUpdateRequest.getTotalMoney());
            stm.setLong(3, billDetailUpdateRequest.getIdProductDetail());
            stm.setString(4, billDetailUpdateRequest.getIdBill());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }
    
    

    public List<ProductCardResponse> getListProductCard(String idBill) {
        String query = """
                       SELECT product_name, color_name, size_name, material_name, sole_name, bill_detail.quantity, total_money,  product_detail.id
                       FROM dbo.bill_detail JOIN dbo.product_detail ON product_detail.id = bill_detail.product_detail_id
                       					 JOIN dbo.products ON products.id = product_detail.product_id
                       					 JOIN dbo.color ON color.id = dbo.product_detail.color_id
                       					 JOIN dbo.material ON material.id = product_detail.material_id
                       					 JOIN dbo.sole_shoes ON sole_shoes.id = product_detail.sole_id
                       					 JOIN dbo.size ON size.id = product_detail.size_id
                       WHERE bill_id = ?
                       ORDER BY bill_detail.time_create DESC
                       """;
        List<ProductCardResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, idBill);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ProductCardResponse(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getBigDecimal(7), rs.getLong(8)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public boolean checkCardItemInDetailBill(Long idProductDetail, String idBill) {
        String query = """
                       SELECT * FROM dbo.bill_detail
                       WHERE product_detail_id = ? AND bill_id = ?
                       """;
        int check = 0;
        List<ProductCardResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, idProductDetail);
            stm.setString(2, idBill);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                check++;
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }
    
    public boolean deleteBillDetail(Long idProductDetail, String idBill) {
        String query = """
                       DELETE FROM dbo.bill_detail
                       WHERE product_detail_id = ? AND bill_id = ?
                       """;
        int check = 0;
        List<ProductCardResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, idProductDetail);
            stm.setString(2, idBill);
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }
    
    public List<BillDetailResponse> getBillDetailByBills(String idBill) {
        String query = """
                       SELECT bill_id, product_name, bill_detail.quantity, total_money, bill_detail.status
                       FROM dbo.bill_detail JOIN dbo.product_detail ON product_detail.id = bill_detail.product_detail_id
                       						JOIN dbo.products ON products.id = product_detail.product_id
                       WHERE bill_id = ?
                       """;
        List<BillDetailResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, idBill);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new BillDetailResponse(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getBigDecimal(4),
                        rs.getBoolean(5)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }
    
    
    
    public static void main(String[] args) {
        BillDetailRepository billDetailRepository = new BillDetailRepository();
        System.out.println(billDetailRepository.getListProductCard("HD01"));
    }
}
