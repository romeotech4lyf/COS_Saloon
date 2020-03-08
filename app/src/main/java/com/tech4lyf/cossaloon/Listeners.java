package com.tech4lyf.cossaloon;

import com.tech4lyf.cossaloon.Models.Area;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.Models.Service;
import com.tech4lyf.cossaloon.Models.Store;

import java.util.ArrayList;

public class Listeners {


    public static ArrayList<OnClickDashBoardItemListener> onClickDashBoardItemListeners = new ArrayList();
    private static ArrayList<OnClickStoreListListener> onClickStoreListListeners = new ArrayList<>();
    private static ArrayList<OnClickAreaListListener> onClickAreaListListeners = new ArrayList<>();
    private static ArrayList<OnClickServiceListListener> onClickServiceListListeners = new ArrayList<>();
    private static ArrayList<OnClickEmployeeListListener> onClickEmployeeListListeners = new ArrayList<>();
    private static ArrayList<OnClickBillItemListListener> onClickBillItemListListeners = new ArrayList<>();
    private static ArrayList<OnBackPressedListener> OnBackPressedListeners = new ArrayList<>();


    public static void triggerOnClickStoreListListener(Store store) {

        for (OnClickStoreListListener onClickStoreListListener : onClickStoreListListeners) {
            onClickStoreListListener.onClick(store);
        }
    }

    public static void triggerOnClickBillItemListener(Service service, Context.FLAG flag) {

        for (OnClickBillItemListListener onClickBillItemListListener : onClickBillItemListListeners) {
            onClickBillItemListListener.onClick(service,flag);
        }
    }

    public static void triggerOnClickServiceListListener(Service service, String itemName, String itemPrice, Context.FLAG flag) {

        for (OnClickServiceListListener onClickServiceListListener : onClickServiceListListeners) {
            onClickServiceListListener.onClick(service, itemName, itemPrice, flag);
        }
    }

    public static void triggerOnClickAreaListListener(Area area) {

        for (OnClickAreaListListener onClickAreaListListener : onClickAreaListListeners) {
            onClickAreaListListener.onClick(area);
        }
    }

    public static void setOnClickStoreListListener(OnClickStoreListListener onClickStoreListListener) {
        onClickStoreListListeners.add(onClickStoreListListener);
    }

    public static void setOnClickBillItemListListeners(OnClickBillItemListListener onClickBillItemListListener) {
        onClickBillItemListListeners.add(onClickBillItemListListener);
    }

    public static void setOnClickAreaListListener(OnClickAreaListListener onClickAreaListListener) {
        onClickAreaListListeners.add(onClickAreaListListener);
    }

    public static void triggerOnClickEmployeeListListener(Employee employee) {

        for (OnClickEmployeeListListener onClickEmployeeListListener : onClickEmployeeListListeners) {
            onClickEmployeeListListener.onClick(employee);
        }
    }

    public static void setOnClickEmployeeListListener(OnClickEmployeeListListener onClickEmployeeListListener) {
        onClickEmployeeListListeners.add(onClickEmployeeListListener);
    }

    public static void setOnClickServiceListListener(OnClickServiceListListener onClickServiceListListener) {
        onClickServiceListListeners.add(onClickServiceListListener);
    }

    public static void triggerOnClickDashBoardItemListener(int stringId) {
        for (OnClickDashBoardItemListener onClickDashBoardItemListener : onClickDashBoardItemListeners) {
            onClickDashBoardItemListener.onClick(stringId);
        }
    }

    public static void setOnClickDashBoardItemListener(OnClickDashBoardItemListener onClickDashBoardItemListener) {
        onClickDashBoardItemListeners.add(onClickDashBoardItemListener);
    }

    public static void triggerOnBackPressedListener(Context.OBJECT_TYPE objectType) {

        for (OnBackPressedListener OnBackPressedListener : OnBackPressedListeners) {
            OnBackPressedListener.onBackPressed(objectType);
        }
    }

    public static void setOnBackPressedListener(OnBackPressedListener OnBackPressedListener) {
        OnBackPressedListeners.add(OnBackPressedListener);
    }

    public interface OnClickStoreListListener {
        void onClick(Store store);
    }

    public interface OnClickAreaListListener {
        void onClick(Area area);
    }

    public interface OnClickBillItemListListener {
        void onClick(Service service, Context.FLAG flag);
    }


    public interface OnClickEmployeeListListener {
        void onClick(Employee employee);
    }

    public interface OnClickServiceListListener {
        void onClick(Service service, String itemName, String itemPrice, Context.FLAG flag);
    }

    public interface OnClickDashBoardItemListener {
        void onClick(int stringId);

    }

    public interface OnBackPressedListener {
        void onBackPressed(Context.OBJECT_TYPE objectType);
    }




}
