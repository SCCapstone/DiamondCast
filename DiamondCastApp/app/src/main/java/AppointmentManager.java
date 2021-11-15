import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class AppointmentManager {
    private ArrayList<Appointment> appointments;
    private DatabaseReference databaseReference;

    public AppointmentManager() {
        //TODO
    }
    public AppointmentManager getInstance() {
        //TODO
        return new AppointmentManager();
    }
    public boolean appointmentExists(Appoinment appointment) {
        //TODO
        return true;
    }
    public Date getAppointmentTime(Appointment appointment) {
        //TODO
        return new Date();
    }

}
