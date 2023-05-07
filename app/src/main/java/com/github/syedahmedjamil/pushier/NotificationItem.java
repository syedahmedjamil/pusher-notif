package com.github.syedahmedjamil.pushier;

public class NotificationItem {
    public String title;
    public String link;
    public String date;
    public String url;
    public String interest;

    NotificationItem(String title, String link, String date, String url, String interest){
        this.title = title;
        this.link = link;
        this.date = date;
        this.url = url;
        this.interest = interest;
    }
}
