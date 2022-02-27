package com.example.diamondcastapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeScreenAdapter extends RecyclerView.Adapter<HomeScreenAdapter.MyViewHolder> {
    private View.OnClickListener clickListener;
    private ArrayList<Appointment> mList;
    private Context context;

    public HomeScreenAdapter(ArrayList<Appointment> list, Context context) {
        this.mList = list;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_screen_card, parent, false);
        return new MyViewHolder(v);
    }
    public void setOnItemClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Appointment model = mList.get(position);
        holder.title.setText(model.getTitle());
        holder.date.setText(model.getDate());
        if (clickListener != null) {
            holder.itemView.setOnClickListener(clickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.aTitle);
            date = itemView.findViewById(R.id.aDate);
        }
    }

}
