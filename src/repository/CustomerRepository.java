/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.util.ArrayList;
import java.util.List;
import response.CustomerReponse;
import request.CustomerRequest;
import util.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import request.CustomerHistoryRequest;
import request.CustomerHistorySearchRequest;
import request.CustomerSearchRequest;
import response.CustomerHistoryReponse;

/**
 *
 * @author admin
 */
public class CustomerRepository {

    public List<CustomerReponse> getAllCustomer() {

        String query = """
                        SELECT id,full_name,address,phone_number,gender,is_bought,status,time_create
                        FROM dbo.customer 
                        WHERE status = 1
                        ORDER BY time_create DESC
                        """;
        List<CustomerReponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new CustomerReponse(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getBoolean(5), rs.getBoolean(6), rs.getBoolean(7),rs.getString(8)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    
    public List<CustomerHistoryReponse> getAllCustomerHistory() {

        String query = """
                        SELECT 
                       customer.id,             --MaKH
                       employee.full_name,      --TenNV
                       bills.id,                --MaHD
                       bills.phone,             --SDT HD
                       bills.time_create,       --Thời gian giao dịch
                       bills.total_money,       --Tổng giao dịch
                       bills.status             --trạng thái hóa đơn -- ? đã thanh toán or chưa thoanh toán
                        FROM dbo.bills JOIN dbo.employee ON dbo.employee.id = dbo.bills.employee_id
                        			JOIN dbo.customer on dbo.customer.id = dbo.bills.customer_id
                        
                        """;
        List<CustomerHistoryReponse> listh = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                listh.add(new CustomerHistoryReponse(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getDouble(6), rs.getBoolean(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return listh;
    }

    public boolean addCustomer(CustomerRequest customerRequest) {

        if (customerRequest == null) {
            return false;
        }
        String query = """
                DECLARE @now DATETIME2;
                SET @now = CAST(GETDATE() AS DATETIME2)
                INSERT INTO dbo.customer
                (
                id,
                full_name,
                address,
                phone_number,
                gender,
                is_bought,
                status,
                time_create
                )
                VALUES
                (   
                ?,   -- id - varchar(10)
                ?,   -- full_name - nvarchar(255)
                ?,   -- address - nvarchar(max)
                ?,   -- phone_number - nvarchar(10)
                ?,   -- gender - bit
                ?,   -- is_bought - bit
                ?,   -- status bit
                @now -- time_create
                )                    
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, customerRequest.getId());
            stm.setString(2, customerRequest.getFullName());
            stm.setString(3, customerRequest.getDiaChi());
            stm.setString(4, customerRequest.getPhoneNumber());
            stm.setBoolean(5, customerRequest.isGender());
            stm.setBoolean(6, customerRequest.isBought());
            stm.setBoolean(7, customerRequest.isStatus());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("2");
        }
        return check > 0;

    }
    
     public boolean updateCustomer(CustomerRequest customerRequest) {
        if (customerRequest == null) {
            return false;
        }
        String query = """
                        UPDATE dbo.customer
                        SET full_name = ?, address = ?, phone_number = ?, gender = ?
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, customerRequest.getFullName());
            stm.setString(2, customerRequest.getDiaChi());
            stm.setString(3, customerRequest.getPhoneNumber());
            stm.setBoolean(4, customerRequest.isGender());
            stm.setString(5, customerRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("3");
        }
        return check > 0;
    }

    public boolean deleteCustomer(String id) {
        String query = """
                        UPDATE dbo.customer
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

    public List<CustomerReponse> searchListCustomer(CustomerSearchRequest item) {
         String query = """
                       DECLARE @customer_id VARCHAR(10), @customer_name NVARCHAR(255)
                       SET @customer_id = ?
                       SET @customer_name = ?
                       SELECT id,full_name,address,phone_number,gender,is_bought,status,time_create
                       FROM dbo.customer 
                       WHERE (id LIKE '%' + @customer_id + '%' OR @customer_id LIKE '')
                       		AND (full_name LIKE @customer_name OR @customer_name LIKE '')
                       ORDER BY time_create DESC
                       """;
        List<CustomerReponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, item.getID());
            stm.setString(2, item.getFullName());
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new CustomerReponse(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getBoolean(5), rs.getBoolean(6),rs.getBoolean(7), rs.getString(8)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public List<CustomerHistoryReponse> searchListCustomerHistory(CustomerHistorySearchRequest item) {
        String query = """
                       SELECT 
                       customer.id,             --MaKH
                       employee.full_name,      --TenNV
                       bills.id,                --MaHD
                       bills.phone,             --SDT HD
                       bills.time_create,       --Thời gian giao dịch
                       bills.total_money,       --Tổng giao dịch
                       bills.status             --trạng thái hóa đơn -- ? đã thanh toán or chưa thoanh toán
                       FROM dbo.bills JOIN dbo.employee ON dbo.employee.id = dbo.bills.employee_id
                        			JOIN dbo.customer on dbo.customer.id = dbo.bills.customer_id
                       WHERE customer.id LIKE ? 
                        
                        """;
        List<CustomerHistoryReponse> listh = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, item.getID());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                listh.add(new CustomerHistoryReponse(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getDouble(6), rs.getBoolean(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return listh;
    }

}
