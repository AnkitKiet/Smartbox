package edu.smartbox.dto;

/**
 * Created by Ankit on 11/01/17.
 */
public class MarksDto {
    String subject;
    String marks;

    public MarksDto(){

    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
