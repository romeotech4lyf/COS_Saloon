package com.tech4lyf.cossaloon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewAdapterEmployees extends RecyclerView.Adapter<RecyclerViewAdapterEmployees.ViewHolder> {

    private ArrayList<String> titleList= new ArrayList<>();
    private ArrayList<String> subTitleList= new ArrayList<>();
    private ArrayList<String> idList= new ArrayList<>();
    private ArrayList<Integer> imageList = new ArrayList<>();
    private Context.OBJECT_TYPE objectType;
    public void setTitleList(ArrayList<String> titleList) {
        this.titleList = titleList;
    }

    public void setImageList(ArrayList<Integer> imageList) {
        this.imageList = imageList;
    }

    public RecyclerViewAdapterEmployees(ArrayList<String> idList, ArrayList <String> titleList, ArrayList <String> subTitleList, ArrayList<Integer> imageList, Context.OBJECT_TYPE objectType){
        this.imageList = imageList;
        this.titleList = titleList;
        this.subTitleList = subTitleList;
        this.idList = idList;
        this.objectType = objectType;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recycler_view_dash_board, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(titleList.get(position));
        holder.subTitle.setText(subTitleList.get(position));
    //    holder.image.setImageResource(R.mipmap.stores);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.triggerOnClickRecyclerItemListener(titleList.get(position),objectType);

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.titleList.size();
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
