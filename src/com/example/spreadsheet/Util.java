package com.example.spreadsheet;

public class Util {

    public static boolean isDoubleValue(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch ( Exception ex) {
            return false;
        }
    }


}
