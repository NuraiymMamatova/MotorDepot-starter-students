package com.company.service;

public interface Service {

    void changeDriver();

    void startDriving();

    void startRepair();

    void changeTruckState() throws NoSuchFieldException;

}
