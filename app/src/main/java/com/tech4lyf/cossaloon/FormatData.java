package com.tech4lyf.cossaloon;

public class FormatData {
    public static Integer decodeIncome(String encodedIncome) {

        StringBuilder temp = new StringBuilder(encodedIncome);
        return Integer.valueOf(temp.substring(temp.indexOf("&&") + 2));

    }

    public static String encodeIncome(String date, Integer income) {

        return date + "&&" + income;
    }
}
