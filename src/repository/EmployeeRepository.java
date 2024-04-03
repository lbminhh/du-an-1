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
import request.EmployeeRequest;
import response.EmployeeResponse;
import util.DBConnect;

/**
 *
 * @author LE MINH
 */
public class EmployeeRepository {

    public List<EmployeeResponse> getAllEmployee() {
        String query = """
                       SELECT id, full_name, gender, address, phone_number, date_of_birth, status
                       FROM dbo.employee
                       ORDER BY time_create DESC
                       """;
        List<EmployeeResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new EmployeeResponse(rs.getString(1), rs.getString(2), rs.getBoolean(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }

    public boolean addEmployee(EmployeeRequest employeeRequest) {
        String query = """
                       DECLARE @now DATETIME2
                        SET @now = CAST(GETDATE() AS DATETIME2)
                       INSERT INTO dbo.employee
                       (
                           id,
                           full_name,
                           date_of_birth,
                           address,
                           phone_number,
                           status,
                           gender,
                           account_id,
                           time_create
                       )
                       VALUES
                       (   ?,   -- id - varchar(10)
                           ?, -- full_name - nvarchar(255)
                           ?, -- date_of_birth - date
                           ?, -- address - nvarchar(255)
                           ?, -- phone_number - varchar(50)
                           ?, -- status - bit
                           ?,
                           NULL,  -- account_id - int
                           @now
                       )
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, getIdEmployee());
            stm.setString(2, employeeRequest.getFullname());
            stm.setObject(3, employeeRequest.getDateOfBirth());
            stm.setString(4, employeeRequest.getAddress());
            stm.setString(5, employeeRequest.getPhoneNumber());
            stm.setBoolean(6, employeeRequest.getStatus());
            stm.setBoolean(7, employeeRequest.getGender());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }

    public boolean updateEmployee(EmployeeRequest employeeRequest) {
        String query = """
                       UPDATE dbo.employee
                       SET full_name = ?, gender = ?, date_of_birth = ?, address = ?, phone_number = ?, status = ?
                       WHERE id = ?
                       """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, employeeRequest.getFullname());
            stm.setBoolean(2, employeeRequest.getGender());
            stm.setObject(3, employeeRequest.getDateOfBirth());
            stm.setString(4, employeeRequest.getAddress());
            stm.setString(5, employeeRequest.getPhoneNumber());
            stm.setBoolean(6, employeeRequest.getStatus());
            stm.setString(7, employeeRequest.getId());
            check = stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return check > 0;
    }

    private static String getIdEmployee() {
        String query = """
                        SELECT MAX(SUBSTRING(id, 3, LEN(id))) FROM dbo.employee
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
        return "NV" + result;
    }

    public List<EmployeeResponse> getEmployeeSearch(String value) {
        String query = """
                       SELECT id, full_name, gender, address, phone_number, date_of_birth, status
                       FROM dbo.employee
                       WHERE full_name LIKE '%' + ? +'%'
                       ORDER BY time_create DESC
                       """;
        List<EmployeeResponse> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, value);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new EmployeeResponse(rs.getString(1), rs.getString(2), rs.getBoolean(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("1");
        }
        return list;
    }
    

    public static void main(String[] args) {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmployeeRequest employeeRequest = new EmployeeRequest("NV04", "fsfsdfd", false, "sdfds", "024", "2003-03-04", true);
        System.out.println(employeeRepository.getEmployeeSearch(""));
    }

}
