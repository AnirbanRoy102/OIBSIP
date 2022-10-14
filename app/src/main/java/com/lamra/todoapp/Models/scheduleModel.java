package com.lamra.todoapp.Models;

public class scheduleModel {
    String addtionalInfo,date,user,time;
    int status,id;

    public scheduleModel() {
    }

    public String getDate() {
        return date;
    }

    public String getUser() {
        return user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(String addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
