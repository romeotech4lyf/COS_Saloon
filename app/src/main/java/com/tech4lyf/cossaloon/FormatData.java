package com.tech4lyf.cossaloon;

import java.util.ArrayList;

public class FormatData {
    public static Integer decodeIncome(String encodedIncome) {

        StringBuilder temp = new StringBuilder(encodedIncome);
        return Integer.valueOf(temp.substring(temp.indexOf("&&") + 2));

    }

    public static String encodeIncome(String date, Integer income) {

        return date + "&&" + income;
    }

    public static String setBillItemNames(final ArrayList<String> listItemNameList) {

        StringBuilder joined = new StringBuilder("\n");
        for (String listItemName : listItemNameList) {
            joined.append(listItemName).append("\n\n");

        }
        joined.deleteCharAt(joined.length() - 1);
        return joined.toString();


    }

    public static String setBillItemPrices(final ArrayList<Integer> listItemPriceList) {

        StringBuilder joined = new StringBuilder("\n");
        for (Integer listItemName : listItemPriceList) {
            joined.append(listItemName).append("\n\n");

        }
        joined.deleteCharAt(joined.length() - 1);
        return joined.toString();


    }

    public static Integer getTotal(ArrayList<Integer> items) {

        Integer total = 0;

        for (Integer item : items) {
            total += item;
        }
        return total;

    }
}
