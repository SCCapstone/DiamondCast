package com.example.diamondcastapp;

import static android.content.ContentValues.TAG;

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
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.TimePicker;

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

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppointmentConfirmationActivity extends NavigationDrawerActivity {
    //this will be base appointment page; will start here from "schedule appointment" and click buttons to move to new screen, then info gets ported back and do next selection

    //creating variables and views that are needed in activity

    Button confirmAppointmentBtn;

    TextView displaySelectedAppointmentWith;


    String selectedDate;
    String selectedTime;
    int hour, minute;
    Button selectTimeButton;
    ArrayList<String> selectedServicesList;
    Appointment appointmentClient;
    Appointment appointmentAppointmentWith;
    DatabaseReference databaseReferenceAppointments;
    DatabaseReference databaseReferenceContractors;
    DatabaseReference databaseReferenceUsers;
    String selectedAppointmentWithID;

    Contractor selectedContractor;
    ArrayList<String> selectedContractorServicesList;

    private DatePickerDialog datePickerDialog;

    private Button selectDateButton;

    private String selectedAppointmentWithName;

    private Button changeContractorAgentButton;

    private User selectedAppointmentWithAsUser;

    private User currentUser;

    private String selectedAppointmentWithType;



    private AppointmentList appointmentListClient;
    private AppointmentList appointmentListAppointmentWith;
    private AppointmentList appointmentListContractor;
    private AppointmentList appointmentListAgent;

    ActivityAppointmentConfirmationBinding activityAppointmentConfirmationBinding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAppointmentConfirmationBinding = ActivityAppointmentConfirmationBinding.inflate(getLayoutInflater());
        setContentView(activityAppointmentConfirmationBinding.getRoot());
        allocateActivityTitle("Appointment Confirmation");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initDatePicker();
        //get data passed from previous activity
        Intent intent = getIntent();
        selectedAppointmentWithType = intent.getStringExtra("selectedType");
        selectedAppointmentWithID = intent.getStringExtra("selectedAppointmentWithID");
        selectedAppointmentWithName = intent.getStringExtra("selectedContractor");
        selectedContractorServicesList = intent.getStringArrayListExtra("selectedContractorServicesList");


        if(selectedContractorServicesList == null)
            selectedContractorServicesList = new ArrayList<>();


        //setting views and buttons to correct id

        confirmAppointmentBtn = findViewById(R.id.confirm_appointment_button);
        displaySelectedAppointmentWith = findViewById(R.id.displaySelectedContractorConfirm);
        changeContractorAgentButton = findViewById(R.id.changeContractorButton);

        selectTimeButton = findViewById(R.id.chooseAppointmentTimeButton);
        selectTimeButton.setText(getCurrentTime());
        selectDateButton = findViewById(R.id.chooseAppointmentDateButton);
        selectDateButton.setText(getTodaysDate());
        // String selectedServicesDisplayString = String.join(", ", selectedServicesList);
        ChipGroup chipGroup;

        chipGroup = findViewById(R.id.chipGroup);
        selectedServicesList = new ArrayList<>();



        //displaySelectedServices.setText(selectedServicesDisplayString);

        //   displaySelectedDate.setText(selectedDate);

        //  String selectedTimeDisplay = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
        // displaySelectedTime.setText(selectedTimeDisplay);
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

        String selectedService = "service choice";

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(dataSnapshot.getKey().equals(currentUserId))
                        currentUser = snapshot.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseReferenceAppointments = FirebaseDatabase.getInstance().getReference().child("Appointments");
        databaseReferenceAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(currentUserId))
                        appointmentListClient = dataSnapshot.getValue(AppointmentList.class);
                    if(appointmentListClient == null)
                        appointmentListClient = new AppointmentList();

                    if(dataSnapshot.getKey().equals(selectedAppointmentWithID))
                        appointmentListAppointmentWith = dataSnapshot.getValue(AppointmentList.class);
                    if(appointmentListAppointmentWith == null)
                        appointmentListAppointmentWith = new AppointmentList();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //trying to query database by contractor name to find uid
        DatabaseReference databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child("Users");

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

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
            ChipDrawable drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Choice);
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
                    Snackbar.make(findViewById(R.id.chooseAppointmentTimeButton), "Select a time for your appointment", Snackbar.LENGTH_SHORT).show();
                }
                else if(selectedDate == null) {
                    Snackbar.make(findViewById(R.id.chooseAppointmentDateButton), "Select a date for your appointment", Snackbar.LENGTH_SHORT).show();
                }
                else if(selectedServicesList.isEmpty()) {
                    Snackbar.make(findViewById(R.id.chipGroup), "Select a service for your appointment", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    appointmentClient = new Appointment("Appointment with: " + selectedAppointmentWithName, selectedDate, selectedTime, currentUserId, selectedAppointmentWithID, selectedServicesList, true);
                    appointmentListClient.addAppointment(appointmentClient);
                    appointmentAppointmentWith = new Appointment("Appointment with: "+currentUser.getFirstName(), selectedDate, selectedTime, selectedAppointmentWithID, currentUserId, selectedServicesList,true);
                    appointmentListAppointmentWith.addAppointment(appointmentAppointmentWith);
                    FirebaseDatabase.getInstance().getReference("Appointments").child(currentUserId).setValue(appointmentListClient);
                    FirebaseDatabase.getInstance().getReference("Appointments").child(selectedAppointmentWithID).setValue(appointmentListAppointmentWith)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        goToHomeScreenActivity();

                                    } else {
                                        Snackbar.make(findViewById(R.id.confirm_appointment_button), "Failed to submit appointment", Snackbar.LENGTH_SHORT).show();
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

                /*CHECK IF TIME IS IN THE FUTURE
                Calendar datetime = Calendar.getInstance();
                Calendar c = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                datetime.set(Calendar.MINUTE, selectedMinute);
                if(datetime.getTimeInMillis() > c.getTimeInMillis()){
                }
                else{
                    Snackbar.make(selectTimeButton, "Appointment time cannot be in the past", Snackbar.LENGTH_SHORT).show();
                }
                */
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute,false);
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
                if((selDay >= day) && (selMonth >= (month+1)) && (selYear >= year)){
                    //Log.d(TAG, +selDay+"   >: "+day);
                    //Log.d(TAG, +selMonth+"   >: "+month+1);
                    //Log.d(TAG, +selYear+"   >: "+year);
                }
                else{
                    Snackbar.make(selectDateButton, "Appointment date cannot be in the past!", Snackbar.LENGTH_SHORT).show();
                    //Set time picker back to today's date
                    String dateFalse = makeDateString(day, month+1, year);
                    selectedDate = dateFalse;
                    selectDateButton.setText(dateFalse);
                }

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


}