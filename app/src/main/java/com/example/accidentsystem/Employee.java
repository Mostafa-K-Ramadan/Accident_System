package com.example.accidentsystem;

import java.util.ArrayList;

public class Employee {

    private int id;
    private String name;
    private String userName;
    private ArrayList<String> reportNum;
    private ArrayList<Accident> reported;

     public Employee(int id, String name, String userName) {
        this.id = id;
        this.name = name;
        this.userName = userName;
    }

     public void addReport(String reportNum) {
        this.reportNum.add(reportNum);
    }

     public void removeReport(String reportNum) {
        this.reportNum.remove(reportNum);
    }

     public void addReported(Accident acc) {
        reported.add(acc);
    }

     public int getId() {
        return id;
    }

     public void setId(int id) {
        this.id = id;
    }

     public String getName() {
        return name;
    }

     public void setName(String name) {
        this.name = name;
    }

     public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

     public ArrayList<String> getReportNum() {
        return reportNum;
    }

     public void setReportNum(ArrayList<String> reportNum) {
        this.reportNum = reportNum;
    }

     public ArrayList<Accident> getReported() {
        return reported;
    }

    public void setReported(ArrayList<Accident> reported) {
        this.reported = reported;
    }
}
