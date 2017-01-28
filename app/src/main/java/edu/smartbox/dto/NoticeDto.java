package edu.smartbox.dto;

/**
 * Created by Ankit on 17/01/17.
 */
public class NoticeDto {

    String date;
    String notice;
String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
