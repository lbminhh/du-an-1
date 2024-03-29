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
public class BillDetailRequest {
    
    private Long id;
    
    private String idBill;
    
    private Long idProductDetail;
    
    private Integer quantity;
    
    private BigDecimal totalMoney;
    
    private Boolean status;
}
