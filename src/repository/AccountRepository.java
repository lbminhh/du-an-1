/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import request.AccountRequest;
import response.AccountResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class AccountRepository {

    public boolean addAccount(AccountRequest accountRequest) {
        if (accountRequest == null) {
            return false;
        }
        String query = """
                       INSERT INTO dbo.account
                       (
                           employee_id,
                           username,
                           password,
                           status,
                           role_id
                       )
                       VALUES
                       (   ?,   -- employee_id - varchar(10)
                           ?, -- username - varchar(50)
                           ?, -- password - varchar(50)
                           ?, -- status - bit
                           ?  -- role_id - int
                           )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, accountRequest.getIdEmployee());
            stm.setString(2, accountRequest.getUsername());
            stm.setString(3, accountRequest.getPassword());
            stm.setBoolean(4, accountRequest.getStatus());
            stm.setInt(5, accountRequest.getIdRole());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }

    public boolean updateAccount(AccountRequest accountRequest) {
        if (accountRequest == null) {
            return false;
        }
        String query = """
                        UPDATE dbo.account
                        SET username = ?, password = ?, status = ?, role_id = ?
                        WHERE employee_id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, accountRequest.getUsername());
            stm.setString(2, accountRequest.getPassword());
            stm.setBoolean(3, accountRequest.getStatus());
            stm.setInt(4, accountRequest.getIdRole());
            stm.setString(5, accountRequest.getIdEmployee());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }

    public boolean checkUsername(String username) {
        String query = """
                        SELECT * FROM dbo.account
                        WHERE employee_id = ?
                        
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, username);
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }

    public AccountResponse getAccountById(String idEmployee) {
        String query = """
                        SELECT employee_id, username, password, account.status , role_name 
                        FROM dbo.account JOIN dbo.role ON role.id = account.role_id
                        WHERE employee_id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, idEmployee);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new AccountResponse(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getString(5));
            
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return null;
    }
    
    public AccountResponse getAccountByUsername(String username) {
        String query = """
                        SELECT employee_id, username, password, account.status , role_name 
                        FROM dbo.account JOIN dbo.role ON role.id = account.role_id
                        WHERE username = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new AccountResponse(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getString(5));
            
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return null;
    }
    
    
    public boolean checkLogin(String username, String password) {
        String query = """
                        SELECT * FROM dbo.account
                        WHERE username = ? AND password = ?
                        
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, username);
            stm.setString(2, password);
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
    
    public static void main(String[] args) {
        AccountRepository accountRepository = new AccountRepository();
        
        AccountRequest accountRequest = new AccountRequest("NV01", "dgffdg", "dfggdf", true, 2);
        System.out.println(accountRepository.checkLogin("dgffdg", "dfggdf"));
        System.out.println(accountRepository.getAccountByUsername("dgffdg"));
    }
}
