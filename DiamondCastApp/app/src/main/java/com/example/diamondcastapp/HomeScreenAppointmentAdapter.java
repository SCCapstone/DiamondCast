package com.example.diamondcastapp;

import android.content.Context;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kotlin.collections.unsigned.UArraysKt;

public class HomeScreenAppointmentAdapter extends RecyclerView.Adapter<HomeScreenAppointmentAdapter.MyViewHolder> {
    private View.OnClickListener clickListener;
    private ArrayList<Appointment> mList;
    private Context context;
    FirebaseAuth fAuth;
    String userID;

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

        //popup menu for edit/delete appointments
        holder.appointmentPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.homescreen_appointment_popup_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.popup_edit:
                                return true;
                            case R.id.popup_delete:
                                fAuth = FirebaseAuth.getInstance();
                                userID = fAuth.getCurrentUser().getUid();
                                String abs_location = Integer.toString(holder.getAbsoluteAdapterPosition());
                                FirebaseDatabase.getInstance().getReference().child("Appointments").child(userID).child("appointmentList").child(abs_location).removeValue();
                                //removeItem(holder.getAbsoluteAdapterPosition());
                                mList.remove(mList.get(holder.getAbsoluteAdapterPosition()));
                                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                                notifyItemRangeChanged(holder.getAbsoluteAdapterPosition(), mList.size());
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView appointmentWithName;
        private TextView dateTime;
        private TextView services;
        private ImageView profileImage;
        private ImageButton appointmentPopup;
        private static final String TAG = "MyViewHolder";
        FirebaseAuth fAuth;
        String userID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            appointmentWithName = itemView.findViewById(R.id.home_screen_client_appointment_title);
            dateTime = itemView.findViewById(R.id.home_screen_client_appointment_date_time);
            services = itemView.findViewById(R.id.home_screen_client_appointment_services);
            profileImage = itemView.findViewById(R.id.searchProfileImage);
            appointmentPopup = itemView.findViewById(R.id.popup_appt);
            //appointmentPopup.setOnClickListener(this);
        }
        /* popup menu for edit/delete appts
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + getAbsoluteAdapterPosition());
            showPopupMenu(v);
        }
        private void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.homescreen_appointment_popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        //Menu popup for edit/delete appointments
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.popup_edit:
                    return true;
                case R.id.popup_delete:
                    fAuth = FirebaseAuth.getInstance();
                    userID = fAuth.getCurrentUser().getUid();
                    String abs_location = Integer.toString(getAbsoluteAdapterPosition());
                    FirebaseDatabase.getInstance().getReference().child("Appointments").child(userID).child("appointmentList").child(abs_location).removeValue();
                    removeItem(getAbsoluteAdapterPosition());
                    return true;
                default:
                    return false;
            }
        }
        public void removeItem(int position){
            //update arraylist and adapter
            mList.remove(mList.get(position));
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mList.size());
        }

         */

    }
}

