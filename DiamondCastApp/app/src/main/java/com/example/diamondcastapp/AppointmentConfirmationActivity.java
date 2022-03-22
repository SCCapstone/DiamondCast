package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppointmentConfirmationActivity extends AppCompatActivity {
    //this will be base appointment page; will start here from "schedule appointment" and click buttons to move to new screen, then info gets ported back and do next selection

    TextView displaySelectedDate;
    Button confirmAppointmentBtn;
    TextView displaySelectedTime;
    TextView displaySelectedContractor;
    TextView displaySelectedServices;
    String selectedContractor;
    String selectedDate;
    int selectedHour;
    int selectedMinute;
    int hour, minute;
    Button selectTimeButton;
    ArrayList<String> selectedServicesList;
    Appointment appointment;
    DatabaseReference databaseReference;

    private DatePickerDialog datePickerDialog;

    private Button selectDateButton;


    private AppointmentList appointmentList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_confirmation);
        initDatePicker();
        //get data passed from previous activity
        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("selectedDate");
        selectedHour = intent.getIntExtra("hour", 0);
        selectedMinute = intent.getIntExtra("minute", 0);
        selectedContractor = intent.getStringExtra("selectedContractor");
        selectedServicesList = intent.getStringArrayListExtra("selectedServices");


        //setting views and buttons to correct id
        displaySelectedDate =  findViewById(R.id.displaySelectedDateNew);
        confirmAppointmentBtn = findViewById(R.id.confirm_appointment_button);
        displaySelectedContractor = findViewById(R.id.displaySelectedContractorConfirm);
        displaySelectedServices = findViewById(R.id.displaySelectedServicesConfirm);
        selectTimeButton = findViewById(R.id.chooseAppointmentTimeButton);
        selectTimeButton.setText(getCurrentTime());
        selectDateButton = findViewById(R.id.chooseAppointmentDateButton);
        selectDateButton.setText(getTodaysDate());
        String selectedServicesDisplayString = String.join(", ", selectedServicesList);

        displaySelectedServices.setText(selectedServicesDisplayString);

        displaySelectedDate.setText(selectedDate);
        displaySelectedTime = findViewById(R.id.displaySelectedTimeNew);
        String selectedTimeDisplay = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
        displaySelectedTime.setText(selectedTimeDisplay);
        String displaySelectedContractorString = "Appointment with: "+selectedContractor;
        displaySelectedContractor.setText(displaySelectedContractorString);

        String selectedService = "service choice";

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Appointments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(currentUserId)) {
                        appointmentList = dataSnapshot.getValue(AppointmentList.class);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        appointment = new Appointment("Appointment with: "+selectedContractor, selectedDate, selectedTimeDisplay, selectedServicesList, true);



        confirmAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointmentList.addAppointment(appointment);
                FirebaseDatabase.getInstance().getReference("Appointments").child(currentUserId).setValue(appointmentList)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    goToHomeScreenActivity();
                                } else{
                                    Snackbar.make(findViewById(R.id.confirm_appointment_button), "Failed to submit appointment", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
    public void openTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute){
                hour = selectedHour;
                minute = selectedMinute;
                LocalTime time = LocalTime.of(hour,minute);
                String timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(time);
                selectTimeButton.setText(timeFormat);
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute,false);

        timePickerDialog.setTitle("SelectTime");
        timePickerDialog.show();

    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDateString(day, month, year);
                selectDateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + "/" + day + "/" + year;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getCurrentTime() {
        LocalTime time = LocalTime.now();
        String timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(time);
        return timeFormat;
    }
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month++;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);
    }
    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";
        return "INV";
    }
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }


    public void goToHomeScreenActivity() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("Clients")
                .child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null && user.getUserType() == UserType.Client) {
                    goToClientHomeScreenActivity();
                } else if (user != null && user.getUserType() == UserType.Agent) {
                    goToAgentHomeScreenActivity();
                } else if (user != null && user.getUserType() == UserType.Contractor) {
                    goToContractorHomeScreenActivity();
                } else {
                    Snackbar.make(findViewById(R.id.loginEnter), "Something went wrong", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(findViewById(R.id.loginEnter), "An error has occurred: " + error, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void goToClientHomeScreenActivity() {
        Intent intent = new Intent(this, ClientHomeScreenActivity.class);
        intent.putExtra("appointment", appointment);
        startActivity(intent);
    }

    private void goToAgentHomeScreenActivity() {
        Intent intent = new Intent(this, AgentHomeScreenActivity.class);
        startActivity(intent);
    }

    private void goToContractorHomeScreenActivity() {
        Intent intent = new Intent(this, ContractorHomeScreenActivity.class);
        startActivity(intent);
    }


}