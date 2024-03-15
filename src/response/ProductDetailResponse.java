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
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponse {
    
    private Long id;
    
    private String productName;
    
    private String brandName;
    
    private BigDecimal price;
    
    private String color;
    
    private String size;
    
    private String soleShoes;
    
    private String material;
    
    private Integer quantity;
    
    private Boolean status;
}
