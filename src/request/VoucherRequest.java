/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package request;

import java.math.BigDecimal;
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
public class VoucherRequest {
    
    private String id;
    
    private BigDecimal value;
    
    private BigDecimal condition;
    
    private String timeStart;
    
    private String timeEnd;
    
    private Boolean status;
    
    private String type;
    
}
