package request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CustomerRequest {
    private String id;
    
    private String fullName;
    
    private String diaChi;
    
    private String phoneNumber;
    
    private boolean gender;
    
    private boolean isBought;
    
    private boolean status;
    
    
}
