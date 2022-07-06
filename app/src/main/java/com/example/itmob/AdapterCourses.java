package com.example.itmob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterCourses extends RecyclerView.Adapter<AdapterCourses.MyViewHolder> {


    List<ModelCourses> list ;
    Context context ;
    OnClick onClick ;

    public AdapterCourses(List<ModelCourses> list, Context context, OnClick onClick) {
        this.list = list;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycler_,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        ModelCourses modelCourses = list.get(holder.getAdapterPosition());
        holder.tvHeading_item.setText(modelCourses.getName()+" - "+modelCourses.getTrainer());
        holder.tvDuration.setText(modelCourses.getTimeDuration());
        holder.tv_Time.setText(modelCourses.getStartTime());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(holder.getAdapterPosition(),modelCourses);
            }
        });





    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Time , tvDuration , tvHeading_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Time = itemView.findViewById(R.id.tv_Time);
            tvDuration = itemView.findViewById(R.id.tvTotalTime);
            tvHeading_item = itemView.findViewById(R.id.heading_item);



        }
    }

}
