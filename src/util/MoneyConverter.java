/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.math.BigDecimal;

/**
 *
 * @author LE MINH
 */
public class MoneyConverter {

    public static String parse(BigDecimal value) {
        String pattern = "###,###.##";
        java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(pattern);
        return decimalFormat.format(value) + " đ";
    }
    
    public static void main(String[] args) {
        BigDecimal value = new BigDecimal(10000);
    }

}
