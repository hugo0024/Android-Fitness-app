package com.hohimlee.mpa.Helper;

public class RecyclerView_data_handler {

    String event, miles, route, duration, startTime, endTime, date;

    public RecyclerView_data_handler(){}

    public RecyclerView_data_handler(String event, String miles, String route, String duration, String startTime, String endTime, String date) {
        this.event = event;
        this.miles = miles;
        this.route = route;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public String getRoute() { return route; }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDuration() { return duration; }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }

    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

}
