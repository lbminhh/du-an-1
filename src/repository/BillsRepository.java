/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import request.BillsRequest;
import request.BillsSearchRequest;
import response.BillsResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class BillsRepository {

    public List<BillsResponse> getAllBillsToday() {
        String query = """
                       SELECT bills.id, bills.time_create, total_money, employee.id, customer.full_name, phone, bills.status, bills.customer_id, reduce_money, id_voucher
                       FROM dbo.bills JOIN dbo.employee ON employee.id = bills.employee_id
                       			   JOIN dbo.customer ON customer.id = bills.customer_id
                       WHERE bills.status = 0
                       ORDER BY bills.time_create DESC
                       """;
        List<BillsResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new BillsResponse(rs.getString(1), rs.getTimestamp(2), rs.getBigDecimal(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7), rs.getString(8), rs.getBigDecimal(9), rs.getString(10)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public BillsResponse getBills() {
        String query = """
                       SELECT TOP 1 * FROM dbo.bills
                       ORDER BY time_create DESC
                       """;
        BillsResponse billsResponse = new BillsResponse();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                billsResponse.setId(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return billsResponse;
    }

    public boolean addBill(BillsRequest billsRequest) {
        if (billsRequest == null) {
            return false;
        }
        String query = """
                       DECLARE @now DATETIME2
                       SET @now = CAST(GETDATE() AS DATETIME2)
                       INSERT INTO dbo.bills
                       (
                           id,
                           customer_id,
                           status,
                           phone,
                           total_money,
                           payment_id,
                           employee_id,
                           id_voucher,
                           reduce_money,
                           time_create
                       )
                       VALUES
                       (   ?,   -- id - varchar(10)
                           ?,   -- customer_id - varchar(10)
                           ?, -- status - bit
                           ?, -- phone - varchar(55)
                           ?, -- total_money - money
                           NULL, -- payment_id - int
                           ?, -- employee_id - varchar(10)
                           ?, -- id_voucher - varchar(10)
                           ?,  -- reduce_money - money
                           @now
                        )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, getIdBills());
            stm.setString(2, billsRequest.getIdCustomer());
            stm.setBoolean(3, billsRequest.getStatus());
            stm.setString(4, billsRequest.getPhoneNumber());
            stm.setBigDecimal(5, billsRequest.getTotalMoney());
            stm.setString(6, billsRequest.getIdEmployee());
            stm.setString(7, billsRequest.getIdVoucher());
            stm.setBigDecimal(8, billsRequest.getReduceMoney());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }

    public boolean updateBill(BillsRequest billsRequest) {
        System.out.println(billsRequest);
        if (billsRequest == null) {
            return false;
        }
        String query = """
                       UPDATE dbo.bills
                       SET customer_id = ?, status = ?, phone = ?, total_money = ?, id_voucher = ?,
                            payment_id = ?, employee_id = ?, reduce_money = ?
                       WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, billsRequest.getIdCustomer());
            stm.setBoolean(2, billsRequest.getStatus());
            stm.setString(3, billsRequest.getPhoneNumber());
            stm.setBigDecimal(4, billsRequest.getTotalMoney());
            stm.setString(5, billsRequest.getIdVoucher());
            if (billsRequest.getIdPayment() != null) {
                stm.setLong(6, billsRequest.getIdPayment());
            } else {
                stm.setLong(6, 1);
            }
            stm.setString(7, billsRequest.getIdEmployee());
            stm.setBigDecimal(8, billsRequest.getReduceMoney());
            stm.setString(9, billsRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1 update");
        }
        return check > 0;
    }

    private static String getIdBills() {
        String query = """
                        SELECT MAX(SUBSTRING(id, 3, LEN(id))) FROM dbo.bills
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
        return "HD" + result;
    }

    public boolean updateBillCustomer(String idBill, String idCustomer, String phone) {
        String query = """
                        UPDATE dbo.bills
                        SET customer_id = ?, phone = ?
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, idCustomer);
            stm.setString(2, phone);
            stm.setString(3, idBill);
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return check > 0;
    }

    public BillsRequest getBillsById(String id) {
        String query = """
                        SELECT id, customer_id, status, phone, total_money, id_voucher, payment_id, employee_id, reduce_money 
                        FROM dbo.bills
                        WHERE id = ?
                       """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new BillsRequest(rs.getString(1), rs.getString(2), rs.getBoolean(3), rs.getString(4),
                        rs.getBigDecimal(5), rs.getString(6), rs.getLong(7), rs.getString(8), rs.getBigDecimal(9));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return null;
    }

    public boolean isExistVoucherInBills(String idVoucher) {
        String query = """
                        SELECT * FROM dbo.bills
                        WHERE id_voucher = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, idVoucher);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                check++;
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return check > 0;
    }

    public boolean setNullVoucherInBills(String id) {
        String query = """
                        UPDATE dbo.bills
                        SET id_voucher = NULL
                        WHERE id = ? 
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, id);
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("4");
        }
        return check > 0;
    }
    
    public List<BillsResponse> getCustomerBill(String idCustomer) {
        String query = """
                       SELECT bills.id, bills.time_create, total_money, employee.id, customer.full_name, phone, bills.status, bills.customer_id, reduce_money, id_voucher
                       FROM dbo.bills JOIN dbo.employee ON employee.id = bills.employee_id
                       			   JOIN dbo.customer ON customer.id = bills.customer_id
                       WHERE customer_id = ?
                       ORDER BY bills.time_create DESC
                       """;
        List<BillsResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, idCustomer);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new BillsResponse(rs.getString(1), rs.getTimestamp(2), rs.getBigDecimal(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7), rs.getString(8), rs.getBigDecimal(9), rs.getString(10)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }
    
    public List<BillsResponse> getAllBills() {
        String query = """
                       SELECT bills.id, bills.time_create, total_money, employee.id, customer.full_name, phone, bills.status, bills.customer_id, reduce_money, id_voucher
                       FROM dbo.bills JOIN dbo.employee ON employee.id = bills.employee_id
                       			   JOIN dbo.customer ON customer.id = bills.customer_id
                       ORDER BY bills.time_create DESC
                       """;
        List<BillsResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new BillsResponse(rs.getString(1), rs.getTimestamp(2), rs.getBigDecimal(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7), rs.getString(8), rs.getBigDecimal(9), rs.getString(10)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }
    
    public List<BillsResponse> getAllBillsSearch(BillsSearchRequest billsSearchRequest) {
        String query = """
                       DECLARE @value NVARCHAR(255), @status BIT, @payment INT, @time_start DATE, @time_end DATE
                       SET @value = ?
                       SET @status = ?
                       SET @payment = ?
                       SET @time_start = CAST(? AS DATE)
                       SET @time_end = CAST(? AS DATE)
                       SELECT bills.id, bills.time_create, total_money, employee.id, customer.full_name, phone, bills.status, bills.customer_id, reduce_money, id_voucher
                       FROM dbo.bills JOIN dbo.employee ON employee.id = bills.employee_id
                                      JOIN dbo.customer ON customer.id = bills.customer_id
                       WHERE (bills.id = @value OR employee.full_name LIKE '%' + @value + '%' OR dbo.customer.full_name LIKE '%' + @value + '%' OR @value IS NULL)
                       	AND (bills.status = @status OR @status IS NULL)
                       	AND (payment_id = @payment OR @payment IS NULL)
                       	AND (CAST(bills.time_create AS DATE) >= @time_start OR @time_start IS NULL)
                       	AND (CAST(bills.time_create AS DATE) <= @time_end OR @time_end IS NULL)
                       ORDER BY bills.time_create DESC
                       """;
        List<BillsResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, billsSearchRequest.getValueSearch());
            stm.setObject(2, billsSearchRequest.getStatusSearch());
            stm.setObject(3, billsSearchRequest.getPaymentSearch());
            stm.setString(4, billsSearchRequest.getTimeStartSearch());
            stm.setString(5, billsSearchRequest.getTimeEndSearch());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new BillsResponse(rs.getString(1), rs.getTimestamp(2), rs.getBigDecimal(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7), rs.getString(8), rs.getBigDecimal(9), rs.getString(10)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public static void main(String[] args) {

        BillsRepository billsRepository = new BillsRepository();
        BillsSearchRequest billsSearchRequest = new BillsSearchRequest("Minh", null, null, "2024-03-29", "2024-03-29");
        System.out.println(billsRepository.getAllBillsSearch(billsSearchRequest).size());
    }
}
