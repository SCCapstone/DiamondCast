package com.example.diamondcastapp;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull HomeScreenAppointmentAdapter.MyViewHolder holder, int position) {
        Appointment model = mList.get(position);
        holder.appointmentWithName.setText(model.getTitle());
        holder.services.setText(String.join(", ", model.getServices()));
        holder.dateTime.setText(model.getDate() + " at "+ model.getTime());

        //Get profile image
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+model.getAppointmentWithId()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.profileImage);
            }
        });

        if (clickListener != null) {
            holder.itemView.setOnClickListener(clickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView appointmentWithName, dateTime, services;
        private ImageView profileImage;
        private static final String TAG = "MyViewHolder";

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            appointmentWithName = itemView.findViewById(R.id.home_screen_client_appointment_title);
            dateTime = itemView.findViewById(R.id.home_screen_client_appointment_date_time);
            services = itemView.findViewById(R.id.home_screen_client_appointment_services);
            profileImage = itemView.findViewById(R.id.searchProfileImage);
        }
    }
}

