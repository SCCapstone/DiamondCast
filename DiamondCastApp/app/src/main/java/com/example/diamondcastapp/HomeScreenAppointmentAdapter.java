package com.example.diamondcastapp;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeScreenAppointmentAdapter extends RecyclerView.Adapter<HomeScreenAppointmentAdapter.MyViewHolder> {
    private View.OnClickListener clickListener;
    private ArrayList<Appointment> mList;
    private Context context;

    public HomeScreenAppointmentAdapter(ArrayList<Appointment> list, Context context) {
        this.mList = list;
        this.context = context;
    }
    @NonNull
    @Override
    public HomeScreenAppointmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_screen_appointment_list, parent, false);
        return new HomeScreenAppointmentAdapter.MyViewHolder(v);
    }
    public void setOnItemClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull HomeScreenAppointmentAdapter.MyViewHolder holder, int position) {
        Appointment model = mList.get(position);
        holder.appointmentWithName.setText(model.getTitle());
        holder.services.setText(String.join(", ", model.getServices()));
        if (clickListener != null) {
            holder.itemView.setOnClickListener(clickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView appointmentWithName;
        TextView dateTime;
        TextView services;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            appointmentWithName = itemView.findViewById(R.id.home_screen_client_appointment_title);
            dateTime = itemView.findViewById(R.id.home_screen_client_appointment_date_time);
            services = itemView.findViewById(R.id.home_screen_client_appointment_services);
        }
    }

}

