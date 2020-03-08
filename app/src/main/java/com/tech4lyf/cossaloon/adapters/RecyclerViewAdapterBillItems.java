package com.tech4lyf.cossaloon.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Service;
import com.tech4lyf.cossaloon.R;

import java.util.ArrayList;

public class RecyclerViewAdapterBillItems extends RecyclerView.Adapter<RecyclerViewAdapterBillItems.ViewHolder> {
    private ArrayList<Service> serviceList;


    public RecyclerViewAdapterBillItems(ArrayList<Service> serviceList) {
        this.serviceList = serviceList;


    }

    public ArrayList<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(ArrayList<Service> serviceList) {
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.put_bill_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Service service = serviceList.get(position);
        Log.d(String.valueOf(serviceList.size()), "size");
        holder.name.setText(service.getName());
        holder.price.setText(String.valueOf(service.getPrice()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    Listeners.triggerOnClickBillItemListener(service, Context.FLAG.DELETE);
                    holder.checkBox.setChecked(false);

                } else {
                    holder.checkBox.setChecked(true);
                    Listeners.triggerOnClickBillItemListener(service, Context.FLAG.ADD);

                }

            }
        });
       /* holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    Listeners.triggerOnClickBillItemListener(service, Context.FLAG.DELETE);
                    holder.checkBox.setChecked(false);

                } else {
                    holder.checkBox.setChecked(true);
                    Listeners.triggerOnClickBillItemListener(service, Context.FLAG.ADD);

                }

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View parent;
        TextView name;
        TextView price;
        CheckBox checkBox;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.employee_put_bill_parent);
            name = itemView.findViewById(R.id.employee_put_bill_item_name);
            price = itemView.findViewById(R.id.employee_put_bill_item_price);
            checkBox = itemView.findViewById(R.id.employee_put_bill_item_checkbox);
        }


    }
}
