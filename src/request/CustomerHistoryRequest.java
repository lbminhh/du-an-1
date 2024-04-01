/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author admin
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerHistoryRequest {
    
     private String maKh;
    
    private String maNv;
    
    private String maHd;
    
    private String Sdt;
    
    private String time_create;
    
    private Double tongTien;
    
    private Boolean status;
}
