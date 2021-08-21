package com.example.accidentsystem;

import java.util.ArrayList;

public class Customer {

    private int id;
    private int password;
    private String name;
    private ArrayList<String> cars;
    private ArrayList<Accident> history;
    private Accident current;
    private String carInAccident;

    Customer(int id, int password) {
        this.password = password;
        this.id = id;
    }

    public void addCar(String car) {
        cars.add(car);
    }

    public void addHistory(Accident acc) {
        history.add(acc);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getCars() {
        return cars;
    }

    public void setCars(ArrayList<String> cars) {
        this.cars = cars;
    }

    public ArrayList<Accident> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Accident> history) {
        this.history = history;
    }

    public Accident getCurrent() {
        return current;
    }

    public void setCurrent(Accident current) {
        this.current = current;
    }

    public String getCarInAccident() {
        return carInAccident;
    }

    public void setCarInAccident(String carInAccident) {
        this.carInAccident = carInAccident;

    }

}