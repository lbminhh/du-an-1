/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package response;

import java.math.BigDecimal;
import java.util.Date;
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
public class VoucherResponse {
    
    private String id;
    
    private BigDecimal value;
    
    private BigDecimal valueCondition;
    
    private Date timeStart;
    
    private Date timeEnd;
    
    private Boolean status;
    
    private String type;
    
    private String idCustomer;
    
}
