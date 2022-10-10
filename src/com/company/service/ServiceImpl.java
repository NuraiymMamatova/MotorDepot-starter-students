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
                        if (driversId == driver.getIdDiver() && !Objects.equals(truck.getDriver(), driver.getName()) && driver.getTruckName().isEmpty()) {
                            truck.setDriver(driver.getName());
                            driver.setTruckName(truck.getTruckName());
                            printTableAboutDrivers();
                            System.out.println("Теперь грузовик " + truck.getTruckName() + "ведёт водитель " + driver.getName());
                        }
                    }
                } else if (truck.getState().equals(Truck.State.ROUTE)) {
                    System.out.println("Грузовик в пути, невозможно сменить водителя");
                    break;
                }
            }
        } catch (Exception exception) {
            System.out.println("Нет свободных водителей");
        }

    }

    @Override
    public void startDriving() {
        printTableAboutTrucks();
        System.out.println("Return truck Id: ");
        int truckId = in.nextInt();
        int counter = 0;
            for (Truck truck : trucks) {
                try {
                    counter++;
                    if (truck.getState().equals(Truck.State.ROUTE) && truck.getId() == truckId /*&& truck.getDriver() != null*/) {
                    System.out.println("Грузовик уже в пути");
                }else if (truck.getId() == truckId && !truck.getDriver().isEmpty() && !truck.getState().equals(Truck.State.ROUTE) && !truck.getDriver().equals("")) {
                    truck.setState(Truck.State.ROUTE);
                    System.out.println("успешно вышли на маршрут");
                    printTableAboutTrucks();

                }else {
                    System.out.println("Truck driver is empty!");
                    break;
                }
            } finally {
                    if (!truck.getDriver().equals("") && truck.getId() ==truckId) {
                     System.out.println("IF you want change state to state on repair, return 1 if not, return 0 ");
                        int state = in.nextInt();
                        if (state == 1 && truck.getId() == truckId) {
                            startRepair();
                            break;
                        } else if (state == 0) {
                            break;
                        }
                    }else if (counter == 1){
                        break;
                    }
        }
        }
    }

    @Override
    public void startRepair() {
        printTableAboutTrucks();
        System.out.println("Chose and return id truck for start repair: ");
        int truckId = in.nextInt();
        try {
            for (Truck truck : trucks) {
                if (truck.getDriver().isEmpty() || truck.getDriver() == null || truck.getDriver().equals("")){
                System.out.println("Truck is empty!");
                }else if (truck.getState().equals(Truck.State.REPAIR) && truck.getId() == truckId) {
                    System.out.println("Уже в ремонте");
                    printTableAboutTrucks();
                } else if (!truck.getState().equals(Truck.State.BASE) && truck.getId() == truckId) {
                    truck.setState(Truck.State.BASE);
                    System.out.println("Truck on base");
                    printTableAboutTrucks();
                }else if (truckId == truck.getId() && !truck.getDriver().equals("") && !truck.getDriver().isEmpty() && !truck.getState().equals(Truck.State.REPAIR) && truck.getDriver() != null) {
                    truck.setState(Truck.State.REPAIR);
                    System.out.println("успешно вышли на ремонт");
                    printTableAboutTrucks();
                }
            }

        } finally {
            System.out.println("if you want случайным образом изменить состояние грузовика на “route” или “base” return ”1” if not return ”0” ");
            int randomState = in.nextInt();
            for (Truck truck : trucks) {
                if (randomState == 1) {
                    Random random = new Random();
                    int stateSecond = random.nextInt(1, 3);
                    if (stateSecond == 1 && truck.getId() == truckId) {
                        truck.setState(Truck.State.ROUTE);
                        System.out.println("успешно вышли на маршрут");
                        printTableAboutTrucks();
                    } else if (stateSecond == 2 && truck.getId() == truckId) {
                        truck.setState(Truck.State.BASE);
                        System.out.println("Successful truck on base");
                        printTableAboutTrucks();
                    }
                } else {
                    break;
                }
            }

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
        } finally {

        }
    }

    public void printTableAboutTrucks() {
        try {
            System.out.println("""
                    #  |Bus               |Driver |State
                    ___+__________________+_______+_____
                    """);
            trucks.forEach(System.out::println);
        } finally {

        }
    }

    public void printTableAboutTruck() {
        try {
            System.out.println("If you want information about one truck return Id if not just return  return:  ");
            int truckId = in.nextInt();
            if (truckId != 0 && truckId < 6) {
                trucks.stream().filter(x -> x.getId() == truckId).forEach(System.out::println);
            } else if (truckId > 6) {
                System.out.println("We not have truck with this id");
            }
        } finally {

        }
    }

    public void printTableAboutDrivers() {
        try {
            System.out.println("""
                                    
                    #  |Driver            |Bus
                    ___+__________________+_______
                    """);
            drivers.forEach(System.out::println);
        } finally {

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
        } finally {

        }
    }
}















