/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author LE MINH
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCardResponse {

    private String productName;
    
    private String colorName;
    
    private String sizeName;
    
    private String materialName;
    
    private String soleName;
    
    private Integer quantity;
    
    private BigDecimal totalMoney;
    
    private Long idProductDetail;
}
