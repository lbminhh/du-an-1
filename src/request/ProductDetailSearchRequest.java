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
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailSearchRequest {
    
    private String colorName;
    
    private String sizeName;
    
    private String materialName;
    
    private String soleName;
    
    private String idProduct;
    
    private Boolean status;
    
    private String brandName;
    
}
