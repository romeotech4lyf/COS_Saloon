package com.tech4lyf.cossaloon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Area;
import com.tech4lyf.cossaloon.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewAdapterAreas extends RecyclerView.Adapter<RecyclerViewAdapterAreas.ViewHolder> {


    private ArrayList<Area> areaList;

    public RecyclerViewAdapterAreas(ArrayList<Area> areaList) {
        this.areaList = areaList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recycler_view_employees, parent, false));
    }

    public void setAreaList(ArrayList<Area> areaList) {
        this.areaList = areaList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Area area = areaList.get(position);
        holder.name.setText(area.getName());
        holder.title.setVisibility(View.GONE);
        holder.subTitle.setVisibility(View.GONE);
        holder.image.setImageResource(R.mipmap.area);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.triggerOnClickAreaListListener(area);

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.areaList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parent;
        TextView title;
        TextView subTitle;
        TextView name;
        CircleImageView image;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent_recycler_view);
            image = itemView.findViewById(R.id.admin_recyclerView_employee_image);
            title = itemView.findViewById(R.id.admin_recyclerView_store_name);
            name = itemView.findViewById(R.id.admin_recyclerView_employee_name);
            subTitle = itemView.findViewById(R.id.admin_recyclerView_area_name);


        }

    }

}


