package com.tech4lyf.cossaloon.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tech4lyf.cossaloon.ChangeOfStyle.getContext;

public class RecyclerViewAdapterEmployees extends RecyclerView.Adapter<RecyclerViewAdapterEmployees.ViewHolder> {

    private ArrayList<Employee> employeeList;

    public RecyclerViewAdapterEmployees(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_employees, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Employee employee = employeeList.get(position);
        if(employee!=null) {
            holder.name.setText(employee.getName());
            holder.storeName.setText(employee.getStoreName());
            holder.areaName.setText(employee.getAreaName());
            if(employee.getdPUriString()!=null)
                Glide.with(getContext()).load(Uri.parse(employee.getdPUriString())).into(holder.image);
            //    Glide.with(getContext()).load(Uri.parse(employee.getdPUriString())).into(holder.storeImage);
            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Listeners.triggerOnClickEmployeeListListener(employee);

                }
            });
        }
    }

    public void setEmployeeList(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public int getItemCount() {
        return this.employeeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parent;
        TextView name;
        TextView storeName;
        TextView areaName;
        CircleImageView image;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.list_item_employees_parent);
            image = itemView.findViewById(R.id.list_item_employees_image);
            name = itemView.findViewById(R.id.list_item_employees_name);
            storeName = itemView.findViewById(R.id.list_item_employees_store_name);
            areaName = itemView.findViewById(R.id.list_item_employees_area_name);


        }

    }


}
