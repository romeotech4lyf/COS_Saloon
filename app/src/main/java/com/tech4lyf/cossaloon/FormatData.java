package com.tech4lyf.cossaloon;

import java.util.ArrayList;

public class FormatData {

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
