/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author LE MINH
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    
    private String id;
    
    private String fullname;
    
    private Boolean gender;
    
    private String address;
    
    private String phoneNumber;
    
    private String dateOfBirth;
    
    private Boolean status;
    
}
