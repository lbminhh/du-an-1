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
import request.CustomerRequest;
import request.CustomerUpdateRequest;
import response.CustomerResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class CustomerRepository {

    public List<CustomerResponse> getListCustomer() {
        String query = """
                       SELECT customer.id, full_name, address, phone_number, gender, email, type_name, number_of_purchase
                       FROM dbo.customer LEFT JOIN dbo.type_customer ON type_customer.id = customer.type_id
                       ORDER BY time_create DESC
                       """;
        List<CustomerResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new CustomerResponse(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getBoolean(5), rs.getString(6), rs.getString(7), rs.getInt(8)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public CustomerResponse getCustomerById(String id) {
        String query = """
                       SELECT customer.id, full_name, address, phone_number, gender, email, type_name, number_of_purchase
                       FROM dbo.customer JOIN dbo.type_customer ON type_customer.id = customer.type_id
                       WHERE customer.id = ?
                       """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new CustomerResponse(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getBoolean(5), rs.getString(6), rs.getString(7), rs.getInt(8));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return null;
    }

    public static String getIdCustomer() {
        String query = """
                        SELECT MAX(SUBSTRING(id, 3, LEN(id))) FROM dbo.customer
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
        return "KH" + result;
    }

    public boolean addCustomer(CustomerRequest customerRequest) {
        String query = """
                        DECLARE @now DATETIME2
                        SET @now = CAST(GETDATE() AS DATETIME2)
                       INSERT INTO dbo.customer
                       (
                           id,
                           full_name,
                           address,
                           phone_number,
                           email,
                           gender,
                           status,
                           type_id,
                           number_of_purchase
                       )
                       VALUES
                       (   ?,   -- id - varchar(10)
                           ?, -- full_name - varchar(255)
                           NULL, -- address - varchar(255)
                           ?, -- phone_number - varchar(50)
                           NULL, -- email - varchar(50)
                           NULL, -- gender - bit
                           NULL, -- status - bit
                           ?,  -- type_id - int
                           0
                       )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, customerRequest.getId());
            stm.setString(2, customerRequest.getFullname());
            stm.setString(3, customerRequest.getPhoneNumber());
            stm.setLong(4, customerRequest.getTypeCustomer());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }
    
    public boolean addCustomer2(CustomerUpdateRequest item) {
        String query = """
                       DECLARE @now DATETIME2
                       SET @now = CAST(GETDATE() AS DATETIME2)
                       INSERT INTO dbo.customer
                       (
                           id,
                           full_name,
                           address,
                           phone_number,
                           email,
                           gender,
                           status,
                           type_id,
                           number_of_purchase,
                           time_create
                       )
                       VALUES
                       (   ?,   -- id - varchar(10)
                           ?, -- full_name - varchar(255)
                           ?, -- address - varchar(255)
                           ?, -- phone_number - varchar(50)
                           ?, -- email - varchar(50)
                           ?, -- gender - bit
                           1, -- status - bit
                           ?,  -- type_id - int
                           0,
                           @now
                       )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, item.getId());
            stm.setString(2, item.getFullname());
            stm.setString(3, item.getAddress());
            stm.setString(4, item.getPhoneNumber());
            stm.setString(5, item.getEmail());
            stm.setBoolean(6, item.getGender());
            stm.setLong(7, item.getTypeCustomer());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }

    public boolean updateCustomer(CustomerRequest customerRequest) {
        String query = """
                       UPDATE dbo.customer
                       SET full_name = ?, phone_number = ?, type_id = ?, number_of_purchase = number_of_purchase + 1, email = ?
                       WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, customerRequest.getFullname());
            stm.setString(2, customerRequest.getPhoneNumber());
            stm.setLong(3, customerRequest.getTypeCustomer());
            stm.setString(4, customerRequest.getEmail());
            stm.setString(5, customerRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }
    
    public boolean updateCustomer2(CustomerUpdateRequest item) {
        String query = """
                       UPDATE dbo.customer
                       SET full_name = ?, phone_number = ?, gender = ?, email = ?, address = ?, type_id = ?
                       WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, item.getFullname());
            stm.setString(2, item.getPhoneNumber());
            stm.setBoolean(3, item.getGender());
            stm.setString(4, item.getEmail());
            stm.setString(5, item.getAddress());
            stm.setLong(6, item.getTypeCustomer());
            stm.setString(7, item.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }

    public int getNumberOfPurchase(String id) {
        String query = """
                       SELECT number_of_purchase FROM dbo.customer
                       WHERE id = ?
                       """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return 0;
    }

    public boolean deleteCustomer() {
        String query = """
                       DELETE dbo.customer
                       WHERE id IN (SELECT customer.id
                                    FROM dbo.customer LEFT JOIN dbo.bills ON bills.customer_id = customer.id
                                    WHERE customer_id IS NULL AND bills.id IS NULL)
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }

    public boolean resetNumberOfPurchase(String id) {
        String query = """
                        UPDATE dbo.customer
                        SET number_of_purchase = 0
                        WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, id);
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }

    public static void main(String[] args) {
        CustomerRepository customerRepository = new CustomerRepository();
        System.out.println(customerRepository.getListCustomer());
    }

}
