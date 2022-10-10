package com.company.entities;

public class Truck {
    private int id;
    private String truckName;
    private String driver;
    private State State;


    public Truck(int id, String truckName, String driver, State State) {
        this.id = id;
        this.truckName = truckName;
        this.driver = driver;
        this.State = State;

    }

    @Override
    public String toString() {
        return id + "  |" + truckName + "     |" + driver + "      |" + State;
    }

    public enum State {
        BASE, ROUTE, REPAIR


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Truck.State getState() {
        return State;
    }

    public void setState(Truck.State state) {
        State = state;
    }
}

