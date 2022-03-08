package com.hohimlee.mpa.Helper;

public class RunningHandler {

    String miles, duration, route, date, event;

    public RunningHandler(){}

    public RunningHandler(String event, String miles, String duration, String route, String date) {
        this.event = event;
        this.miles = miles;
        this.duration = duration;
        this.route = route;
        this.date = date;
    }

    public String getEvent() { return event; }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMiles() { return miles; }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    public String getDuration() { return duration; }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRoute() { return route; }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date = date;
    }
}
