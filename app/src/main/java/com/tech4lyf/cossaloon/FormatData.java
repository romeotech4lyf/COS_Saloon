package com.tech4lyf.cossaloon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FormatData {

    public static String setBillItemNames(final ArrayList<String> listItemNameList) {
        if (listItemNameList != null) {
            StringBuilder joined = new StringBuilder("\n");
            for (String listItemName : listItemNameList) {
                joined.append(listItemName).append("\n\n");

            }
            joined.deleteCharAt(joined.length() - 1);
            return joined.toString();
        } else return null;
    }

    public static String setBillItemPrices(final ArrayList<Integer> listItemPriceList) {
        if (listItemPriceList != null) {

            StringBuilder joined = new StringBuilder("\n");
            for (Integer listItemName : listItemPriceList) {
                joined.append(listItemName).append("\n\n");

            }
            joined.deleteCharAt(joined.length() - 1);
            return joined.toString();
        } else return null;
    }

    public static Integer getTotal(ArrayList<Integer> items) {

        Integer total = 0;

        for (Integer item : items) {
            total += item;
        }
        return total;

    }

    public static String getCurrentDeviceDate() {
        return new SimpleDateFormat("dd").format(new Date().getTime());
    }

    public static String getCurrentDeviceMonth() {
        return new SimpleDateFormat("MM").format(new Date().getTime());
    }

    public static String getCurrentDeviceYear() {
        return new SimpleDateFormat("yyyy").format(new Date().getTime());
    }

    public static String getCurrentDeviceHour() {
        return new SimpleDateFormat("hh").format(new Date().getTime());
    }

    public static String getCurrentDeviceMinute() {
        return new SimpleDateFormat("mm").format(new Date().getTime());
    }

    public static String getCurrentDeviceSecond() {
        return new SimpleDateFormat("ss").format(new Date().getTime());
    }

    public static String getCurrentDeviceFullDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date().getTime());
    }

    public static String getCurrentDeviceFullTime() {
        return new SimpleDateFormat("hh:mm:ss").format(new Date().getTime());
    }

    public static String setJobsCountToday(Integer today) {
        return "Today's Jobs : " + today + " ";
    }

    public static String setJobsCountThisMonth(Integer thisMonth) {
        return "This Month's Jobs : " + thisMonth + " ";

    }


}
