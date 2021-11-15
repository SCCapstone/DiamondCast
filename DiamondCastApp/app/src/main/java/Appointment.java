import java.util.Date;

public class Appointment {
    public String id;
    public String title;
    public Date date;
    public String time;
    public String service;
    public boolean active;

    public Appointment() {
        //TODO
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
    public String getAppointmentTime() {
        return this.time;
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
    public void setAppointmentTime(String time) {
        this.time = time;
    }
    public void setAppointmentService(String service) {
        this.service = service;
    }
    public void setAppointmentActive(boolean isActive) {
        this.active = isActive;
    }
}
