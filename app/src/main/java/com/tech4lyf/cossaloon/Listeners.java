package com.tech4lyf.cossaloon;

import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.Models.Store;

import java.util.ArrayList;

public class Listeners {
    private static ArrayList<OnClickStoreListListener> onClickStoreListListeners = new ArrayList<>();
    private static ArrayList<OnClickEmployeeListListener> onClickEmployeeListListeners = new ArrayList<>();

    public static void triggerOnClickStoreListListener(Store store) {

        for (OnClickStoreListListener onClickStoreListListener : onClickStoreListListeners) {
            onClickStoreListListener.onClick(store);
        }
    }

    public static void setOnClickStoreListListener(OnClickStoreListListener onClickStoreListListener) {
        onClickStoreListListeners.add(onClickStoreListListener);
    }

    public static void triggerOnClickEmployeeListListener(Employee employee) {

        for (OnClickEmployeeListListener onClickEmployeeListListener : onClickEmployeeListListeners) {
            onClickEmployeeListListener.onClick(employee);
        }
    }

    public static void setOnClickEmployeeListListener(OnClickEmployeeListListener onClickEmployeeListListener) {
        onClickEmployeeListListeners.add(onClickEmployeeListListener);
    }

    public interface OnClickStoreListListener {
        void onClick(Store store);
    }

    public interface OnClickEmployeeListListener {
        void onClick(Employee employee);
    }


    public interface  OnClickDashBoardItemListener{
        void onClick (int stringId);

    }
    public static ArrayList<OnClickDashBoardItemListener> onClickDashBoardItemListeners= new ArrayList();
    public static void triggerOnClickDashBoardItemListener(int stringId){
        for(OnClickDashBoardItemListener onClickDashBoardItemListener : onClickDashBoardItemListeners){
            onClickDashBoardItemListener.onClick(stringId);
        }
    }
    public static void setOnClickDashBoardItemListener(OnClickDashBoardItemListener onClickDashBoardItemListener){
        onClickDashBoardItemListeners.add(onClickDashBoardItemListener);
    }

    public interface OnBackPressedListener{
        void onBackPressed(Context.OBJECT_TYPE objectType);
    }


    private static ArrayList<OnBackPressedListener> OnBackPressedListeners = new ArrayList<>();
    public static void triggerOnBackPressedListener(Context.OBJECT_TYPE objectType){

        for(OnBackPressedListener OnBackPressedListener : OnBackPressedListeners){
            OnBackPressedListener.onBackPressed(objectType);
        }
    }

    public static void  setOnBackPressedListener (OnBackPressedListener OnBackPressedListener){
        OnBackPressedListeners.add(OnBackPressedListener);
    }


}
