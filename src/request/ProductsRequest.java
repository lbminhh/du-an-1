/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package request;

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
public class ProductsRequest {
    
    private String id;
    
    private String productName;
    
    private String description;
    
    private Long idCategory;
    
    private Long idBrand;
    
    private Long idDiscount;
    
    private Boolean status;
}
