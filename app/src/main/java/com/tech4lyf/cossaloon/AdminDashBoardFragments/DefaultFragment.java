package com.tech4lyf.cossaloon.AdminDashBoardFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.R;

public class DefaultFragment extends Fragment implements View.OnClickListener {

    private CardView stores;
    private CardView employees;
    private CardView manage;
    private CardView settings;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_admin_dash_board_default, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();


    }

    private void initializeViews() {
        stores = root.findViewById(R.id.dashBoard_admin_stores_card);
        employees = root.findViewById(R.id.dashBoard_admin_employees_card);
        manage = root.findViewById(R.id.dashBoard_admin_manage_card);
        settings = root.findViewById(R.id.dashBoard_admin_settings_card);

        stores.setOnClickListener(this);
        employees.setOnClickListener(this);
        manage.setOnClickListener(this);
        settings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dashBoard_admin_stores_card:
                Listeners.triggerOnClickDashBoardItemListener(R.string.stores);
                break;

            case R.id.dashBoard_admin_employees_card:
                Listeners.triggerOnClickDashBoardItemListener(R.string.employee);
                break;

            case R.id.dashBoard_admin_manage_card:
                Listeners.triggerOnClickDashBoardItemListener(R.string.administration);
                break;

            case R.id.dashBoard_admin_settings_card:
                Listeners.triggerOnClickDashBoardItemListener(R.string.settings);
                break;

            default:
                break;

        }

    }
}