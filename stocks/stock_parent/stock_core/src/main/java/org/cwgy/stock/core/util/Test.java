package org.cwgy.stock.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        String[] items = {"1", "2", "3","4","5", "6"};
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        
        Arrays.asList(items).stream().filter(i->Integer.valueOf(i)%2!=0).forEach(i->{
            map.computeIfAbsent(i, v->new ArrayList<String>()).add(i);
        });
        
        System.out.println(map);
    }

}
