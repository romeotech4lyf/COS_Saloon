package com.tech4lyf.cossaloon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterEmployees extends RecyclerView.Adapter<RecyclerViewAdapterEmployees.ViewHolder> {

    private ArrayList<Employee> employeeList;

    public RecyclerViewAdapterEmployees(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recycler_view_dash_board, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Employee employee = employeeList.get(position);
        holder.title.setText(employee.getName());
        holder.subTitle.setText(employee.getStoreName());
        //    holder.image.setImageResource(R.mipmap.stores);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.triggerOnClickEmployeeListListener(employee);

            }
        });

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
        TextView title;
        TextView subTitle;
        CircleImageView image;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent_recycler_view);
            image = itemView.findViewById(R.id.admin_recyclerView_item_image);
            title = itemView.findViewById(R.id.admin_recyclerView_item_title);
            subTitle = itemView.findViewById(R.id.admin_recyclerView_item_sub_title);


        }

    }


}
