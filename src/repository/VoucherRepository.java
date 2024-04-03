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
import request.VoucherRequest;
import response.VoucherResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class VoucherRepository {

    public List<VoucherResponse> getAllVoucherNoCustomer() {
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
    
    public List<VoucherResponse> getAllVoucher() {
        String query = """
                       SELECT * FROM dbo.voucher
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
    
    public boolean addVoucher(VoucherRequest voucherRequest) {
        if (voucherRequest == null) {
            return false;
        }
        String query = """
                        INSERT INTO dbo.voucher
                        (
                            id,
                            voucher_value,
                            voucher_condition,
                            time_start,
                            time_end,
                            status,
                            type_voucher,
                            customer_id
                        )
                        VALUES
                        (   ?,   -- id - varchar(10)
                            ?, -- voucher_value - money
                            ?, -- voucher_condition - money
                            ?, -- time_start - date
                            ?, -- time_end - date
                            ?, -- status - bit
                            ?, -- type_voucher - nvarchar(255)
                            NULL  -- customer_id - varchar(10)
                        )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, voucherRequest.getId());
            stm.setBigDecimal(2, voucherRequest.getValue());
            stm.setBigDecimal(3, voucherRequest.getCondition());
            stm.setObject(4, voucherRequest.getTimeStart());
            stm.setObject(5, voucherRequest.getTimeEnd());
            stm.setBoolean(6, voucherRequest.getStatus());
            stm.setString(7, voucherRequest.getType());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("voucher");
        }
        return check > 0;
    }
    
    public boolean updateVoucher(VoucherRequest voucherRequest) {
        if (voucherRequest == null) {
            return false;
        }
        String query = """
                        UPDATE dbo.voucher
                        SET voucher_value = ?, voucher_condition = ?, time_start = ?, time_end = ?, status = ?, type_voucher = ? 
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setBigDecimal(1, voucherRequest.getValue());
            stm.setBigDecimal(2, voucherRequest.getCondition());
            stm.setObject(3, voucherRequest.getTimeStart());
            stm.setObject(4, voucherRequest.getTimeEnd());
            stm.setBoolean(5, voucherRequest.getStatus());
            stm.setString(6, voucherRequest.getType());
            stm.setString(7, voucherRequest.getId());
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
