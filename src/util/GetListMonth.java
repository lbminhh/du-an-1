/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author LE MINH
 */
public class GetListMonth {
    
    public static Map<Integer, Integer> getListMonth() {
        Map<Integer, Integer> list = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);

        for (int i = currentMonth + 1; i <= 12; i++) {
            list.put(i, currentYear - 1);
        }
        for (int i = 1; i <= currentMonth; i++) {
            list.put(i, currentYear);
        }

        Map<Integer, Integer> result = list.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        return result;
    }
    
}
