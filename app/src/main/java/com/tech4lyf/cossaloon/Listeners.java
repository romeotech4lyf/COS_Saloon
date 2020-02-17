package com.tech4lyf.cossaloon;

import java.util.ArrayList;

public class Listeners {
    public interface OnClickRecyclerItemListener{
        public void onClick(String title);
    }
    private static ArrayList<OnClickRecyclerItemListener> onClickRecyclerItemListeners = new ArrayList<>();
    public static void triggerOnClickRecyclerItemListener(String title){

        for(OnClickRecyclerItemListener onClickRecyclerItemListener : onClickRecyclerItemListeners){
            onClickRecyclerItemListener.onClick(title);
        }
    }

    public static void  setOnClickRecyclerItemListener (OnClickRecyclerItemListener onClickRecyclerItemListener){
        onClickRecyclerItemListeners.add(onClickRecyclerItemListener);
    }





    public interface OnBackPressedListener{
        public void onBackPressed();
    }
    private static ArrayList<OnBackPressedListener> OnBackPressedListeners = new ArrayList<>();
    public static void triggerOnBackPressedListener(){

        for(OnBackPressedListener OnBackPressedListener : OnBackPressedListeners){
            OnBackPressedListener.onBackPressed();
        }
    }

    public static void  setOnBackPressedListener (OnBackPressedListener OnBackPressedListener){
        OnBackPressedListeners.add(OnBackPressedListener);
    }
}
