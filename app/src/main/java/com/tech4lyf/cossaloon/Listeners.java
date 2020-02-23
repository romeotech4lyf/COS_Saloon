package com.tech4lyf.cossaloon;

import java.util.ArrayList;

public class Listeners {
    public interface OnClickRecyclerItemListener{
         void onClick(String id, Context.OBJECT_TYPE object_type);
    }
    private static ArrayList<OnClickRecyclerItemListener> onClickRecyclerItemListeners = new ArrayList<>();
    public static void triggerOnClickRecyclerItemListener(String id, Context.OBJECT_TYPE objectType){

        for(OnClickRecyclerItemListener onClickRecyclerItemListener : onClickRecyclerItemListeners){
            onClickRecyclerItemListener.onClick(id,objectType);
        }
    }

    public static void  setOnClickRecyclerItemListener (OnClickRecyclerItemListener onClickRecyclerItemListener){
        onClickRecyclerItemListeners.add(onClickRecyclerItemListener);
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
