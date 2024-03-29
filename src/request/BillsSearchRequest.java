/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 *
 * @author LE MINH
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillsSearchRequest {
    
    private String valueSearch;
    
    private Boolean statusSearch;
    
    private Integer paymentSearch;
    
    private String timeStartSearch;
    
    private String timeEndSearch;
    
}
