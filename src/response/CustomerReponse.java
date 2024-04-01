/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author admin
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CustomerReponse {



   
    private String id;
    
    private String fullName;
    
    private String diaChi;
    
    private String phoneNumber;
    
    private Boolean gender;
    
    private Boolean isBought;
    
    private Boolean status;
    
    private String time_create;
}
