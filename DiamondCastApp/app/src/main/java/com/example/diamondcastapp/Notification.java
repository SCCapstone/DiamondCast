package com.example.diamondcastapp;

import java.io.Serializable;

public class Notification implements Serializable {

    public String contents;

    public Notification() {
        this.contents = "Appointment at XX:XX XX/XX/XXXX Cancelled";
    }

    public Notification(String contents) {
        this.contents = contents;
    }

    public String getContents() { return contents; }
}
