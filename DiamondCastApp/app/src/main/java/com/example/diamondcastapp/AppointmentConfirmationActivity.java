package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.diamondcastapp.databinding.ActivityAppointmentConfirmationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AppointmentConfirmationActivity extends NavigationDrawerActivity {
    //this will be base appointment page; will start here from "schedule appointment" and click buttons to move to new screen, then info gets ported back and do next selection

    //creating variables and views that are needed in activity

    Button confirmAppointmentBtn, selectTimeButton;
    TextView displaySelectedAppointmentWith;
    String selectedDate, selectedTime, selectedAppointmentWithID;
    int hour, minute;
    ArrayList<String> selectedServicesList;
    Appointment appointmentClient, appointmentAppointmentWith;
    DatabaseReference databaseReferenceAppointments, databaseReferenceContractors,
            databaseReferenceUsers;
    ArrayList<String> selectedContractorServicesList;
    ActivityAppointmentConfirmationBinding activityAppointmentConfirmationBinding;

    private DatePickerDialog datePickerDialog;
    private Button selectDateButton, changeContractorAgentButton;
    private String selectedAppointmentWithName, currentUserName, selectedAppointmentWithType,
            clientNameForAppointment;
    private User selectedAppointmentWithAsUser, currentUser;
    private AppointmentList appointmentListClient, appointmentListAppointmentWith;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAppointmentConfirmationBinding = ActivityAppointmentConfirmationBinding
                .inflate(getLayoutInflater());
        setContentView(activityAppointmentConfirmationBinding.getRoot());
        allocateActivityTitle("Appointment Confirmation");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initDatePicker();
        //get data passed from previous activity
        Intent intent = getIntent();
        clientNameForAppointment = intent.getStringExtra("clientNameForAppointment");
        selectedAppointmentWithType = intent.getStringExtra("selectedType");
        selectedAppointmentWithID = intent.getStringExtra("selectedAppointmentWithID");
        selectedAppointmentWithName = intent.getStringExtra("selectedContractor");
        selectedContractorServicesList = intent.getStringArrayListExtra("selectedContractorServicesList");

        if(selectedContractorServicesList == null) {
            selectedContractorServicesList = new ArrayList<>();
        }

        //setting views and buttons to correct id

        confirmAppointmentBtn = findViewById(R.id.confirm_appointment_button);
        displaySelectedAppointmentWith = findViewById(R.id.displaySelectedContractorConfirm);
        changeContractorAgentButton = findViewById(R.id.changeContractorButton);

        selectTimeButton = findViewById(R.id.chooseAppointmentTimeButton);
        selectTimeButton.setText(getCurrentTime());
        selectDateButton = findViewById(R.id.chooseAppointmentDateButton);
        selectDateButton.setText(getTodaysDate());
        ChipGroup chipGroup;

        chipGroup = findViewById(R.id.chipGroup);
        selectedServicesList = new ArrayList<>();

        if(selectedAppointmentWithType == null) {
            String displaySelectedTypeNullString = "Choose a Contractor or Agent";
            displaySelectedAppointmentWith.setText(displaySelectedTypeNullString);
        }
        else if(selectedAppointmentWithType.equals("Contractor")){
            String displaySelectedContractorString = "Contractor :" + selectedAppointmentWithName;
            displaySelectedAppointmentWith.setText(displaySelectedContractorString);
        }
        else {
            String displaySelectedAgentString = "Agent: " + selectedAppointmentWithName;
            displaySelectedAppointmentWith.setText(displaySelectedAgentString);
        }

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(dataSnapshot.getKey().equals(currentUserId))
                        currentUserName = snapshot.child("firstName").getValue(String.class);
                        currentUser = snapshot.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AppointmentConfirmationActivity.this,
                        "An error has occurred: "+error, Toast.LENGTH_SHORT).show();
            }
        });

        databaseReferenceAppointments = FirebaseDatabase.getInstance().getReference()
                .child("Appointments");
        databaseReferenceAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(currentUserId)) {
                        appointmentListClient = dataSnapshot.getValue(AppointmentList.class);
                    }
                    if(appointmentListClient == null) {
                        appointmentListClient = new AppointmentList();
                    }
                    if(dataSnapshot.getKey().equals(selectedAppointmentWithID)) {
                        appointmentListAppointmentWith = dataSnapshot
                                .getValue(AppointmentList.class);
                    }
                    if(appointmentListAppointmentWith == null) {
                        appointmentListAppointmentWith = new AppointmentList();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AppointmentConfirmationActivity.this,
                        "An error has occurred: "+error, Toast.LENGTH_SHORT).show();
            }
        });

        //trying to query database by contractor name to find uid
        DatabaseReference databaseReferenceUsers = FirebaseDatabase.getInstance().getReference()
                .child("Users");
        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(dataSnapshot.getKey().equals(selectedAppointmentWithName))
                        selectedAppointmentWithAsUser = dataSnapshot.getValue(User.class);
                    if(selectedAppointmentWithAsUser != null) {
                        selectedAppointmentWithID = selectedAppointmentWithAsUser.getId();
                        databaseReferenceContractors.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if(dataSnapshot.getKey().equals(selectedAppointmentWithID));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(AppointmentConfirmationActivity.this,
                                        "An error has occurred: "+error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AppointmentConfirmationActivity.this,
                        "An error has occurred: "+error, Toast.LENGTH_SHORT).show();
            }
        });

        changeContractorAgentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSearchingActivity();
            }
        });

        for(int i = 0; i < selectedContractorServicesList.size(); i++) {
            Chip chip = new Chip(this);
            ChipDrawable drawable = ChipDrawable.createFromAttributes(this, null,
                    0, R.style.Widget_MaterialComponents_Chip_Choice);
            chip.setChipDrawable(drawable);
            chip.setText(selectedContractorServicesList.get(i));
            chipGroup.addView(chip);
        }

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                selectedServicesList.add(chip.getText().toString());
            }
        });

        confirmAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> selectedChipIds = chipGroup.getCheckedChipIds();
                for (int i = 0; i < selectedChipIds.size(); i++) {
                    Chip chip = chipGroup.findViewById(selectedChipIds.get(i));
                    if (chip.isChecked()) {
                        selectedServicesList.add(chip.getText().toString());
                    }
                }
                if(selectedTime == null) {
                    Toast.makeText(AppointmentConfirmationActivity.this,
                            "You have not selected a time for your appointment, reselect a time.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selectedDate == null) {
                    Toast.makeText(AppointmentConfirmationActivity.this,
                            "You have not selected a date for your appointment, reselect a date.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selectedServicesList.isEmpty()) {
                    Toast.makeText(AppointmentConfirmationActivity.this,
                            "Select a service for your appointment", Toast.LENGTH_SHORT).show();
                }
                else {
                    appointmentClient = new Appointment("Appointment with: "
                            + selectedAppointmentWithName, selectedDate, selectedTime,
                            currentUserId, selectedAppointmentWithID, selectedServicesList, true);

                    appointmentListClient.addAppointment(appointmentClient);

                    appointmentAppointmentWith = new Appointment("Appointment with: "
                            + clientNameForAppointment, selectedDate, selectedTime,
                            selectedAppointmentWithID, currentUserId, selectedServicesList,true);
                    appointmentListAppointmentWith.addAppointment(appointmentAppointmentWith);

                    FirebaseDatabase.getInstance().getReference("Appointments").child(currentUserId)
                            .setValue(appointmentListClient);
                    FirebaseDatabase.getInstance().getReference("Appointments")
                            .child(selectedAppointmentWithID).setValue(appointmentListAppointmentWith)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        String message = "New Appointment on "+selectedDate+" at " +selectedTime;
                                        sendAppointmentMessage(currentUserId, selectedAppointmentWithID, message);

                                        goToHomeScreenActivity();
                                    } else {
                                        Toast.makeText(AppointmentConfirmationActivity.this,
                                                "Failed to submit appointment", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

    }

    //TIME SELECTION
    public void openTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute){
                hour = selectedHour;
                minute = selectedMinute;
                LocalTime time = LocalTime.of(hour,minute);
                String timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(time);
                selectedTime = timeFormat;
                selectTimeButton.setText(timeFormat);
            }

        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style,
                onTimeSetListener, hour, minute,false);
        timePickerDialog.setTitle("SelectTime");
        timePickerDialog.show();
    }

    //DATE SELECTION
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDateString(day, month, year);
                selectedDate = date;
                selectDateButton.setText(date);
                int selYear = year;
                int selMonth = month;
                int selDay = day;
                Calendar cal = Calendar.getInstance();

                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                //CHECK IF DATE IS IN THE FUTURE
                /*if((selDay >= day) && (selMonth >= (month+1)) && (selYear >= year)){
                    //Continues on
                }
                else{
                    Toast.makeText(AppointmentConfirmationActivity.this,
                            "Appointment date cannot be in the past!", Toast.LENGTH_SHORT).show();
                    String dateFalse = makeDateString(day, month+1, year);
                    selectedDate = dateFalse;
                    selectDateButton.setText(dateFalse);
                }
                */

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis()+(1000 * 60 * 60 * 24));
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

    public String getMonthFormat(int month) {
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
        FirebaseDatabase.getInstance().getReference("Users")
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
                    Snackbar.make(findViewById(R.id.loginEnter), "Something went wrong",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AppointmentConfirmationActivity.this,
                        "An error has occurred: " +error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToClientHomeScreenActivity() {
        Intent intent = new Intent(this, ClientHomeScreenActivity.class);
        intent.putExtra("appointment", appointmentClient);
        startActivity(intent);
    }
    private void goToSearchingActivity() {
        Intent intent = new Intent( this, SearchingActivity.class);
        startActivity(intent);
    }

    public void goToAgentHomeScreenActivity() {
        Intent intent = new Intent(this, AgentHomeScreenActivity.class);
        startActivity(intent);
    }

    public void goToContractorHomeScreenActivity() {
        Intent intent = new Intent(this, ContractorHomeScreenActivity.class);
        startActivity(intent);
    }

    private void sendAppointmentMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("Messages").push().setValue(hashMap);
    }
}