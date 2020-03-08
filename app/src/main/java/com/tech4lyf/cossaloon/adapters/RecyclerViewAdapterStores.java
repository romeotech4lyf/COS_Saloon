package com.tech4lyf.cossaloon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Store;
import com.tech4lyf.cossaloon.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewAdapterStores extends RecyclerView.Adapter<RecyclerViewAdapterStores.ViewHolder> {


    private ArrayList<Store> storeList;

    public RecyclerViewAdapterStores(ArrayList<Store> storeList) {
        this.storeList = storeList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recycler_view_employees, parent, false));
    }

    public void setStoreList(ArrayList<Store> storeList) {
        this.storeList = storeList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Store store = storeList.get(position);
        holder.title.setText(store.getName());
        holder.subTitle.setText(store.getAreaName());
        //    holder.image.setImageResource(R.mipmap.stores);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.triggerOnClickStoreListListener(store);

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.storeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parent;
        TextView title;
        TextView subTitle;
        CircleImageView image;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent_recycler_view);
            image = itemView.findViewById(R.id.admin_recyclerView_employee_image);
            title = itemView.findViewById(R.id.admin_recyclerView_employee_name);
            subTitle = itemView.findViewById(R.id.admin_recyclerView_store_name);


        }

    }

}

