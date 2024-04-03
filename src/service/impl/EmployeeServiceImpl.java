/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import repository.EmployeeRepository;
import request.EmployeeRequest;
import response.EmployeeResponse;
import service.EmployeeService;

/**
 *
 * @author LE MINH
 */
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository = new EmployeeRepository();

    @Override
    public List<EmployeeResponse> getAllEmployee() {
        return employeeRepository.getAllEmployee();
    }

    @Override
    public boolean addEmployee(EmployeeRequest employeeRequest) {
        return employeeRepository.addEmployee(employeeRequest);
    }

    @Override
    public boolean updateEmployee(EmployeeRequest employeeRequest) {
        return employeeRepository.updateEmployee(employeeRequest);
    }

    @Override
    public List<EmployeeResponse> getEmployeeSearch(String value) {
        return employeeRepository.getEmployeeSearch(value);
    }

}
