package com.tech4lyf.cossaloon.adapters;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Service;
import com.tech4lyf.cossaloon.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterServices extends RecyclerView.Adapter<RecyclerViewAdapterServices.ViewHolder> {


    public ArrayList<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(ArrayList<Service> serviceList) {
        this.serviceList = serviceList;
    }

    public Context.FLAG getFlag() {
        return flag;
    }

    public void setFlag(Context.FLAG flag) {
        this.flag = flag;
    }

    private ArrayList<Service> serviceList;


    public RecyclerViewAdapterServices(ArrayList<Service> serviceList) {
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_services, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Service service = serviceList.get(position);
        holder.serviceName.setText(service.getName());
        holder.servicePrice.setText(service.getPrice().toString());
        holder.enterName.setText(service.getName());
        holder.enterPrice.setText(service.getPrice().toString());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.expandableLayout.isExpanded())
                    holder.expandableLayout.collapse(true);
                else
                    holder.expandableLayout.expand(true);

            }
        });

        holder.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredItemName = null;
                String enteredItemPrice = null;
                Editable enteredItemName_ = holder.enterName.getText();
                Editable enteredItemPrice_ = holder.enterPrice.getText();
                if(enteredItemName_!=null)
                   enteredItemName = enteredItemName_.toString();
                if(enteredItemPrice_ !=null)
                    enteredItemPrice = enteredItemPrice_.toString();

                if(enteredItemName!=null && enteredItemPrice!= null) {


                    if (enteredItemName.equals(""))
                        enteredItemName = service.getName();
                    if (enteredItemPrice.equals(""))
                        enteredItemPrice = service.getPrice().toString();

                    if (enteredItemPrice.equals(service.getPrice().toString()) && enteredItemName.equals(service.getName()))
                        flag = Context.FLAG.CANCEL;
                    else
                        flag = Context.FLAG.UPDATE;

                    Listeners.triggerOnClickServiceListListener(service, enteredItemName, enteredItemPrice, flag);

                }
                    holder.expandableLayout.collapse(true);





            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.expandableLayout.collapse(true);
                Listeners.triggerOnClickServiceListListener(service,null,null, Context.FLAG.DELETE);
            }
        });

    }
    private Context.FLAG flag = Context.FLAG.CANCEL;

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView enter;
        TextView delete;
        EditText enterName;
        EditText enterPrice;
        RelativeLayout parent;
        TextView serviceName;
        TextView servicePrice;
        ExpandableLayout expandableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            expandableLayout = itemView.findViewById(R.id.admin_services_expandable_layout);
            enter = itemView.findViewById(R.id.admin_services_service_enter_item);
            delete = itemView.findViewById(R.id.admin_services_service_delete_item);
            serviceName = itemView.findViewById(R.id.admin_services_service_item_name);
            servicePrice = itemView.findViewById(R.id.admin_services_service_item_price);
            enterName = itemView.findViewById(R.id.admin_services_service_edit_item_name);
            enterPrice = itemView.findViewById(R.id.admin_services_service_edit_item_price);
            parent = itemView.findViewById(R.id.admin_services_recycler_view_parent_for_expanding);
        }
    }
}
