 package com.tech4lyf.cossaloon.AdminDashBoardFragments;


 import android.os.Bundle;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;

 import androidx.annotation.NonNull;
 import androidx.annotation.Nullable;
 import androidx.fragment.app.Fragment;

 import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
 import com.tech4lyf.cossaloon.Models.Store;
 import com.tech4lyf.cossaloon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreDetailsFragment extends Fragment {

    private View root;
    private Store store;

    public StoreDetailsFragment(Store store) {
        this.store = store;

    }


    public StoreDetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_store_details, container, false);
    return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AdminHomeActivity.level =2;
    }


}
