/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

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
public class ProductDetailRequest {
    
    private String idProduct;
    
    private BigDecimal price;
    
    private int quantity;
    
    private Long idColor;
    
    private Long idSize;
    
    private Long idMaterial;
    
    private Long idSole;
    
    private Boolean status;
    
}
