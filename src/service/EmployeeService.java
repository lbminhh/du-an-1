/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import request.EmployeeRequest;
import response.EmployeeResponse;

/**
 *
 * @author LE MINH
 */
public interface EmployeeService {

    List<EmployeeResponse> getAllEmployee();

    boolean addEmployee(EmployeeRequest employeeRequest);

    boolean updateEmployee(EmployeeRequest employeeRequest);
    
    List<EmployeeResponse> getEmployeeSearch(String value);

}
