/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.Exceptions;

/**
 * List all the SalesForce API versions available
 * 
 * @author Adrian Grajeda
 */
public final class APIVersion {
   
    public static final int API_1_0 = 1;
    public static final int API_2_0 = 2;
    public static final int API_3_0 = 3;
    public static final int API_4_0 = 4;
    public static final int API_5_0 = 5;
    public static final int API_6_0 = 6;
    public static final int API_7_0 = 7;
    public static final int API_8_0 = 8;
    public static final int API_9_0 = 9;
    public static final int API_10_0 = 10;
    public static final int API_11_0 = 11;
    public static final int API_12_0 = 12;
    public static final int API_13_0 = 13;
    public static final int API_14_0 = 14;
    public static final int API_15_0 = 15;
    public static final int API_16_0 = 16;
    public static final int API_17_0 = 17;
    public static final int API_18_0 = 18;
    public static final int API_19_0 = 19;
    public static final int API_20_0 = 20;
    public static final int API_21_0 = 21;
    public static final int API_22_0 = 22;
    public static final int API_23_0 = 23;
    public static final int API_24_0 = 24;
    public static final int API_25_0 = 25;
    public static final int API_26_0 = 26;
    public static final int API_27_0 = 27;
    public static final int API_28_0 = 28;
    public static final int API_29_0 = 29;
    public static final int API_30_0 = 30;
    public static final int API_31_0 = 31;
    public static final int API_32_0 = 32;
    public static final int API_33_0 = 33;
    public static final int API_34_0 = 34;
    public static final int API_35_0 = 35;
    public static final int API_36_0 = 36;
    public static final int API_37_0 = 37;
  
    private static final String AS_TEXT_TEMPLATE = "%#.1f";
    
    private APIVersion() {}
    
    public static String[] getAllAsText() {
        Integer[] values = getAll();
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            Integer value = values[i];
            result[i] = String.format(AS_TEXT_TEMPLATE, value.floatValue());
        }
        return result;
    }
    
    public static Integer[] getAll() {
      APIVersion instance = new APIVersion();
      Field[] fields = instance.getClass().getDeclaredFields();
      List<Integer> result = new ArrayList<>();
        for (Field field : fields) {
            if (field.getName().startsWith("AS")) {
                continue;
            }
          try {
              result.add(field.getInt(instance));
          } catch (Exception ex) {
              Exceptions.printStackTrace(ex);
          }          
        }
        return result.toArray(new Integer[result.size()]);
      
    }
}
    
