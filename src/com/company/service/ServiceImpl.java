package com.company.service;

import com.company.entities.Driver;
import com.company.entities.Truck;

import java.util.*;

import static com.company.Main.*;

public class ServiceImpl implements Service {
    static Scanner in = new Scanner(System.in);
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
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
            System.out.println(ANSI_BLUE + "\nReturn id truck: " + ANSI_RESET);
            int truckID = in.nextInt();
            for (Truck truck : trucks) {
                if (truckID == truck.getId() && truck.getState().equals(Truck.State.BASE)) {
                    System.out.println(ANSI_BLUE + "\nReturn id driver" + ANSI_RESET);
                    int driversId = in.nextInt();
                    for (Driver driver : drivers) {
                        if (driversId == driver.getIdDiver() && !(Objects.equals(truck.getDriver(), driver.getName()))) {
                            truck.setDriver(driver.getName());
                            driver.setTruckName(truck.getTruckName());
                            printTableAboutDrivers();
                            System.out.println("Теперь грузовик " + truck.getTruckName() + "ведёт водитель " + driver.getName());
                        }
                    }
                    break;
                } else if (truck.getState().equals(Truck.State.ROUTE) && !truck.getState().equals(Truck.State.BASE)) {
                    System.out.println(ANSI_GREEN + "Грузовик в пути, невозможно сменить водителя" + ANSI_RESET);
                    break;
                } else if (truck.getState().equals(Truck.State.REPAIR) && !truck.getState().equals(Truck.State.BASE)) {
                    System.out.println(ANSI_GREEN + "Truck on repair невозможно сменить водителя" + ANSI_RESET);
                    break;
                } else if (truck.getDriver().isEmpty()) {
                    System.out.println("Choose other driver or other truck");
                } else {
                    System.out.println("Нет свободных водителей");
                    break;
                }
            }
        } catch (Exception exception) {
            System.out.println(ANSI_BLACK + "Нет свободных водителей" + ANSI_RESET);
        }

    }

    @Override
    public void startDriving() {
        printTableAboutTrucks();
        System.out.println(ANSI_PURPLE + "Return truck Id: " + ANSI_RESET);
        int truckId = in.nextInt();
        int counter = 0;
        for (Truck truck : trucks) {
            try {
                counter++;
                if (truck.getState().equals(Truck.State.ROUTE) && truck.getId() == truckId) {
                    System.out.println("Грузовик уже в пути");
                    printTableAboutTruck();
                    break;
                } else if (truck.getId() == truckId && !truck.getDriver().isEmpty() && !truck.getState().equals(Truck.State.ROUTE) && !truck.getDriver().equals(" ")) {
                    truck.setState(Truck.State.ROUTE);
                    System.out.println("успешно вышли на маршрут");
                    printTableAboutTrucks();
                    break;
                } else {
                    System.out.println(ANSI_YELLOW + "Truck driver is empty!" + ANSI_RESET);
                    printTableAboutTrucks();
                    break;
                }
            } finally {
                if (!truck.getDriver().equals("") && truck.getId() == truckId) {
                    System.out.println(ANSI_CYAN + "IF you want change state to state on repair, return 1 if not, return 0 " + ANSI_RESET);
                    int state = in.nextInt();
                    if (state == 1 && truck.getId() == truckId) {
                        startRepair();
                        break;
                    } else if (state == 0) {
                        break;
                    }
                } else if (counter == 1) {
                    break;
                }
            }
        }
    }

    @Override
    public void startRepair() {
        printTableAboutTrucks();
        System.out.println(ANSI_BLUE + "Chose and return id truck for start repair: " + ANSI_RESET);
        int truckId = in.nextInt();
        for (Truck truck : trucks) {
            try {
                if (truck.getDriver().isEmpty() || truck.getDriver() == null || truck.getDriver().equals(" ")) {
                    System.out.println(ANSI_YELLOW + "Truck is empty!" + ANSI_RESET);
                    printTableAboutTrucks();
                } else if (truck.getState().equals(Truck.State.REPAIR) && truck.getId() == truckId) {
                    System.out.println(ANSI_GREEN + "Уже в ремонте" + ANSI_RESET);
                    printTableAboutTrucks();
                } else if (truckId == truck.getId() && !truck.getDriver().equals(" ") && !truck.getDriver().isEmpty() && !truck.getState().equals(Truck.State.REPAIR) && truck.getDriver() != null) {
                    truck.setState(Truck.State.REPAIR);
                    System.out.println("успешно вышли на ремонт");
                    printTableAboutTrucks();
                }
            } finally {
                if (!truck.getDriver().isEmpty() && !truck.getDriver().equals(" ")) {
                    System.out.println(ANSI_CYAN + "if you want случайным образом изменить состояние грузовика на “route” или “base” return ”1” if not return ”0” " + ANSI_RESET);
                    int randomState = in.nextInt();
                    if (randomState == 1) {
                        Random random = new Random();
                        int stateSecond = random.nextInt(1, 3);
                        if (stateSecond == 1 && truck.getId() == truckId && !truck.getDriver().isEmpty()) {
                            truck.setState(Truck.State.ROUTE);
                            System.out.println("успешно вышли на маршрут");
                            printTableAboutTrucks();
                        } else if (stateSecond == 2 && truck.getId() == truckId) {
                            truck.setState(Truck.State.BASE);
                            System.out.println("Successful truck on base");
                            printTableAboutTrucks();
                        }
                        break;
                    } else {
                        break;
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
            Scanner in = new Scanner(System.in);
            ServiceImpl service = new ServiceImpl();
            service.printTableAboutTrucks();
            service.printTableAboutDrivers();
            buttons();
            System.out.println(ANSI_BLACK + "Return command: " + ANSI_BLACK);
            String button = in.nextLine();
            while (!button.equalsIgnoreCase("x")) {
                switch (button) {
                    case "1" -> service.changeDriver();
                    case "2" -> service.startDriving();
                    case "3" -> service.startRepair();
                }
                buttons();
                System.out.println(ANSI_CYAN + "IF you want stop this while return 'x' if you not want stop this while return command : " + ANSI_RESET);
                button = in.nextLine();
            }
        } catch (Exception e) {

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
            System.out.println("If you want information about one truck return Id if not just return  0:  ");
            int truckId = in.nextInt();
            if (truckId == 0){
                System.out.println("Ok");
            }
            else if (truckId != 0 && truckId < 6) {
                trucks.stream().filter(x -> x.getId() == truckId).forEach(System.out::println);
            } else if (truckId > 6) {
                System.out.println("We not have truck with this id");
            } else {

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