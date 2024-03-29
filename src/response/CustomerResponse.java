/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package response;

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
public class CustomerResponse {
    
    private String id;
    
    private String fullname;
    
    private String address;
    
    private String phoneNumber;
    
    private Boolean gender;
    
    private String email;
    
    private String typeCustomer;
    
    private Integer numberOfPurchase;
    
    
    
}
