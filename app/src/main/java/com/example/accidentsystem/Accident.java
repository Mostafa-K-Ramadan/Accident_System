package com.example.accidentsystem;

import android.media.Image;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Accident {


   private String id;
   private ArrayList<String> cars;
   private Date date;
   private ArrayList<String> descriptions = new ArrayList<String>();
   private ArrayList<String> evaluate = new ArrayList<String>();
   private boolean evaluated;
   private boolean state;
   private ArrayList<String> images = new ArrayList<String>();
    public Accident() {

    }

    public Accident(ArrayList<String> cars, Date date, ArrayList<String> descriptions, ArrayList<String> evaluate, boolean evaluated
            ,String id,ArrayList<String> images, boolean state) {
        this.id = id;
        this.images = images;
        this.cars = cars;
        this.date = date;
        this.descriptions = descriptions;
        this.evaluate = evaluate;
        this.evaluated = evaluated;
        this.state = state;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getCars() {
        return cars;
    }

    public void setCars(ArrayList<String> cars) {
        this.cars = cars;
    }

    public String getDate() {

        String d = "";
   //     long time = date.getTime();
       // d += "( "+date.getHours()+" : "+date.getMinutes()+" - "+date.getYear()+" : "+date.getMonth()+" : "+date.getDay()+" ) ";
   //     d = (int)(time/1000)+" : " + (int)(time/1000)+" : "+(int)(time/1000)+ " HOURS "+c.get(Calendar.AM_PM);
    //    d = date.getTime()+"";
      //  Date date1 = new Date(date);
     //   DateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");

       Long ms = date.getTime();
        /*int min = (int)((ms/(1000*60))%60);
        int hour = (int)((ms/(1000*60*60))%24);
        int day = (int)((ms/(1000*60*60*60))%31);
        int mon = (int)((ms/(1000*60*60*60*60))%12);
        int year = (int)((ms/(1000*60*60*60*60*60))%3000);*/

        d = new SimpleDateFormat("E - dd/MMM/yyyy hh:mm - z ").format(date);

    //    d = date.getYear() +" : "+date.getMonth() + " : "+date.getTime();

        return d;

    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(ArrayList<String> descriptoins) {
        this.descriptions = descriptoins;
    }

    public ArrayList<String> getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(ArrayList<String> evaluate) {
        this.evaluate = evaluate;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
