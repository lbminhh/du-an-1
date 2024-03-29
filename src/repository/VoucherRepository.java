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
import response.VoucherResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class VoucherRepository {

    public List<VoucherResponse> getAllVoucher() {
        String query = """
                       SELECT * FROM dbo.voucher
                       WHERE customer_id IS NULL
                       """;
        List<VoucherResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new VoucherResponse(rs.getString(1), rs.getBigDecimal(2), rs.getBigDecimal(3),
                        rs.getDate(4), rs.getDate(5), rs.getBoolean(6), rs.getString(7), rs.getString(8)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public boolean getCustomerToVoucher(String idCustomer, String idVoucher) {
        String query = """
                        UPDATE dbo.voucher
                        SET customer_id = ?
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, idCustomer);
            stm.setString(2, idVoucher);
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("voucher");
        }
        return check > 0;
    }

    public VoucherResponse getVoucherById(String id) {
        String query = """
                       SELECT * FROM dbo.voucher
                       WHERE id = ? AND customer_id IS NOT NULL
                       """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return new VoucherResponse(rs.getString(1), rs.getBigDecimal(2), rs.getBigDecimal(3),
                        rs.getDate(4), rs.getDate(5), rs.getBoolean(6), rs.getString(7), rs.getString(8));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public boolean setDisableVoucher(String idVoucher) {
        String query = """
                        UPDATE dbo.voucher
                        SET status = 0
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, idVoucher);
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("voucher");
        }
        return check > 0;
    }
    
    
    public boolean setEnableVoucher(String idVoucher) {
        String query = """
                        UPDATE dbo.voucher
                        SET status = 1
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, idVoucher);
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("voucher");
        }
        return check > 0;
    }

    public static void main(String[] args) {
        VoucherRepository voucherRepository = new VoucherRepository();
        System.out.println(voucherRepository.getVoucherById("DGECDSE20"));
    }
}
