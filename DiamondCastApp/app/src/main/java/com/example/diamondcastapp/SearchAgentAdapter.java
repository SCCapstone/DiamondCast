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

public class SearchAgentAdapter extends RecyclerView.Adapter<SearchAgentAdapter.MyViewHolder> {
    private View.OnClickListener clickListener;
    private ArrayList<Agent> mList;
    private Context context;

    public SearchAgentAdapter(ArrayList<Agent> list, Context context) {
        this.mList = list;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.search_result_list, parent, false);
        return new MyViewHolder(v);
    }
    public void setOnItemClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Agent model = mList.get(position);
        holder.avatar.setImageResource(R.drawable.avatar);
        holder.name.setText(model.getFirstName());
        holder.services.setText(model.getLastName());
        if (clickListener != null) {
            holder.itemView.setOnClickListener(clickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView name;
        TextView services;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.searchProfileImage);
            name = itemView.findViewById(R.id.search_result_name);
            services = itemView.findViewById(R.id.search_result_description);
        }
    }

}