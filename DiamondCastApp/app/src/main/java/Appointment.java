import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {
    public String id;
    public String title;
    public Date date;
    public String service;
    public boolean active;

    public Appointment() {
        this.id = "0";
        this.title = title;
        //need to remove this is deprecated
        this.date = new Date("yyyy-MM-dd HH:mm:ss");
        this.service = "service";
        this.active = true;
    }
    public String getAppointmentID() {
        return this.id;
    }
    public String getAppointmentTitle() {
        return this.title;
    }
    public Date getAppointmentDate() {
        return this.date;
    }
    public String getAppointmentService() {
        return this.service;
    }
    public boolean getAppointmentActive() {
        return this.active;
    }
    public void setAppointmentID(String id) {
        this.id = id;
    }
    public void setAppointmentTitle(String title) {
        this.title = title;
    }
    public void setAppointmentDate(Date date) {
        this.date = date;
    }
    public void setAppointmentService(String service) {
        this.service = service;
    }
    public void setAppointmentActive(boolean isActive) {
        this.active = isActive;
    }
}
