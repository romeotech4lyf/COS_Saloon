package com.tech4lyf.cossaloon.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tech4lyf.cossaloon.Models.Service;
import com.tech4lyf.cossaloon.R;

import java.util.List;

public class ListViewAdapterBillIItems extends ArrayAdapter<Service> {
    private Context context;
    private List<Service> services;
    private List<Boolean> isCheckedList;


    public ListViewAdapterBillIItems(@NonNull Context context, int resource, @NonNull List<Service> services) {
        super(context, R.layout.put_bill_list_item, services);
        this.services = services;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        //    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.put_bill_list_item,null);
        TextView billItemName = parent.findViewById(R.id.employee_put_bill_item_name);
        TextView billItemPrice = parent.findViewById(R.id.recycler_view_bill_item_price);
        CheckBox checkBox = parent.findViewById(R.id.employee_put_bill_item_checkbox);
//        checkBox.setChecked(isCheckedList.get(position));
        billItemName.setText(services.get(position).getName());
        billItemPrice.setText(services.get(position).getPrice());
        return parent;
    }


}
