package com.company;

import com.company.service.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static final GsonBuilder BUILDER = new GsonBuilder();
    public static final Gson GSON = BUILDER.setPrettyPrinting().create();
    public static final Path WRITE_PATH = Paths.get("./truck.json");
    public static final Path WRITE_PATH1 = Paths.get("./driver.json");
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        ServiceImpl service = new ServiceImpl();
        service.printTableAboutTrucks();
        service.printTableAboutDrivers();
        buttons();
        System.out.println("Return command: ");
        String button = in.nextLine();
        while (!button.equalsIgnoreCase("x")){
            switch (button){
             case "1" -> service.changeDriver();
             case "2" -> service.startDriving();
             case "3" -> service.startRepair();
             case "4" -> service.changeTruckState();

        }
            buttons();
            System.out.println("IF you want stop this while return 'x' if you not want stop this while return command : ");
            button = in.nextLine();
        }

        }
//        Truck[] truck = {
//                new Truck(1, "Volvo ", "Timon ", State.BASE),
//                new Truck(2, "Lexus ", "Puma  ", State.BASE),
//                new Truck(3, "Honda ", "Simon ", State.BASE),
//                new Truck(4, "Tesla ", "Harlan", State.BASE),
//                new Truck(5, "Iodise", "Travis", State.BASE)};
//        System.out.println(Arrays.toString(truck).trim());


    public static void buttons(){
        System.out.println("""
                Press 1 to change Driver
                Press 2 to send to the Route
                Press 3 to send to the Repairing
                Press 4 to change Truck State
                """);
    }

   public static String readTtuck() {
       return getString(WRITE_PATH);
   }

   public static String readDriver() {
       return getString(WRITE_PATH1);
   }

    private static String getString(Path writePath1) {
        StringBuilder json = new StringBuilder();
        try (FileReader fr = new FileReader(String.valueOf(writePath1))){
            int a;
            while ((a = fr.read()) != -1) {
                json.append((char) a);
            }
            return json.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return json.toString();
    }
}