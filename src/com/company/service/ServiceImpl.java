package com.company.service;


import com.company.entities.Driver;
import com.company.entities.Truck;

import java.util.*;


import static com.company.Main.*;

public class ServiceImpl implements Service {
    static Scanner in = new Scanner(System.in);
    List<Truck> trucks = new ArrayList<>(List.of(GSON.fromJson(readTtuck(), Truck[].class)));
    List<Driver> drivers = new ArrayList<>(List.of(GSON.fromJson(readDriver(), Driver[].class)));


    public List<Truck> getTrucks() {
        return trucks;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    @Override
    public void changeDriver() {
        try {
            printTableAboutTrucks();
            System.out.println("\nid truck: ");
            int truckID = in.nextInt();
            for (Truck truck : trucks) {
                if (truckID == truck.getId() && truck.getState() != Truck.State.ROUTE) {
                    System.out.println("\nid driver");
                    int driversId = in.nextInt();
                    for (Driver driver : drivers) {
                        if (driversId == driver.getIdDiver() && !Objects.equals(truck.getDriver(), driver.getName())) {
                            truck.setDriver(driver.getName());
                            driver.setTruckName(truck.getTruckName());
                            printTableAboutDrivers();
                            System.out.println("Теперь грузовик " + truck.getTruckName() + "ведёт водитель " + driver.getName());
                        }
                    }
                } else if (truck.getState().equals(Truck.State.ROUTE)) {
                    System.out.println("Грузовик в пути, невозможно сменить водителя");
                }
            }
        } catch (Exception exception) {
            System.out.println("Нет свободных водителей");
        }

    }

    @Override
    public void startDriving() {
        try{
        printTableAboutTrucks();
        System.out.println("Return truck Id: ");
        int truckId = in.nextInt();
        for (Truck truck : trucks) {
            if (truck.getId() == truckId && !truck.getDriver().isEmpty()) {
                truck.setState(Truck.State.ROUTE);
                System.out.println("успешно вышли на маршрут");
                printTableAboutTrucks();
                System.out.println("IF you want change state to state on repair, return repair or not return return ");
                String state = in.nextLine();
                if (state.equalsIgnoreCase("repair") && truck.getId() == truckId || state.contains("Repair") && truckId == truck.getId()) {
                    startRepair();
                    //System.out.println("успешно вышли на ремонт");
                }
            } else if (truck.getState().equals(Truck.State.ROUTE) && truck.getId() == truck.getId()) {
                System.out.println("Грузовик уже в пути");
            }
        }
        }finally {

        }
    }

    @Override
    public void startRepair() {
        try{
        printTableAboutTrucks();
        System.out.println("Chose and return id truck for start repair: ");
        int truckId = in.nextInt();
        for (Truck truck : trucks) {
            System.out.println("if you want случайным образом изменить состояние грузовика на “route” или “base” return ”yes” if not return ”no” ");
            String state = in.nextLine();
            if (truckId == truck.getId()) {
                truck.setState(Truck.State.REPAIR);
                System.out.println("успешно вышли на ремонт");
            } else if (truck.getState().equals(Truck.State.REPAIR) && truck.getId() == truckId) {
                System.out.println("Уже в ремонте");
            } else if (!truck.getState().equals(Truck.State.BASE) && truck.getId() == truckId) {
                truck.setState(Truck.State.BASE);
                System.out.println("Truck on base");
            } else if (state.contains("Yes") && truck.getId() == truckId || state.equalsIgnoreCase("yes") && truck.getId() == truckId) {
                Random random = new Random();
                int stateSecond = random.nextInt(1, 3);
                if (stateSecond == 1) {
                    startDriving();
                    System.out.println("успешно вышли на маршрут");
                }
            }

        }
        }finally {

        }

    }

    @Override
    public void changeTruckState() {
        try {
            printTableAboutTrucks();
            for (Truck truck : trucks) {
                System.out.println("Return truck state: ");
                String truckState = in.nextLine();
                if (truckState.contains("Base") || truckState.equalsIgnoreCase("base")) {
                    truck.setState(Truck.State.BASE);
                    System.out.println("State truck successful changed base");
                    printTableAboutTrucks();
                    break;
                } else if (truckState.contains("Route") || truckState.equalsIgnoreCase("route") && !truck.getDriver().isEmpty()) {
                    truck.setState(Truck.State.ROUTE);
                    System.out.println("State truck successful changed route");
                    printTableAboutTrucks();
                    break;
                } else if (truckState.contains("Repair") || truckState.equalsIgnoreCase("repair")) {
                    truck.setState(Truck.State.REPAIR);
                    System.out.println("State truck successful changed repair");
                    printTableAboutTrucks();
                    break;
                }
            }
        }finally {

        }
    }

    public void printTableAboutTrucks() {
        try {
            System.out.println("""
                    #  |Bus               |Driver |State
                    ___+__________________+_______+_____
                    """);
            trucks.forEach(System.out::println);
        }finally {

        }
    }

    public void printTableAboutTruck() {
        try{
            System.out.println("If you want information about one truck return Id if not just return  return:  ");
            int truckId = in.nextInt();
            if (truckId != 0 && truckId < 6) {
            trucks.stream().filter(x -> x.getId() == truckId).forEach(System.out::println);
            } else if (truckId > 6) {
            System.out.println("We not have truck with this id");
        }
        }finally {

        }
    }

    public void printTableAboutDrivers() {
        try {
            System.out.println("""
                                    
                    #  |Driver            |Bus
                    ___+__________________+_______
                    """);
            drivers.forEach(System.out::println);
        }finally {

        }
    }

    public void printTableAboutDriver() {
        try {
        System.out.println("If you want information about one driver return Id if not just return  return:  ");
        String driverId = in.nextLine();
        if (driverId.length() == 1) {
            trucks.stream().filter(x -> driverId.equals(x.getId())).forEach(System.out::println);
        } else if (driverId.length() > 1) {
            System.out.println("We not have truck with this id ");
        }
        }finally {

        }
    }
}















