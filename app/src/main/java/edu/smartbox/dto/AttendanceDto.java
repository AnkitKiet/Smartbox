package edu.smartbox.dto;

/**
 * Created by Ankit on 20/01/17.
 */
public class AttendanceDto {

    String name;
    String roll;
    String attendance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String date) {
        this.roll = date;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
