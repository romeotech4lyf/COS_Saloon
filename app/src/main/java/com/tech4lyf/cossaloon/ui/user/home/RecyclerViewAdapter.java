package com.tech4lyf.cossaloon.ui.user.home;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tech4lyf.cossaloon.AdminHome.isReturn;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> titleList= new ArrayList<>();
    private ArrayList<Integer> imageList = new ArrayList<>();

    public void setTitleList(ArrayList<String> titleList) {
        this.titleList = titleList;
    }

    public void setImageList(ArrayList<Integer> imageList) {
        this.imageList = imageList;
    }

    RecyclerViewAdapter(ArrayList <String> titleList, ArrayList<Integer> imageList){
        this.imageList = imageList;
        this.titleList = titleList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recycler_view_dash_board, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(titleList.get(position));
        holder.image.setImageResource(imageList.get(position));

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isReturn = false;
                Listeners.triggerOnClickRecyclerItemListener(titleList.get(position));

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
        ImageView image;


        ViewHolder(@NonNull View itemView) {
        super(itemView);

        parent = itemView.findViewById(R.id.parent_recycler_view);
        image = itemView.findViewById(R.id.dashBoard_item_image);
        title = itemView.findViewById(R.id.dashBoard_item_title);



    }

}

}
