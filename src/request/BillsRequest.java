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
public class BillsRequest {
    
    private String id;
    
    private String idCustomer;
    
    private Boolean status;
    
    private String phoneNumber;
    
    private BigDecimal totalMoney;
    
    private String idVoucher;
    
    private Long idPayment;
    
    private String idEmployee;
    
    private BigDecimal reduceMoney;
    
}
