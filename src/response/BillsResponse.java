/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

/**
 *
 * @author LE MINH
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillsResponse {

    private String id;

    private Timestamp timeCreate;

    private BigDecimal totalMoney;

    private String idEmployee;

    private String customerName;

    private String phoneNumber;

    private Boolean status;

    private String idCustomer;
    
    private BigDecimal reduceMoney;
    
    private String idVoucher;
}
