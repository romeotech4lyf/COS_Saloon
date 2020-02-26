package com.tech4lyf.cossaloon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tech4lyf.cossaloon.FormatData;
import com.tech4lyf.cossaloon.Models.Bill;
import com.tech4lyf.cossaloon.R;

import java.util.ArrayList;

public class RecyclerViewAdapterBills extends RecyclerView.Adapter<RecyclerViewAdapterBills.ViewHolder> {

    private ArrayList<Bill> billList;

    public RecyclerViewAdapterBills(ArrayList<Bill> billList) {
        this.billList = billList;
    }

    public ArrayList<Bill> getBillList() {
        return billList;
    }

    public void setBillList(ArrayList<Bill> billList) {
        this.billList = billList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recycler_view_bills, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Bill bill = billList.get(position);
        holder.employeeName.setText(bill.getEmployeeName());
        holder.itemName.setText(FormatData.setBillItemNames(bill.getListItems()));
        holder.itemPrice.setText(FormatData.setBillItemPrices(bill.getListItemPrices()));
//        holder.total.setText(FormatData.getTotal(bill.getListItemPrices()));
    }

    @Override
    public int getItemCount() {
        return this.billList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView parent;
        TextView itemName;
        TextView itemPrice;
        TextView total;
        TextView employeeName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent_recycler_view_bills);
            itemName = itemView.findViewById(R.id.recycler_view_bill_item_name);
            itemPrice = itemView.findViewById(R.id.recycler_view_bill_item_price);
            total = itemView.findViewById(R.id.recycler_view_bill_total_price);
            employeeName = itemView.findViewById(R.id.recycler_view_bill_employee_name);
        }
    }
}

