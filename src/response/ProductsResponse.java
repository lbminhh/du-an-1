/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package response;

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
public class ProductsResponse {
    
    private String id;
    
    private String productName;
    
    private int quantity;
    
    private String categoryName;
    
    private String brandName;
    
    private Boolean status;
    
    private String description;
    
}
